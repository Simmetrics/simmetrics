/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.simmetrics.tokenizers;

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.and;
import static com.google.common.collect.Lists.asList;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Utilities for tokenizers. Construct simple tokenizers, chain multiple
 * tokenizers into a single tokenizers or apply filters and transforms to
 * tokens.
 * <p>
 * All methods return immutable objects provided the arguments are also
 * immutable.
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

//			@Override
//			Collection<String> tokenizeToFilteredMultiset(String input) {
//				return Collections2.filter(
//						tokenizer.tokenizeToTransformedMultiset(input), predicate);
//			}
			
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

//			@Override
//			public Multiset<String> tokenizeToMultiset(String input) {
//				return HashMultiset.create(Collections2.filter(
//						tokenizer.tokenizeToTransformedMultiset(input), predicate));
//			}
			
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

//		Collection<String> tokenizeToFilteredMultiset(String input) {
//			return Collections2.filter(tokenizer.tokenizeToMultiset(input),
//					predicate);
//		}
		
		Collection<String> tokenizeToFilteredSet(String input) {
			return Sets.filter(tokenizer.tokenizeToSet(input),
					predicate);
		}
		
		@Override
		public List<String> tokenizeToList(String input) {
			return new ArrayList<>(Collections2.filter(
					tokenizer.tokenizeToList(input), predicate));
		}

//		@Override
//		public Multiset<String> tokenizeToMultiset(String input) {
//			return HashMultiset.create(Multisets.filter(
//					tokenizer.tokenizeToMultiset(input), predicate));
//		}
		
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

