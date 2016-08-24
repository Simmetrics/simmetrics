/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * #L%
 */
package org.simmetrics.tokenizers;

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.and;
import static com.google.common.base.Strings.repeat;
import static com.google.common.collect.Lists.asList;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

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
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;

/**
 * Construct simple tokenizers, chains multiple tokenizers into a single
 * tokenizer or creates a tokenizers that apply filters and transforms to
 * tokens.
 * <p>
 * The created tokenizers are immutable and thread-safe provided all their
 * components are also immutable and thread-safe.
 */
public final class Tokenizers {

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
		return new Split(pattern);
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
	 * Returns a q-gram tokenizer for a variable {@code q}. The tokenizer will
	 * return an empty collection if the input is empty. A collection with the
	 * original input is returned for tokens shorter then {@code q}.
	 * <p>
	 * The tokenizer takes care to split the string on Unicode code points, not
	 * separating valid surrogate pairs.
	 * 
	 * @param q
	 *            size of the tokens
	 * @return a q-gram tokenizer
	 *
	 */
	public static Tokenizer qGram(int q) {
		return new QGram(q);
	}

	/**
	 * Returns a q-gram tokenizer for a variable {@code q}.The tokenizer will
	 * return an empty collection if the input is empty or shorter then
	 * {@code q}.
	 * <p>
	 * The tokenizer takes care to split the string on Unicode code points, not
	 * separating valid surrogate pairs.
	 * @param q
	 *            size of the tokens
	 * @return a q-gram tokenizer
	 *
	 */
	public static Tokenizer qGramWithFilter(int q) {
		return new QGram(q, true);
	}

	/**
	 * Returns a q-gram tokenizer for a variable {@code q}. The input is padded
	 * with {@code q-1} special characters before being tokenized. Uses
	 * {@code #} as the default padding.
	 * <p>
	 * The tokenizer takes care to split the string on Unicode code points, not
	 * separating valid surrogate pairs.
	 * 
	 * @param q
	 *            size of the tokens
	 * @return a q-gram tokenizer
	 */
	public static Tokenizer qGramWithPadding(int q) {
		return new QGramExtended(q);
	}

	/**
	 * Returns a q-gram tokenizer for a variable {@code q}. The q-gram is
	 * extended beyond the length of the string with padding.
	 * <p>
	 * The tokenizer takes care to split the string on Unicode code points, not
	 * separating valid surrogate pairs.
	 * 
	 * @param q
	 *            size of the tokens
	 * @param padding
	 *            padding to pad start and end of string with
	 * @return a q-gram tokenizer
	 */
	public static Tokenizer qGramWithPadding(int q, String padding) {
		return qGramWithPadding(q, padding, padding);
	}

	/**
	 * Returns a q-gram tokenizer for a variable {@code q}.The q-gram is
	 * extended beyond the length of the string with padding.
	 * <p>
	 * The tokenizer takes care to split the string on Unicode code points, not
	 * separating valid surrogate pairs.
	 * 
	 * @param q
	 *            size of the tokens
	 * @param startPadding
	 *            padding to pad start of string with
	 * @param endPadding
	 *            padding to pad end of string with
	 * @return a q-gram tokenizer
	 */
	public static Tokenizer qGramWithPadding(int q, String startPadding,
			String endPadding) {
		return new QGramExtended(q, startPadding, endPadding);
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

		if (tokenizer instanceof Transform) {
			return Transform.createCombined(
					(Transform) tokenizer, function);
		} else if (tokenizer instanceof Filter) {
			return Transform.createCombined(
					(Filter) tokenizer, function);
		}

		return new Transform(tokenizer, function);
	}



