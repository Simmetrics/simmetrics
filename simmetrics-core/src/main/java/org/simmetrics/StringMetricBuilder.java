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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.simmetrics.simplifiers.PassThroughSimplifier;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.utils.CachingSimplifier;
import org.simmetrics.utils.CachingTokenizer;
import org.simmetrics.utils.CompositeSimplifier;
import org.simmetrics.utils.CompositeStringMetric;
import org.simmetrics.utils.CompositeTokenListMetric;
import org.simmetrics.utils.CompositeTokenSetMetric;
import org.simmetrics.utils.FilteringTokenizer;
import org.simmetrics.utils.CompositeTokenizer;
import org.simmetrics.utils.SimplifyingSimplifier;
import org.simmetrics.utils.TokenizingTokenizer;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Convenience tool to build string metrics. Any class implementing
 * {@link StringMetric}, {@link ListMetric} or {@link SetMetric} can be used to
 * build a string metric. Supports the addition of simplification, tokenization,
 * filtering and caching to a metric.
 * 
 * <h2>Metrics</h2>
 * 
 * A metric is used to measure the similarity between strings. Metrics can work
 * on strings, lists or sets tokens. To compare strings with a metric that works
 * on a collection of tokens a tokenizer is required.
 * 
 * <p>
 * By adding simplifiers, tokenizers and filters the effectiveness of a metric
 * can be improved. The exact combination is generally domain specific. This
 * builder supports these domain specific customizations.
 * <p>
 * 
 * <pre>
 * <code>
 * {@code
 * 	StringMetric metric = new StringMetricBuilder()
 * 		.with(new CosineSimilarity<String>())
 * 		.simplify(new NonWordCharacterSimplifier())
 * 		.simplify(new CaseSimplifier.Lower())
 * 		.tokenize(new WhitespaceTokenizer())
 * 		.build();
 * }
 * </code>
 * </pre>
 * 
 * <h2>Simplification</h2>
 * 
 * Simplification increases the effectiveness of a metric by removing noise and
 * reducing the dimensionality of the problem. The process maps a a complex
 * string such as <code>Chilpéric II son of Childeric II</code> to a simpler
 * format <code>chilperic ii son of childeric ii</code>. This allows string from
 * different sources to be compared in the same normal form.
 * 
 * <p>
 * Simplification can be done by any class implementing the {@link Simplifier}
 * interface.
 * 
 * <h2>Tokenization</h2>
 * 
 * Tokenization cuts up a string into tokens e.g.
 * <code>chilperic ii son of childeric ii</code> is tokenized into
 * <code>[chilperic, ii, son, of,
 * childeric, ii]</code>. Tokenization can also be done repeatedly by tokenizing
 * the individual tokens e.g.
 * <code>[ch,hi,il,il,lp,pe,er,ri,ic, ii, so,on, of, ch,hi,il,ld,de,er,ri,ic, ii]</code>
 * <p>
 * 
 * <pre>
 * <code>
 * {@code
 * 	return new StringMetricBuilder()
 * 			.with(new SimonWhite<String>())
 * 			.tokenize(new WhitespaceTokenizer())
 * 			.tokenize(new QGramTokenizer(2))
 * 			.build();
 * }
 * </code>
 * </pre>
 * 
 * 
 * 
 * 
 * <p>
 * The method of tokenization changes the space in which strings are compared.
 * The effectiveness depends on the context. A whitespace tokenizer might be
 * more useful to measure similarity between large bodies of texts whiles a
 * q-gram tokenizer will work more effectively for matching words.
 * 
 * <p>
 * Tokenization can be done by any class implementing the {@link Tokenizer}
 * interface and is required for all metrics that work on collections of tokens
 * rather then whole strings; {@link ListMetric}s and {@link SetMetric}s
 * 
 * <h2>Filtering</h2>
 * 
 * 
 * Filtering removes tokens that should not be considered for comparison. For
 * example removing all tokens with a size less then three from `[chilperic, ii,
 * son, of, childeric, ii]` results in `[chilperic, son, childeric]`.
 * 
 * A Filter can be implemented by implementing a the Predicate interface.
 * 
 * <pre>
 * <code>
 * {@code
 * 		StringMetric metric = new StringMetricBuilder()
 * 				.with(new CosineSimilarity<String>())
 * 				.simplify(new CaseSimplifier.Lower())
 * 				.simplify(new NonWordCharacterSimplifier())
 * 				.tokenize(new WhitespaceTokenizer())
 * 				.filter(new MinimumLenght(3))
 * 				.build();
 * }
 * </code>
 * </pre>
 * 
 * By chaining predicates more complicated filters can be build.
 * 
 * <pre>
 * <code>
 * {@code
 * 		Set<String> commonWords = ...;
 * 		
 * 		StringMetric metric = new StringMetricBuilder()
 * 				.with(new CosineSimilarity<String>())
 * 				.simplify(new CaseSimplifier.Lower())
 * 				.simplify(new NonWordCharacterSimplifier())
 * 				.tokenize(new WhitespaceTokenizer())
 * 				.filter(Predicates.not(Predicates.in(commonWords)))
 * 				.build();
 * }
 * </code>
 * </pre>
 * 
 * 
 * <h2>Caching</h2>
 * 
 * Simplification and tokenization can be complex and expensive operations. When
 * comparing one string against a collection of strings these two operations are
 * done repeatedly for a single string - a common use case when searching for a
 * match. With a simple caching mechanism this overhead can be reduced.
 * 
 * 
 * <pre>
 * <code>
 * {@code
 * 		StringMetric metric = new StringMetricBuilder()
 * 				.with(new CosineSimilarity<String>())
 * 				.simplify(new CaseSimplifier.Lower())
 * 				.setSimplifierCache()
 * 				.tokenize(new QGramTokenizer(2))
 * 				.setTokenizerCache()
 * 				.build();
 * }
 * </code>
 * </pre>
 * 
 * When a cache is set it applies to the whole simplification or tokenization
 * chain. The default cache has a size of two for use with
 * `StringMetrics.compare(StringMetric, String, List<String>)` and friends.
 * 
 * 
 * @See {@link StringMetrics}
 * @See {@link Predicates}
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
	public CompositeStringMetricBuilder with(StringMetric metric) {
		return new CompositeStringMetricBuilder(metric);
	}

	/**
	 * Starts building a metric with a list metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public CompositeListMetricBuilder with(ListMetric<String> metric) {
		return new CompositeListMetricBuilder(metric);

	}

	/**
	 * Starts building a metric with a set metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public CompositeSetMetricBuilder with(SetMetric<String> metric) {
		return new CompositeSetMetricBuilder(metric);

	}

	/**
	 * Builder for the simplification chain.
	 * 
	 * @author M.P. Korstanje
	 * @param <T>
	 *            type of metric for which the chain is build
	 * 
	 */
	public abstract class SimplyfingBuilder<T> {

		protected final T metric;

		protected Simplifier simplifier = new PassThroughSimplifier();

		SimplyfingBuilder(T metric) {
			checkNotNull(metric);
			this.metric = metric;
		}

		void setSimplifier(Simplifier simplifier) {
			this.simplifier = simplifier;
		}

		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return a builder for fluent chaining
		 */
		public abstract SimplifierChainBuilder simplify(Simplifier simplifier);

	}

	/**
	 * Builder for the simplification chain of string metrics.
	 * 
	 * @author M.P. Korstanje
	 * 
	 */
	public final class CompositeStringMetricBuilder extends
			SimplyfingBuilder<StringMetric> {

		CompositeStringMetricBuilder(StringMetric metric) {
			super(metric);
		}

		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return a builder for fluent chaining
		 */
		@Override
		public StringSimplifierChainBuilder simplify(Simplifier simplifier) {
			return new StringSimplifierChainBuilder(this, simplifier);
		}

		/**
		 * Builds a metric with the given simplification steps and cache.
		 * 
		 * @return a metric
		 */
		public StringMetric build() {
			return new CompositeStringMetric(metric, simplifier);
		}

	}

	/**
	 * Builder for the simplification chain of collection based metrics.
	 * 
	 * @author M.P. Korstanje
	 * @param <T>
	 *            type of metric for which the chain is build
	 * 
	 */
	public abstract class CollectionMetricBuilder<T> extends
			SimplyfingBuilder<T> {

		protected Tokenizer tokenizer;

		CollectionMetricBuilder(T metric) {
			super(metric);
		}

		void setTokenizer(Tokenizer tokenizer) {
			checkNotNull(tokenizer);
			this.tokenizer = tokenizer;
		}

		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return a builder for fluent chaining
		 */
		public TokenizingChainBuilder tokenize(Tokenizer tokenizer) {
			return new TokenizingChainBuilder(this, tokenizer);
		}

		@Override
		public TokenSimplifierChainBuilder<T> simplify(Simplifier simplifier) {
			return new TokenSimplifierChainBuilder<>(this, simplifier);
		}

		abstract StringMetric build();

	}

	/**
	 * Convenience tool to create a string metric from a {@link ListMetric} .
	 * Supports simplification and caching.
	 * 
	 * @author mpkorstanje
	 * 
	 */
	public final class CompositeListMetricBuilder extends
			CollectionMetricBuilder<ListMetric<String>> {

		CompositeListMetricBuilder(ListMetric<String> metric) {
			super(metric);
		}

		@Override
		CompositeTokenListMetric build() {
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
	public final class CompositeSetMetricBuilder extends
			CollectionMetricBuilder<SetMetric<String>> {

		CompositeSetMetricBuilder(SetMetric<String> metric) {
			super(metric);
		}

		@Override
		public CompositeTokenSetMetric build() {
			return new CompositeTokenSetMetric(metric, simplifier, tokenizer);
		}

	}

	/**
	 * Builder for the simplification and tokenization chains.
	 * 
	 * @author M.P. Korstanje
	 * 
	 * @param <T>
	 *            type of the metric for which the chain is build
	 */
	public final class TokenSimplifierChainBuilder<T> extends
			SimplifierChainBuilder {

		private final CollectionMetricBuilder<T> builder;

		TokenSimplifierChainBuilder(CollectionMetricBuilder<T> builder,
				Simplifier simplifier) {
			super(simplifier);

			this.builder = builder;

		}

		@Override
		public TokenSimplifierChainBuilder<T> simplify(Simplifier simplifier) {
			super.simplify(simplifier);
			return this;
		}

		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return a builder for fluent chaining
		 */
		public TokenizingChainBuilder tokenize(Tokenizer tokenizer) {
			builder.setSimplifier(innerBuild());
			return builder.tokenize(tokenizer);
		}

		@Override
		public TokenSimplifierChainBuilder<T> setSimplifierCache(
				SimplifyingSimplifier cache) {
			super.setSimplifierCache(cache);
			return this;
		}

		@Override
		public TokenSimplifierChainBuilder<T> setSimplifierCache(
				int initialCapacity, int maximumSize) {
			super.setSimplifierCache(initialCapacity, maximumSize);
			return this;
		}

		@Override
		public TokenSimplifierChainBuilder<T> setSimplifierCache() {
			super.setSimplifierCache();
			return this;
		}

	}

	/**
	 * Builder for a simplifier chain.
	 * 
	 * @author M.P. Korstanje
	 * 
	 */
	public abstract class SimplifierChainBuilder {

		private static final int CACHE_SIZE = 2;

		private final List<Simplifier> simplifiers = new ArrayList<>();

		private SimplifyingSimplifier cache;

		SimplifierChainBuilder(Simplifier simplifier) {
			checkNotNull(simplifier);
			this.simplifiers.add(simplifier);
		}

		/**
		 * Adds a simplification step to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return a builder for fluent chaining
		 */
		public SimplifierChainBuilder simplify(Simplifier simplifier) {
			simplifiers.add(simplifier);
			return this;
		}

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all simplification steps. The cache will be provided with a
		 * simplifier through
		 * {@link SimplifyingSimplifier#setSimplifier(Simplifier)}.
		 * 
		 * @param cache
		 *            a cache to add
		 * @return this for fluent chaining
		 */
		public SimplifierChainBuilder setSimplifierCache(
				SimplifyingSimplifier cache) {
			Preconditions.checkNotNull(cache);
			this.cache = cache;
			return this;
		}

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all simplification steps.
		 * 
		 * @param initialCapacity
		 *            initial cache size
		 * @param maximumSize
		 *            maximum cache size
		 * 
		 * @return this for fluent chaining
		 */
		public SimplifierChainBuilder setSimplifierCache(int initialCapacity,
				int maximumSize) {
			return setSimplifierCache(new CachingSimplifier(initialCapacity,
					maximumSize));
		}

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all simplification steps. The cache will have a size of 2.
		 * 
		 * @return this for fluent chaining
		 */
		public SimplifierChainBuilder setSimplifierCache() {
			return setSimplifierCache(CACHE_SIZE, CACHE_SIZE);
		}

		Simplifier innerBuild() {
			Simplifier simplifier;

			if (simplifiers.size() == 1) {
				simplifier = simplifiers.get(0);
			} else {
				simplifier = new CompositeSimplifier(simplifiers);
			}

			if (cache != null) {
				cache.setSimplifier(simplifier);
				simplifier = cache;
			}

			return simplifier;
		}

	}

	/**
	 * Builder for the simplification chain, used by the
	 * {@link CompositeStringMetricBuilder}.
	 * 
	 * @author M.P. Korstanje
	 * 
	 */
	public final class StringSimplifierChainBuilder extends
			SimplifierChainBuilder {

		private final CompositeStringMetricBuilder builder;

		StringSimplifierChainBuilder(CompositeStringMetricBuilder builder,
				Simplifier simplifier) {
			super(simplifier);
			this.builder = builder;
		}

		@Override
		public StringSimplifierChainBuilder simplify(Simplifier simplifier) {
			super.simplify(simplifier);
			return this;
		}

		@Override
		public StringSimplifierChainBuilder setSimplifierCache(
				SimplifyingSimplifier cache) {
			super.setSimplifierCache(cache);
			return this;
		}

		@Override
		public StringSimplifierChainBuilder setSimplifierCache(
				int initialCapacity, int maximumSize) {
			super.setSimplifierCache(initialCapacity, maximumSize);
			return this;
		}

		@Override
		public StringSimplifierChainBuilder setSimplifierCache() {
			super.setSimplifierCache();
			return this;
		}

		/**
		 * Builds a string metric that will apply the given simplification
		 * steps.
		 * 
		 * @return a string metric.
		 */
		public StringMetric build() {
			builder.setSimplifier(innerBuild());
			return builder.build();
		}

	}

	/**
	 * Builder for the tokenization chain. Supports tokenization, filtering and
	 * caching.
	 * 
	 * @author M.P. Korstanje
	 * 
	 */
	public final class TokenizingChainBuilder {
		private static final int CACHE_SIZE = 2;

		private final CollectionMetricBuilder<?> builder;

		private final List<Tokenizer> tokenizers = new ArrayList<>();

		private TokenizingTokenizer cache;

		TokenizingChainBuilder(CollectionMetricBuilder<?> builder,
				Tokenizer tokenizer) {

			checkNotNull(tokenizer);

			this.builder = builder;
			this.tokenizers.add(tokenizer);

		}

		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return this for fluent chaining
		 */
		public TokenizingChainBuilder tokenize(Tokenizer tokenizer) {
			Preconditions.checkNotNull(tokenizer);
			this.tokenizers.add(tokenizer);
			return this;
		}

		/**
		 * Adds a filter step to the metric. All tokens that match the predicate
		 * are kept.
		 * 
		 * @param predicate
		 *            a predicate for tokens to keep
		 * @return this for fluent chaining
		 */
		public TokenizingChainBuilder filter(Predicate<String> predicate) {
			Preconditions.checkNotNull(predicate);
			Tokenizer tokenizer = innerBuild();
			this.tokenizers.add(new FilteringTokenizer(tokenizer, predicate));
			return this;
		}

		/**
		 * Sets a cache for tokenization chain. The cache will store the result
		 * of all tokenization steps. The cache will be provided with a
		 * tokenizer through {@link TokenizingTokenizer#setTokenizer(Tokenizer)}
		 * . .
		 * 
		 * @param cache
		 *            a cache to add
		 * @return this for fluent chaining
		 */
		public TokenizingChainBuilder setTokenizerCache(
				TokenizingTokenizer cache) {
			Preconditions.checkNotNull(cache);
			this.cache = cache;

			return this;
		}

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all simplification steps. The cache will have a size of 2.
		 * 
		 * @return this for fluent chaining
		 */
		public TokenizingChainBuilder setTokenizerCache() {
			return setTokenizerCache(CACHE_SIZE, CACHE_SIZE);
		}

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all simplification steps.
		 * 
		 * @param initialCapacity
		 *            initial cache size
		 * @param maximumSize
		 *            maximum cache size
		 * 
		 * @return this for fluent chaining
		 */
		public TokenizingChainBuilder setTokenizerCache(int initialCapacity,
				int maximumSize) {
			return setTokenizerCache(new CachingTokenizer(initialCapacity,
					maximumSize));
		}

		private Tokenizer innerBuild() {
			Tokenizer tokenizer;

			if (tokenizers.size() == 1) {
				tokenizer = tokenizers.get(0);
			} else {
				tokenizer = new CompositeTokenizer(new ArrayList<>(tokenizers));
			}

			if (cache != null) {
				cache.setTokenizer(tokenizer);
				tokenizer = cache;
			}

			return tokenizer;
		}

		/**
		 * Builds a string metric that will use the given simplification,
		 * tokenization and filtering steps.
		 * 
		 * @return a string metric.
		 */
		public StringMetric build() {
			builder.setTokenizer(innerBuild());
			return builder.build();
		}

	}

}