//		@Override
//		public Multiset<String> tokenizeToMultiset(String input) {
//
//			// tokenizeToList is not reused here on purpose. Removing duplicate
//			// words early means these don't have to be tokenized multiple
//			// times. Increases performance.
//
//			Multiset<String> tokens = HashMultiset.create(input.length());
//			tokens.add(input);
//
//			Multiset<String> newTokens =  HashMultiset.create(input.length());
//			for (Tokenizer t : tokenizers) {
//				for (String token : tokens) {
//					newTokens.addAll(t.tokenizeToList(token));
//				}
//				Multiset<String> swap = tokens;
//				tokens = newTokens;
//				newTokens = swap;
//				newTokens.clear();
//			}
//
//			return tokens;
//		}
		
		@Override
		public Set<String> tokenizeToSet(final String input) {

			// tokenizeToList is not reused here on purpose. Removing duplicate
			// words early means these don't have to be tokenized multiple
			// times. Increases performance.

			Set<String> tokens = new HashSet<>(input.length());
			tokens.add(input);

			Set<String> newTokens = new HashSet<>(input.length());
			for (Tokenizer t : tokenizers) {
				for (String token : tokens) {
					// Do use to list here, avoid intermediate 
					// adding of a list to to set to add it to newTokens
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

	private static final class SplitTokenizer extends AbstractTokenizer {

		private final Pattern pattern;

		public SplitTokenizer(Pattern pattern) {
			this.pattern = pattern;
		}

		@Override
		public List<String> tokenizeToList(final String input) {
			return asList(pattern.split(input, -1));
		}

		@Override
		public String toString() {
			return "SplitTokenizer [" + pattern + "]";
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

//			@Override
//			public Multiset<String> tokenizeToMultiset(String input) {
//				return HashMultiset.create(Collections2.transform(
//						tokenizer.tokenizeToFilteredMultiset(input), function));
//			}
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

//			@Override
//			Collection<String> tokenizeToTransformedMultiset(String input) {
//				return Collections2.transform(
//						tokenizer.tokenizeToFilteredMultiset(input), function);
//			}
			
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

//		@Override
//		public Multiset<String> tokenizeToMultiset(String input) {
//			return HashMultiset.create(Collections2.transform(
//					tokenizer.tokenizeToMultiset(input), function));
//		}

		@Override
		public Set<String> tokenizeToSet(String input) {
			return newHashSet(Collections2.transform(
					tokenizer.tokenizeToSet(input), function));
		}
		
		Collection<String> tokenizeToTransformedList(String input) {
			return Lists.transform(tokenizer.tokenizeToList(input),
					function);
		}

//		Collection<String> tokenizeToTransformedMultiset(String input) {
//			return Collections2.transform(tokenizer.tokenizeToMultiset(input),
//					function);
//		}
		
		Collection<String> tokenizeToTransformedSet(String input) {
			return Collections2.transform(tokenizer.tokenizeToSet(input),
					function);
		}
		
		@Override
		public final String toString() {
			return Joiner.on(" -> ").join(tokenizer, function);
		}


	}

	private static final class Whitespace extends AbstractTokenizer {

		private final Pattern pattern = Pattern.compile("\\s+");

		Whitespace() {
		}

		@Override
		public List<String> tokenizeToList(final String input) {
			if (input.isEmpty()) {
				return emptyList();
			}

			String[] tokens = pattern.split(input);

			// Remove leading empty token if any
			if (tokens.length > 0 && tokens[0].isEmpty()) {
				tokens = copyOfRange(tokens, 1, tokens.length);
			}

			return asList(tokens);
		}

		@Override
		public String toString() {
			return "WhitespaceTokenizer";
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
				// Tokenizers controls the creation of recursive tokenizers
				// all recursive tokenizers are flat so we don't have
				// to flatten recursively
				final RecursiveTokenizer c = (RecursiveTokenizer) s;
				flattend.addAll(c.getTokenizers());
			} else {
				flattend.add(s);
			}
		}

		return flattend;
	}

	/**
	 * Returns a tokenizer that splits a string into tokens around the pattern
	 * as if calling {@code pattern.split(input,-1)}.
	 * 
	 * @param pattern
	 *            to split the the string around
	 * 
	 * @return a pattern tokenizer
	 */
	public static Tokenizer pattern(Pattern pattern) {
		return new SplitTokenizer(pattern);
	}

	/**
	 * Returns a tokenizer that splits a string into tokens around the pattern
	 * as if calling {@code Pattern.compile(regex).split(input,-1)}.
	 * 
	 * @param regex
	 *            to split the the string around
	 * 
	 * @return a pattern tokenizer
	 */
	public static Tokenizer pattern(String regex) {
		return pattern(Pattern.compile(regex));
	}

	/**
	 * Returns a basic q-gram tokenizer for a variable q. The tokenizer will
	 * return an empty list if the input is empty. A list with the original
	 * input is returned for tokens shorter then q.
	 * 
	 * @param q
	 *            size of the tokens
	 * @return a q-gram tokenizer
	 *
	 */
	@SuppressWarnings("deprecation")
	public static Tokenizer qGram(int q) {
		return new QGram(q);
	}

	/**
	 * Returns a basic q-gram tokenizer for a variable q. The tokenizer will
	 * return an empty list if the input is empty or shorter then q.
	 * 
	 * @param q
	 *            size of the tokens
	 * @return a q-gram tokenizer
	 *
	 */
	@SuppressWarnings("deprecation")
	public static Tokenizer qGramWithFilter(int q) {
		return new QGram(q, true);
	}

	/**
	 * Returns a basic q-gram tokenizer for a variable q.The input is padded
	 * with q-1 special characters before being tokenized. Uses {@code #} as the
	 * default padding.
	 * 
	 * @param q
	 *            size of the tokens
	 * @return a q-gram tokenizer
	 */
	@SuppressWarnings("deprecation")
	public static Tokenizer qGramWithPadding(int q) {
		return new QGramExtended(q);
	}

	/**
	 * 
	 * Returns a basic q-gram tokenizer for a variable Q.The Q-Gram is extended
	 * beyond the length of the string with padding.
	 * 
	 * @param q
	 *            size of the tokens
	 * @param padding
	 *            padding to padd start and end of string with
	 * @return a q-gram tokenizer
	 */
	public static Tokenizer qGramWithPadding(int q, String padding) {
		return qGramWithPadding(q, padding, padding);
	}

	/**
	 * Returns a basic q-gram tokenizer for a variable Q.The Q-Gram is extended
	 * beyond the length of the string with padding.
	 * 
	 * @param q
	 *            size of the tokens
	 * @param startPadding
	 *            padding to padd startof string with
	 * @param endPadding
	 *            padding to padd end of string with
	 * @return a q-gram tokenizer
	 */
	@SuppressWarnings("deprecation")
	public static Tokenizer qGramWithPadding(int q, String startPadding,
			String endPadding) {
		return new QGramExtended(q, startPadding, endPadding);
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

	/**
	 * Returns a tokenizer that splits a string into tokens around whitespace.
	 * Does not return leading or trailing empty tokens.
	 * <p>
	 * To create tokenizer that returns leading and trailing empty tokens use
	 * {@code Tokenizers.pattern("\\s+")}
	 * 
	 * @return a white space tokenizer
	 */
	public static Tokenizer whitespace() {
		return new Whitespace();
	}

	private Tokenizers() {
		// Utility class
	}

}