	/**
	 * Chains tokenizers together. The output of each tokenizer is tokenized by
	 * the next. The tokenizers are applied in order.
	 * <p>
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
		return new Recursive(flatten(tokenizers));
	}
	
	private static List<Tokenizer> flatten(List<Tokenizer> simplifiers) {
		Builder<Tokenizer> flattend = ImmutableList.builder();

		for (Tokenizer s : simplifiers) {
			if (s instanceof Recursive) {
				// Tokenizers controls the creation of recursive tokenizers
				// all recursive tokenizers are flat so we don't have
				// to flatten recursively
				final Recursive c = (Recursive) s;
				flattend.addAll(c.getTokenizers());
			} else {
				flattend.add(s);
			}
		}

		return flattend.build();
	}

	/**
	 * Chains tokenizers together. The output of each tokenizer is tokenized by
	 * the next. The tokenizers are applied in order.
	 * <p>
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
		checkNotNull(tokenizer);

		if (tokenizers.length == 0) {
			return tokenizer;
		}
		return new Recursive(flatten(asList(tokenizer, tokenizers)));
	}

	/**
	 * Constructs a new filtering tokenizer. After tokenization, all tokens that
	 * don't match {@code predicate} are removed.
	 * 
	 * @param tokenizer
	 *            delegate tokenizer
	 * @param predicate
	 *            for tokens to keep
	 * @return a new filtering tokenizer.
	 */
	public static Tokenizer filter(Tokenizer tokenizer,
			Predicate<String> predicate) {

		if (tokenizer instanceof Filter) {
			return Filter.createCombined(
					(Filter) tokenizer, predicate);
		} else if (tokenizer instanceof Transform) {
			return Filter.createCombined(
					(Transform) tokenizer, predicate);
		}

		return new Filter(tokenizer, predicate);
	}
	
	static class Filter implements Tokenizer {

