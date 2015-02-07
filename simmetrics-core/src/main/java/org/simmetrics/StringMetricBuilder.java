/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics;

import static com.google.common.collect.Collections2.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.simplifiers.CompositeSimplifier;
import org.simmetrics.simplifiers.PassThroughSimplifier;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.SimplifyingSimplifier;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizingTokenizer;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * Convenience tool to build string metrics.
 * 
 * <p>
 * String metrics build this way supports a general work flow that consists of
 * simplification, tokenization and comparison. Where applicable it should be
 * possible to construct a similarity metric with any simplifier, tokenizer. It
 * is also possible to add a cache for repeat operations.
 * 
 * <pre>
 * <code>
 * {@code
 * 	StringMetric metric = new StringMetricBuilder()
 * 			.with(new CosineSimilarity<String>())
 * 			.addSimplifier(new NonWordCharacterSimplifier())
 * 			.addSimplifier(new CaseSimplifier.Lower())
 * 			.setTokenizer(new WhitespaceTokenizer()).build();
 * }
 * </code>
 * </pre>
 * 
 * <h2>Simplification</h2>
 * 
 * Simplification increases the effectiveness of a metric by removing noise. The
 * process maps a a complex string such as
 * <code>Chilpéric II son of Childeric II</code> to a simpler format
 * <code>chilperic ii son of childeric ii</code>. This allows string from
 * different sources to be compared in the same normal form.
 * 
 * <p>
 * Simplification can be done by any class implementing the {@link Simplifier}
 * interface.
 * 
 * <h2>Tokenization</h2>
 * 
 * Tokenization cuts up a string into tokens e.g. <code>[chilperic, ii, son, of,
 * childeric, ii]</code>. Tokenization can also be done recursively
 * <code>[ch,hi,il,il,lp,pe,er,ri,ic, ii, so,on, of, ch,hi,il,ld,de,er,ri,ic, ii]</code>
 * 
 * <p>
 * The method of tokenization changes the effectiveness of a metric depending on
 * the context. A whitespace tokenizer might be more useful to measure
 * similarity between large bodies of texts whiles a q-gram tokenizer will work
 * more effectively for matching words.
 * 
 * <p>
 * Tokenization is required for all metrics that work on collections of tokens
 * rather then whole strings. These are {@link ListMetric}s and
 * {@link SetMetric}s
 * 
 * <p>
 * Tokenization can be done by any class implementing the
 * {@link TokenizingTokenizer} interface.
 * 
 * <h2>Measurement</h2>
 * 
 * Once simplified and possibly tokenized two strings can be compared. The exact
 * way which is determined by a metric. Metrics can either work on lists, sets
 * or directly work on strings. Any class implementing {@link StringMetric},
 * &nbsp;{@link ListMetric} or {@link SetMetric} can be used.
 * 
 * @author mpkorstanje
 *
 */
public class StringMetricBuilder {
	/**
	 * Starts building a metric with a string metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public SimplyfingMetricBuilder with(StringMetric metric) {
		return new SimplyfingMetricBuilder(metric);
	}

	/**
	 * Starts building a metric with a list metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public TokenListMetricBuilder with(ListMetric<String> metric) {
		return new TokenListMetricBuilder(metric);

	}

	/**
	 * Starts building a metric with a set metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public TokenSetMetricBuilder with(SetMetric<String> metric) {
		return new TokenSetMetricBuilder(metric);

	}

	/**
	 * Convenience tool to add simplification and caching support to a string
	 * metric.
	 * 
	 * @author mpkorstanje
	 *
	 */
	public final class SimplyfingMetricBuilder {

		private final StringMetric metric;

		private final List<Simplifier> simplifiers = new ArrayList<>();

		private SimplifyingSimplifier stringCache;

		SimplyfingMetricBuilder(StringMetric metric) {
			Preconditions.checkNotNull(metric);
			this.metric = metric;
		}

		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		public SimplyfingMetricBuilder addSimplifier(Simplifier simplifier) {
			Preconditions.checkNotNull(simplifier);
			this.simplifiers.add(simplifier);
			return this;
		}

