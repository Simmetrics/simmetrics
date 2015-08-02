package org.simmetrics.tokenizers;

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.and;
import static com.google.common.collect.Lists.asList;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.StringMetricBuilder;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * Tokenizer composed of a tokenizer and one or more post processors. Either
 * more tokenizers, filters or transformations.
 * <p>
 * This class is immutable and thread-safe if its components are.
 * 
 * @see StringMetricBuilder
 */
public final class Tokenizers {

	private static class FilteringTokenizer implements Tokenizer {

		private static final class TransformingFilteringTokenizer extends
				FilteringTokenizer {

			private final TransformingTokenizer tokenizer;

			TransformingFilteringTokenizer(TransformingTokenizer tokenizer,
					Predicate<String> predicate) {
				super(tokenizer, predicate);
				this.tokenizer = tokenizer;
			}

			@Override
			TransformingTokenizer getTokenizer() {
				return tokenizer;
			}

			@Override
			Collection<String> tokenizeToFilteredList(String input) {
				return Collections2.filter(
						tokenizer.tokenizeToTransformedList(input), predicate);

			}

			@Override
			Collection<String> tokenizeToFilteredSet(String input) {
				return Collections2.filter(
						tokenizer.tokenizeToTransformedSet(input), predicate);
			}

			@Override
			public List<String> tokenizeToList(String input) {
				return newArrayList(Collections2.filter(
						tokenizer.tokenizeToTransformedList(input), predicate));
			}

			@Override
			public Set<String> tokenizeToSet(String input) {
				return newHashSet(Collections2.filter(
						tokenizer.tokenizeToTransformedSet(input), predicate));
			}
		}

		static Tokenizer createCombined(FilteringTokenizer tokenizer,
				Predicate<String> predicate) {

			if (tokenizer instanceof TransformingFilteringTokenizer) {
				TransformingFilteringTokenizer tft = (TransformingFilteringTokenizer) tokenizer;
				return new TransformingFilteringTokenizer(tft.getTokenizer(),
						and(tft.getPredicate(), predicate));
			}

			return new FilteringTokenizer(tokenizer.getTokenizer(), and(
					tokenizer.getPredicate(), predicate));
		}

		static Tokenizer createCombined(TransformingTokenizer tokenizer,
				Predicate<String> predicate) {
			return new TransformingFilteringTokenizer(tokenizer, predicate);
		}

		protected final Predicate<String> predicate;

		private final Tokenizer tokenizer;

		FilteringTokenizer(Tokenizer tokenizer, Predicate<String> predicate) {
			checkNotNull(tokenizer);
			checkNotNull(predicate);
			this.predicate = predicate;
			this.tokenizer = tokenizer;
		}

		Predicate<String> getPredicate() {
			return predicate;
		}

		Tokenizer getTokenizer() {
			return tokenizer;
		}

		Collection<String> tokenizeToFilteredList(String input) {
			return Collections2.filter(tokenizer.tokenizeToList(input),
					predicate);

		}

		Collection<String> tokenizeToFilteredSet(String input) {
			return Collections2.filter(tokenizer.tokenizeToSet(input),
					predicate);
		}

		@Override
		public List<String> tokenizeToList(String input) {
			return new ArrayList<>(Collections2.filter(
					tokenizer.tokenizeToList(input), predicate));
		}

		@Override
		public Set<String> tokenizeToSet(String input) {
			return new HashSet<>(Collections2.filter(
					tokenizer.tokenizeToSet(input), predicate));
		}

		@Override
		public final String toString() {
			return Joiner.on(" -> ").join(tokenizer, predicate);
		}

	}

	private static final class RecursiveTokenizer implements Tokenizer {

		private final List<Tokenizer> tokenizers;

		RecursiveTokenizer(List<Tokenizer> tokenizers) {
			checkArgument(!tokenizers.contains(null));
			this.tokenizers = new ArrayList<>(tokenizers);
		}