		static final class TransformFilter extends
				Filter {

			private final Transform tokenizer;

			TransformFilter(Transform tokenizer,
					Predicate<String> predicate) {
				super(tokenizer, predicate);
				this.tokenizer = tokenizer;
			}

			@Override
			Transform getTokenizer() {
				return tokenizer;
			}

			@Override
			Collection<String> tokenizeToFilteredList(String input) {
				return Collections2.filter(
						tokenizer.tokenizeToTransformedList(input), predicate);

			}

			@Override
			Collection<String> tokenizeToFilteredMultiset(String input) {
				return Collections2.filter(
						tokenizer.tokenizeToTransformedMultiset(input),
						predicate);
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
			public Multiset<String> tokenizeToMultiset(String input) {
				return HashMultiset.create(Collections2.filter(
						tokenizer.tokenizeToTransformedMultiset(input),
						predicate));
			}

			@Override
			public Set<String> tokenizeToSet(String input) {
				return newHashSet(Collections2.filter(
						tokenizer.tokenizeToTransformedSet(input), predicate));
			}
		}

		static Tokenizer createCombined(Filter tokenizer,
				Predicate<String> predicate) {

			if (tokenizer instanceof TransformFilter) {
				TransformFilter tft = (TransformFilter) tokenizer;
				return new TransformFilter(tft.getTokenizer(),
						and(tft.getPredicate(), predicate));
			}

			return new Filter(tokenizer.getTokenizer(), and(
					tokenizer.getPredicate(), predicate));
		}

		static Tokenizer createCombined(Transform tokenizer,
				Predicate<String> predicate) {
			return new TransformFilter(tokenizer, predicate);
		}

		protected final Predicate<String> predicate;

		private final Tokenizer tokenizer;

		Filter(Tokenizer tokenizer, Predicate<String> predicate) {
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

		Collection<String> tokenizeToFilteredMultiset(String input) {
			return Collections2.filter(tokenizer.tokenizeToMultiset(input),
					predicate);
		}

		Collection<String> tokenizeToFilteredSet(String input) {
			return Sets.filter(tokenizer.tokenizeToSet(input), predicate);
		}

		@Override
		public List<String> tokenizeToList(String input) {
			return new ArrayList<>(Collections2.filter(
					tokenizer.tokenizeToList(input), predicate));
		}

		@Override
		public Multiset<String> tokenizeToMultiset(String input) {
			return HashMultiset.create(Multisets.filter(
					tokenizer.tokenizeToMultiset(input), predicate));
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

	static final class Recursive implements Tokenizer {

		private final List<Tokenizer> tokenizers;

		Recursive(List<Tokenizer> tokenizers) {
			this.tokenizers = ImmutableList.copyOf(tokenizers);
		}

		List<Tokenizer> getTokenizers() {
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
		public Multiset<String> tokenizeToMultiset(String input) {

			// tokenizeToList is not reused here on purpose. Removing duplicate
			// words early means these don't have to be tokenized multiple
			// times. Increases performance.

			Multiset<String> tokens = HashMultiset.create(input.length());
			tokens.add(input);

			Multiset<String> newTokens = HashMultiset.create(input.length());
			for (Tokenizer t : tokenizers) {
				for (String token : tokens) {
					newTokens.addAll(t.tokenizeToList(token));
				}
				Multiset<String> swap = tokens;
				tokens = newTokens;
				newTokens = swap;
				newTokens.clear();
			}

			return tokens;
		}

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

	static final class Split extends AbstractTokenizer {

		private final Pattern pattern;

		public Split(Pattern pattern) {
			this.pattern = pattern;
		}

		@Override
		public List<String> tokenizeToList(final String input) {
			return asList(pattern.split(input, -1));
		}

		@Override
		public String toString() {
			return "Split[" + pattern + "]";
		}

		Pattern getPattern() {
			return pattern;
		}

	}

	static class Transform implements Tokenizer {
		
		static final class FilterTransform extends
				Transform {

			private final Filter tokenizer;

			FilterTransform(Filter tokenizer,
					Function<String, String> function) {
				super(tokenizer, function);
				this.tokenizer = tokenizer;
			}

			@Override
			Filter getTokenizer() {
				return tokenizer;
			}

			@Override
			public List<String> tokenizeToList(String input) {
				return newArrayList(Collections2.transform(
						tokenizer.tokenizeToFilteredList(input), function));
			}

			@Override
			public Multiset<String> tokenizeToMultiset(String input) {
				return HashMultiset.create(Collections2.transform(
						tokenizer.tokenizeToFilteredMultiset(input), function));
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
			Collection<String> tokenizeToTransformedMultiset(String input) {
				return Collections2.transform(
						tokenizer.tokenizeToFilteredMultiset(input), function);
			}

			@Override
			Collection<String> tokenizeToTransformedSet(String input) {
				return Collections2.transform(
						tokenizer.tokenizeToFilteredSet(input), function);
			}
		}

		static Tokenizer createCombined(Filter tokenizer,
				Function<String, String> function) {
			return new FilterTransform(tokenizer, function);
		}

		static Tokenizer createCombined(Transform tokenizer,
				Function<String, String> function) {

			if (tokenizer instanceof FilterTransform) {
				FilterTransform ftt = (FilterTransform) tokenizer;
				return new FilterTransform(ftt.getTokenizer(),
						compose(function, tokenizer.getFunction()));
			}

			return new Transform(tokenizer.getTokenizer(), compose(
					function, tokenizer.getFunction()));
		}

		protected final Function<String, String> function;

		private final Tokenizer tokenizer;

		Transform(Tokenizer tokenizer,
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
		public Multiset<String> tokenizeToMultiset(String input) {
			return HashMultiset.create(Collections2.transform(
					tokenizer.tokenizeToMultiset(input), function));
		}

		@Override
		public Set<String> tokenizeToSet(String input) {
			return newHashSet(Collections2.transform(
					tokenizer.tokenizeToSet(input), function));
		}

		Collection<String> tokenizeToTransformedList(String input) {
			return Lists.transform(tokenizer.tokenizeToList(input), function);
		}

		Collection<String> tokenizeToTransformedMultiset(String input) {
			return Collections2.transform(tokenizer.tokenizeToMultiset(input),
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

	static final class Whitespace extends AbstractTokenizer {

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
			return "Whitespace";
		}

	}

	/**
	 * Q-gram tokenizer for a variable {@code q}.
	 * <p>
	 * Input strings are tokenized into {@code q} substrings of {@code q}
	 * length. E.g. for {@code q=2} the string {@code "HelloWorld"} is tokenized
	 * into {@code [He, el, ll, lo, oW, Wo, or, rl, ld]}.
	 * <p>
	 * The tokenizer takes care to split the string on Unicode code points, not
	 * separating valid surrogate pairs.
	 * <p>
	 * This class is immutable and thread-safe.
	 *
	 */
	static class QGram extends AbstractTokenizer {

		private final int q;
		private final boolean filter;

		QGram(int q, boolean filter) {
			checkArgument(q > 0, "q must be greater then 0");
			this.q = q;
			this.filter = filter;
		}

		public QGram(int q) {
			this(q, false);
		}

		int getQ() {
			return q;
		}
		
		boolean isFilter() {
			return filter;
		}
		
		@Override
		public List<String> tokenizeToList(final String input) {
			if (input.isEmpty()) {
				return emptyList();
			}

			// Minor optimization. Because characters are either equal to or
			// smaller then codepoints a string must contain at least q
			// characters.
			if (filter && input.length() < q) {
				return new ArrayList<>();
			} else if (input.length() < q) {
				return singletonList(input);
			}

			// To create a q-gram set of tokens we move a q-codepoints-wide
			// sliding windows across the string. So the final index of the left
			// side of the window lies q code points to the left of end of the
			// string
			final int lastQGramStart;
			try {
				lastQGramStart = input.offsetByCodePoints(input.length(), -q);
			} catch (IndexOutOfBoundsException e) {
				// When the window doesn't fit act according to the filter
				// setting.
				if (filter) {
					return new ArrayList<>();
				}
				return singletonList(input);
			}

			final List<String> ret = new ArrayList<>(input.length());
			for (int qGramStart = 0; 
					 qGramStart <= lastQGramStart; 
					 qGramStart = input.offsetByCodePoints(qGramStart,1)) {
				ret.add(input.substring(qGramStart, input.offsetByCodePoints(qGramStart, q)));
			}

			return ret;
		}

		@Override
		public String toString() {
			return "QGram [q=" + q + "]";
		}
	}

	/**
	 * Q-gram tokenizer for a variable {@code q}.The q-gram is extended beyond
	 * the length of the string with padding.
	 * <p>
	 * * Input strings are tokenized into {@code q} substrings of {@code q}
	 * length. E.g. for {@code q=2} and padding {@code #} the string
	 * {@code "HelloWorld"} is tokenized into
	 * {@code [#H, He, el, ll, lo, oW, Wo, or, rl, ld, d#]}.
	 * <p>
	 * The tokenizer takes care to split the string on Unicode code points, not
	 * separating valid surrogate pairs.
	 * <p>
	 * This class is immutable and thread-safe.
	 * 
	 */
	static class QGramExtended extends AbstractTokenizer {

		private final static String DEFAULT_START_PADDING = "#";
		private final static String DEFAULT_END_PADDING = "#";

		private final String endPadding;
		private final String startPadding;

		private final QGram tokenizer;

		/**
		 * Constructs a q-gram tokenizer with the given {@code q} and padding.
		 * 
		 * @param q
		 *            size of the tokens
		 * @param startPadding
		 *            padding to apply at the start of short tokens
		 * @param endPadding
		 *            padding to apply at the end of short tokens
		 */
		public QGramExtended(int q, String startPadding, String endPadding) {
			checkArgument(!startPadding.isEmpty(),
					"startPadding may not be empty");
			checkArgument(!endPadding.isEmpty(), "endPadding may not be empty");

			this.tokenizer = new QGram(q);
			this.startPadding = repeat(startPadding, q - 1);
			this.endPadding = repeat(endPadding, q - 1);
		}

		/**
		 * Constructs a q-gram tokenizer with the given q and default padding.
		 * 
		 * @param q
		 *            size of the tokens
		 */
		public QGramExtended(int q) {
			this(q, DEFAULT_START_PADDING, DEFAULT_END_PADDING);
		}

		@Override
		public List<String> tokenizeToList(String input) {
			if (input.isEmpty()) {
				return emptyList();
			}

			return tokenizer.tokenizeToList(startPadding + input + endPadding);
		}

		@Override
		public String toString() {
			return "QGramExtended [startPadding=" + startPadding
					+ ", endPadding=" + endPadding + ", q=" + tokenizer.getQ()
					+ "]";
		}

		 int getQ() {
			return tokenizer.getQ();
		}
		 
		String getStartPadding() {
			return startPadding;
		}
		 
		 String getEndPadding() {
			return endPadding;
		}

	}

	private Tokenizers() {
		// Utility class
	}

}