		/**
		 * Sets the cache that will store the simplified strings.
		 * 
		 * @param cache
		 *            the cache to add
		 * @return this for fluent chaining
		 */
		public SimplyfingMetricBuilder setCache(SimplifyingSimplifier cache) {
			Preconditions.checkNotNull(cache);
			this.stringCache = cache;
			return this;
		}

		/**
		 * Builds a metric with the simplifiers and cache.
		 * 
		 * @return a metric
		 */
		public StringMetric build() {
			if (simplifiers.isEmpty()) {
				return metric;
			}

			Simplifier simplifier;
			if (simplifiers.size() == 1) {
				simplifier = simplifiers.get(0);
			} else {
				CompositeSimplifier composite = new CompositeSimplifier();
				composite.setSimplifiers(simplifiers
						.toArray(new Simplifier[simplifiers.size()]));
				simplifier = composite;
			}

			if (stringCache != null) {
				stringCache.setSimplifier(simplifier);
				simplifier = stringCache;
			}

			return new CompositeStringMetric(metric, simplifier);

		}
	}

	public abstract class TokenMetricBuilder<T> {

		private final T metric;

		private final List<Simplifier> simplifiers = new ArrayList<>();

		private Tokenizer tokenizer;

		private TokenizingTokenizer tokenCache;

		private SimplifyingSimplifier stringCache;

		private Predicate<String> tokenFilter;

		TokenMetricBuilder(T metric) {
			this.metric = metric;
		}

		/**
		 * Adds a simplifier to the end of the chain.
		 * 
		 * @param simplifier
		 *            simplifier to add
		 * @return this for fluent chaining
		 */
		public TokenMetricBuilder<T> addSimplifier(Simplifier simplifier) {
			Preconditions.checkNotNull(simplifier);
			this.simplifiers.add(simplifier);
			return this;
		}

		/**
		 * Sets the tokenizer.
		 * 
		 * @param tokenizer
		 *            tokenizer to tokenize strings
		 * @return this for fluent chaining
		 */
		public TokenMetricBuilder<T> setTokenizer(Tokenizer tokenizer) {
			Preconditions.checkNotNull(tokenizer);
			this.tokenizer = tokenizer;
			return this;
		}

		/**
		 * Sets the cache for the tokenization step.
		 * 
		 * The builder will use
		 * {@link TokenizingTokenizer#setTokenizer(Tokenizer)} to inject the
		 * tokenizer into the cache.
		 * 
		 * @param cache
		 *            cache to use for tokenized strings
		 * @return this for fluent chaining
		 */
		public TokenMetricBuilder<T> setCache(TokenizingTokenizer cache) {
			Preconditions.checkNotNull(cache);
			this.tokenCache = cache;
			return this;
		}

		/**
		 * Sets the cache for the simplification step.
		 * 
		 * The builder will use
		 * {@link SimplifyingSimplifier#setSimplifier(Simplifier)} to inject the
		 * simplifier into the cache.
		 * 
		 * @param cache
		 *            cache to use for simplified strings
		 * @return this for fluent chaining
		 */
		public TokenMetricBuilder<T> setCache(SimplifyingSimplifier cache) {
			Preconditions.checkNotNull(cache);
			this.stringCache = cache;
			return this;
		}

		public TokenMetricBuilder<T> filter(Predicate<String> tokenFilter) {
			this.tokenFilter = tokenFilter;
			return this;
		}

		/**
		 * Builds a the metric with the simplifiers, tokenizers and caches. A
		 * tokenizer is required.
		 * 
		 * @return a metric
		 * 
		 */
		public StringMetric build() {

			Preconditions.checkNotNull(tokenizer,
					"A tokenizer must be set to build a tokenizing metric");

			Simplifier simplifier;
			if (simplifiers.isEmpty()) {
				simplifier = new PassThroughSimplifier();
			} else if (simplifiers.size() == 1) {
				simplifier = simplifiers.get(0);
			} else {
				CompositeSimplifier composite = new CompositeSimplifier();
				composite.setSimplifiers(simplifiers
						.toArray(new Simplifier[simplifiers.size()]));
				simplifier = composite;
			}

			if (stringCache != null) {
				stringCache.setSimplifier(simplifier);
				simplifier = stringCache;
			}

			Tokenizer tokenizer = this.tokenizer;

			if (tokenFilter != null) {
				tokenizer = new TokenFilter(tokenizer, tokenFilter);
			}

			if (tokenCache != null) {
				tokenCache.setTokenizer(tokenizer);
				tokenizer = tokenCache;
			}

			return build(metric, simplifier, tokenizer);

		}