		Collection<Tokenizer> getTokenizers() {
			return tokenizers;
		}

		@Override
		public List<String> tokenizeToList(final String input) {
			List<String> tokens = new ArrayList<>(input.length());
			tokens.add(input);

			List<String> newTokens = new ArrayList<>(input.length());
			for (Tokenizer t : tokenizers) {
				for (String token : tokens) {
					newTokens.addAll(t.tokenizeToList(token));
				}
				List<String> swap = tokens;
				tokens = newTokens;
				newTokens = swap;
				newTokens.clear();
			}

			return tokens;
		}

		@Override
		public Set<String> tokenizeToSet(final String input) {

			// tokenizeToArray is not reused here on purpose. Removing duplicate
			// words early means these don't have to be tokenized multiple
			// times. Increases performance.

			Set<String> tokens = new HashSet<>(input.length());
			tokens.add(input);

			Set<String> newTokens = new HashSet<>(input.length());
			for (Tokenizer t : tokenizers) {
				for (String token : tokens) {
					newTokens.addAll(t.tokenizeToList(token));
				}
				Set<String> swap = tokens;
				tokens = newTokens;
				newTokens = swap;
				newTokens.clear();
			}

			return tokens;
		}

		@Override
		public String toString() {
			return Joiner.on(" -> ").join(tokenizers);
		}

	}

	private static class TransformingTokenizer implements Tokenizer {
		private static final class FilteringTransformingTokenizer extends
				TransformingTokenizer {

			private final FilteringTokenizer tokenizer;

			FilteringTransformingTokenizer(FilteringTokenizer tokenizer,
					Function<String, String> function) {
				super(tokenizer, function);
				this.tokenizer = tokenizer;
			}

			@Override
			FilteringTokenizer getTokenizer() {
				return tokenizer;
			}

			@Override
			public List<String> tokenizeToList(String input) {
				return newArrayList(Collections2.transform(
						tokenizer.tokenizeToFilteredList(input), function));
			}

			@Override
			public Set<String> tokenizeToSet(String input) {
				return newHashSet(Collections2.transform(
						tokenizer.tokenizeToFilteredSet(input), function));
			}

			@Override
			Collection<String> tokenizeToTransformedList(String input) {
				return Collections2.transform(
						tokenizer.tokenizeToFilteredList(input), function);
			}

			@Override
			Collection<String> tokenizeToTransformedSet(String input) {
				return Collections2.transform(
						tokenizer.tokenizeToFilteredSet(input), function);
			}
		}

		static Tokenizer createCombined(FilteringTokenizer tokenizer,
				Function<String, String> function) {
			return new FilteringTransformingTokenizer(tokenizer, function);
		}

		static Tokenizer createCombined(TransformingTokenizer tokenizer,
				Function<String, String> function) {

			if (tokenizer instanceof FilteringTransformingTokenizer) {
				FilteringTransformingTokenizer ftt = (FilteringTransformingTokenizer) tokenizer;
				return new FilteringTransformingTokenizer(ftt.getTokenizer(),
						compose(function, tokenizer.getFunction()));
			}

			return new TransformingTokenizer(tokenizer.getTokenizer(), compose(
					function, tokenizer.getFunction()));
		}

		protected final Function<String, String> function;

		private final Tokenizer tokenizer;

		TransformingTokenizer(Tokenizer tokenizer,
				Function<String, String> function) {
			checkNotNull(tokenizer);
			checkNotNull(function);
			this.function = function;
			this.tokenizer = tokenizer;
		}

		Function<String, String> getFunction() {
			return function;
		}

		Tokenizer getTokenizer() {
			return tokenizer;
		}

		@Override
		public List<String> tokenizeToList(String input) {
			return newArrayList(Collections2.transform(
					tokenizer.tokenizeToList(input), function));
		}

		@Override
		public Set<String> tokenizeToSet(String input) {
			return newHashSet(Collections2.transform(
					tokenizer.tokenizeToSet(input), function));
		}

		Collection<String> tokenizeToTransformedList(String input) {
			return Collections2.transform(tokenizer.tokenizeToList(input),
					function);
		}

		Collection<String> tokenizeToTransformedSet(String input) {
			return Collections2.transform(tokenizer.tokenizeToSet(input),
					function);
		}

		@Override
		public final String toString() {
			return Joiner.on(" -> ").join(tokenizer, function);
		}
	}

	/**
	 * Chains tokenizers together. The output of each tokenizer is tokenized by
	 * the next. The tokenizers are applied in order.
	 * 
	 * If only a single tokenizer is provided, that tokenizer is returned.
	 * 
	 * @param tokenizers
	 *            a non-empty list of tokenizers
	 * @return a chain of tokenizers
	 */
	public static Tokenizer chain(List<Tokenizer> tokenizers) {
		if (tokenizers.size() == 1) {
			return tokenizers.get(0);
		}
		return new RecursiveTokenizer(flatten(tokenizers));
	}

	
	/**
	 * Chains tokenizers together. The output of each tokenizer is tokenized by
	 * the next. The tokenizers are applied in order.
	 * 
	 * If only a single tokenizer is provided, that tokenizer is returned.
	 * 
	 * @param tokenizer
	 *            the first tokenizer
	 * 
	 * @param tokenizers
	 *            a the other tokenizers
	 * @return a chain of tokenizers
	 */
	public static Tokenizer chain(Tokenizer tokenizer, Tokenizer... tokenizers) {
		checkArgument(tokenizer != null);

		if (tokenizers.length == 0) {
			return tokenizer;
		}

		return chain(asList(tokenizer, tokenizers));
	}
	/**
	 * Constructs a new filtering tokenizer. After tokenization, all tokens that
	 * don't match a predicate are removed.
	 * 
	 * @param tokenizer
	 *            delegate tokenizer
	 * @param predicate
	 *            for tokens to keep
	 * @return a new filtering tokenizer.
	 */
	public static Tokenizer filter(Tokenizer tokenizer,
			Predicate<String> predicate) {

		if (tokenizer instanceof FilteringTokenizer) {
			return FilteringTokenizer.createCombined(
					(FilteringTokenizer) tokenizer, predicate);
		} else if (tokenizer instanceof TransformingTokenizer) {
			return FilteringTokenizer.createCombined(
					(TransformingTokenizer) tokenizer, predicate);
		}

		return new FilteringTokenizer(tokenizer, predicate);
	}

	private static List<Tokenizer> flatten(List<Tokenizer> simplifiers) {
		final List<Tokenizer> flattend = new ArrayList<>(simplifiers.size());

		for (Tokenizer s : simplifiers) {
			if (s instanceof RecursiveTokenizer) {
				final RecursiveTokenizer c = (RecursiveTokenizer) s;
				flattend.addAll(c.getTokenizers());
			} else {
				flattend.add(s);
			}
		}

		return flattend;
	}

	/**
	 * Constructs a new transforming tokenizer. After tokenization, all tokens
	 * are transformed by the function.
	 * 
	 * @param tokenizer
	 *            delegate tokenizer
	 * @param function
	 *            to transform tokens
	 * @return a new transforming tokenizer.
	 */
	public static Tokenizer transform(Tokenizer tokenizer,
			Function<String, String> function) {

		if (tokenizer instanceof TransformingTokenizer) {
			return TransformingTokenizer.createCombined(
					(TransformingTokenizer) tokenizer, function);
		} else if (tokenizer instanceof FilteringTokenizer) {
			return TransformingTokenizer.createCombined(
					(FilteringTokenizer) tokenizer, function);
		}

		return new TransformingTokenizer(tokenizer, function);
	}

	private Tokenizers() {
		// Utility class
	}

}