		protected abstract StringMetric build(T metric, Simplifier simplifier,
				Tokenizer tokenizer);
	}

	/**
	 * Convenience tool to create a string metric from a {@link ListMetric} .
	 * Supports simplification and caching.
	 * 
	 * @author mpkorstanje
	 *
	 */
	public final class TokenListMetricBuilder extends
			TokenMetricBuilder<ListMetric<String>> {

		TokenListMetricBuilder(ListMetric<String> metric) {
			super(metric);
		}

		@Override
		protected StringMetric build(ListMetric<String> metric,
				Simplifier simplifier, Tokenizer tokenizer) {
			return new CompositeTokenListMetric(metric, simplifier, tokenizer);
		}

	}

	/**
	 * Convenience tool to create a string metric from a {@link SetMetric} .
	 * Supports simplification and caching.
	 * 
	 * @author mpkorstanje
	 *
	 */
	public final class TokenSetMetricBuilder extends
			TokenMetricBuilder<SetMetric<String>> {

		TokenSetMetricBuilder(SetMetric<String> metric) {
			super(metric);
		}

		@Override
		protected StringMetric build(SetMetric<String> metric,
				Simplifier simplifier, Tokenizer tokenizer) {
			return new CompositeTokenSetMetric(metric, simplifier, tokenizer);
		}

	}

	public final class CompositeStringMetric implements StringMetric {

		private final Simplifier simplifier;

		private final StringMetric metric;

		CompositeStringMetric(StringMetric metric, Simplifier simplifier) {
			this.metric = metric;
			this.simplifier = simplifier;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(simplifier.simplify(a),
					simplifier.simplify(b));
		}

		@Override
		public String toString() {
			return metric + " [" + simplifier + "]";
		}

	}

	public final class CompositeTokenListMetric implements StringMetric {

		private final ListMetric<String> metric;
		private final Simplifier simplifier;
		private final Tokenizer tokenizer;

		CompositeTokenListMetric(ListMetric<String> metric,
				Simplifier simplifier, Tokenizer tokenizer) {
			super();
			this.metric = metric;
			this.simplifier = simplifier;
			this.tokenizer = tokenizer;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(
					tokenizer.tokenizeToList(simplifier.simplify(a)),
					tokenizer.tokenizeToList(simplifier.simplify(b)));
		}

		@Override
		public String toString() {
			return metric + " [" + simplifier + " -> " + tokenizer + "]";
		}

	}

	public final class CompositeTokenSetMetric implements StringMetric {

		private final SetMetric<String> metric;
		private final Simplifier simplifier;
		private final Tokenizer tokenizer;

		CompositeTokenSetMetric(SetMetric<String> metric,
				Simplifier simplifier, Tokenizer tokenizer) {
			super();
			this.metric = metric;
			this.simplifier = simplifier;
			this.tokenizer = tokenizer;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(
					tokenizer.tokenizeToSet(simplifier.simplify(a)),
					tokenizer.tokenizeToSet(simplifier.simplify(b)));
		}

		@Override
		public String toString() {
			return metric + " [" + simplifier + " -> " + tokenizer + "]";
		}

	}

	public final class TokenFilter implements Tokenizer {

		private final Tokenizer tokenizer;

		private final Predicate<String> filter;

		TokenFilter(Tokenizer tokenizer, Predicate<String> filter) {
			super();
			this.tokenizer = tokenizer;
			this.filter = filter;
		}

		@Override
		public ArrayList<String> tokenizeToList(String input) {
			return new ArrayList<>(filter(tokenizer.tokenizeToList(input),
					filter));
		}

		@Override
		public Set<String> tokenizeToSet(String input) {
			return new HashSet<>(filter(tokenizer.tokenizeToSet(input), filter));
		}

		@Override
		public String toString() {
			return tokenizer + "->" + filter;
		}

	}

}
