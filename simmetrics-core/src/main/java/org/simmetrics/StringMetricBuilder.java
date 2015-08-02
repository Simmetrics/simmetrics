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
import static org.simmetrics.StringMetrics.create;
import static org.simmetrics.StringMetrics.createForListMetric;
import static org.simmetrics.StringMetrics.createForSetMetric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;
import org.simmetrics.utils.CachingSimplifier;
import org.simmetrics.utils.CachingTokenizer;
import org.simmetrics.utils.SimplifyingSimplifier;
import org.simmetrics.utils.TokenizingTokenizer;

import com.google.common.base.Function;
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
 * By adding simplifiers, tokenizers and filters and transform the effectiveness
 * of a metric can be improved. The exact combination is generally domain
 * specific. This builder supports these domain specific customizations.
 * <p>
 * 
 * <pre>
 * <code>
 * {@code
 * 	StringMetric metric = new StringMetricBuilder()
 * 		.with(new CosineSimilarity<String>())
 * 		.simplify(new NonWordCharacter())
 * 		.simplify(new Case.Lower())
 * 		.tokenize(new Whitespace())
 * 		.filter(new LongWords())
 * 		.transform(new RemovePossessiveS())
 * 		.tokenize(new QGram(3))
 * 		.build();
 * }
 * 
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
 * 			.tokenize(new Whitespace())
 * 			.tokenize(new QGram(2))
 * 			.build();
 * }
 * </code>
 * </pre>
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
 * A Filter can be implemented by implementing a the {@link Predicate}
 * interface.
 * 
 * <pre>
 * <code>
 * {@code
 * 				with(new CosineSimilarity<String>())
 * 				.simplify(new Case.Lower())
 * 				.simplify(new WordCharacter())
 * 				.tokenize(new Whitespace())
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
 * 				with(new CosineSimilarity<String>())
 * 				.simplify(new Case.Lower())
 * 				.simplify(new NonWordCharacter())
 * 				.tokenize(new Whitespace())
 * 				.filter(Predicates.not(Predicates.in(commonWords)))
 * 				.build();
 * }
 * </code>
 * </pre>
 * 
 * <h2>Transformation</h2>
 * 
 * Functions can be used to transform tokens to a simpler or canonical form.
 * This may be used to reduce the possible token space. For example removing the
 * possessive -s from a sentence `[I'll, do, my, work, and, you, do, yours]`
 * results in `[I'll, do, my, work, and, you, do, your]`.
 * 
 * A function can be implemented by implementing a the {@link Function}
 * interface.
 *
 * <pre>
 * <code>
 * {@code
 * 		with(new CosineSimilarity<String>())
 * 		.simplify(new NonWordCharacter())
 * 		.simplify(new Case.Lower())
 * 		.tokenize(new Whitespace())
 * 		.transform(new RemovePossessiveS())
 * 		.build();
 * }
 * </code>
 * </pre>
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
 * 				with(new CosineSimilarity<String>())
 * 				.simplify(new Case.Lower())
 * 				.simplifierCache()
 * 				.tokenize(new QGram(2))
 * 				.tokenizerCache()
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
 * 
 * 
 */
public final class StringMetricBuilder {
	
	private StringMetricBuilder(){
		// Utility class
	}

	/**
	 * Starts building a metric with a string metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public static StringMetricInitialSimplifierStep with(StringMetric metric) {
		return new CompositeStringMetricBuilder(metric);
	}

	/**
	 * Starts building a metric with a list metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public static CollectionMetricInitialSimplifierStep with(
			ListMetric<String> metric) {
		return new CompositeListMetricBuilder(metric);

	}

	/**
	 * Starts building a metric with a set metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public static CollectionMetricInitialSimplifierStep with(
			SetMetric<String> metric) {
		return new CompositeSetMetricBuilder(metric);

	}

	@SuppressWarnings("javadoc")
	public interface BuildStep {
		/**
		 * Builds a metric with the given steps.
		 * 
		 * @return a metric
		 */
		StringMetric build();

	}

	@SuppressWarnings("javadoc")
	public interface StringMetricInitialSimplifierStep extends BuildStep {
		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		StringMetricSimplifierStep simplify(Simplifier simplifier);

		/**
		 * Builds a metric with the given simplifier.
		 * 
		 * @return a metric
		 */
		@Override
		StringMetric build();

	}

	@SuppressWarnings("javadoc")
	public interface StringMetricSimplifierStep extends
			StringMetricInitialSimplifierStep {
		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		@Override
		StringMetricSimplifierStep simplify(Simplifier simplifier);

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
		BuildStep simplifierCache(SimplifyingSimplifier cache);

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
		BuildStep simplifierCache(int initialCapacity, int maximumSize);

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all simplification steps. The cache will have a size of 2.
		 * 
		 * @return this for fluent chaining
		 */
		BuildStep simplifierCache();

		/**
		 * Builds a metric with the given simplifier.
		 * 
		 * @return a metric
		 */
		@Override
		StringMetric build();

	}

	@SuppressWarnings("javadoc")
	public interface CollectionMetricInitialSimplifierStep {
		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		CollectionMetricSimplifierStep simplify(Simplifier simplifier);

		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return this for fluent chaining
		 */
		CollectionMetricTokenizerStep tokenize(Tokenizer tokenizer);

	}

	@SuppressWarnings("javadoc")
	public interface CollectionMetricSimplifierStep extends
			CollectionMetricInitialSimplifierStep {
		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		@Override
		CollectionMetricSimplifierStep simplify(Simplifier simplifier);

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
		CollectionMetricInitialTokenizerStep simplifierCache(
				SimplifyingSimplifier cache);

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
		CollectionMetricInitialTokenizerStep simplifierCache(
				int initialCapacity, int maximumSize);

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all simplification steps. The cache will have a size of 2.
		 * 
		 * @return this for fluent chaining
		 */
		CollectionMetricInitialTokenizerStep simplifierCache();

		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return this for fluent chaining
		 */
		@Override
		CollectionMetricTokenizerStep tokenize(Tokenizer tokenizer);

	}

	@SuppressWarnings("javadoc")
	public interface CollectionMetricInitialTokenizerStep {
		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return a builder for fluent chaining
		 */
		CollectionMetricTokenizerStep tokenize(Tokenizer tokenizer);

	}

	@SuppressWarnings("javadoc")
	public interface CollectionMetricTokenizerStep extends BuildStep,
			CollectionMetricInitialTokenizerStep {
		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return a builder for fluent chaining
		 */
		@Override
		CollectionMetricTokenizerStep tokenize(Tokenizer tokenizer);

		/**
		 * Adds a filter step to the metric. All tokens that match the predicate
		 * are kept.
		 * 
		 * @param predicate
		 *            a predicate for tokens to keep
		 * @return this for fluent chaining
		 */
		CollectionMetricTokenizerStep filter(Predicate<String> predicate);

		/**
		 * Adds a transform step to the metric. All tokens are transformed by
		 * the function. The function may not return null.
		 * 
		 * @param function
		 *            a function to transform tokens
		 * @return this for fluent chaining
		 */
		CollectionMetricTokenizerStep transform(
				Function<String, String> function);

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
		BuildStep tokenizerCache(TokenizingTokenizer cache);

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
		BuildStep tokenizerCache(int initialCapacity, int maximumSize);

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all simplification steps. The cache will have a size of 2.
		 * 
		 * @return this for fluent chaining
		 */
		BuildStep tokenizerCache();

		/**
		 * Builds a string metric that will use the given simplification,
		 * tokenization and filtering steps.
		 * 
		 * @return a string metric.
		 */
		@Override
		StringMetric build();

	}

	private static final class CompositeStringMetricBuilder implements
			StringMetricSimplifierStep {

		private final StringMetric metric;

		private static final int CACHE_SIZE = 2;

		private final List<Simplifier> simplifiers = new ArrayList<>();

		private SimplifyingSimplifier cache;

		CompositeStringMetricBuilder(StringMetric metric) {
			checkNotNull(metric);
			this.metric = metric;
		}

		@Override
		public StringMetric build() {

			if (simplifiers.isEmpty()) {
				return metric;
			}

			if (cache == null) {
				return create(metric, chainSimplifiers());
			}

			cache.setSimplifier(chainSimplifiers());
			return create(metric, cache);
		}

		private Simplifier chainSimplifiers() {
			final Simplifier simplifier = Simplifiers.chain(simplifiers);
			simplifiers.clear();

			return simplifier;
		}

		@Override
		public BuildStep simplifierCache(SimplifyingSimplifier cache) {
			checkNotNull(cache);
			this.cache = cache;
			return this;
		}

		@Override
		public BuildStep simplifierCache(int initialCapacity, int maximumSize) {
			return simplifierCache(new CachingSimplifier(initialCapacity,
					maximumSize));
		}

		@Override
		public BuildStep simplifierCache() {
			return simplifierCache(CACHE_SIZE, CACHE_SIZE);
		}

		@Override
		public StringMetricSimplifierStep simplify(Simplifier simplifier) {
			checkNotNull(simplifier);
			this.simplifiers.add(simplifier);
			return this;
		}

	}

	private static abstract class CompositeCollectionMetricBuilder<T extends Collection<String>>
			implements CollectionMetricSimplifierStep,
			CollectionMetricTokenizerStep {

		private final Metric<T> metric;

		private static final int CACHE_SIZE = 2;

		private final List<Simplifier> simplifiers = new ArrayList<>();
		private final List<Tokenizer> tokenizers = new ArrayList<>();

		private SimplifyingSimplifier stringCache;
		private TokenizingTokenizer tokenCache;

		CompositeCollectionMetricBuilder(Metric<T> metric) {
			checkNotNull(metric);
			this.metric = metric;
		}

		@Override
		public StringMetric build() {

			Tokenizer tokenizer = chainTokenizers();

			if (tokenCache != null) {
				tokenCache.setTokenizer(tokenizer);
				tokenizer = tokenCache;
			}

			if (simplifiers.isEmpty()) {
				return build(metric, tokenizer);
			}

			if (stringCache != null) {
				stringCache.setSimplifier(chainSimplifiers());
				return build(metric, stringCache, tokenizer);
			}

			return build(metric, chainSimplifiers(), tokenizer);
		}

		abstract StringMetric build(Metric<T> metric, Simplifier simplifier,
				Tokenizer tokenizer);

		abstract StringMetric build(Metric<T> metric, Tokenizer tokenizer);

		@Override
		public BuildStep tokenizerCache(TokenizingTokenizer cache) {
			checkNotNull(cache);
			this.tokenCache = cache;
			return this;
		}

		@Override
		public BuildStep tokenizerCache(int initialCapacity, int maximumSize) {
			return tokenizerCache(new CachingTokenizer(initialCapacity,
					maximumSize));
		}

		@Override
		public BuildStep tokenizerCache() {
			return tokenizerCache(CACHE_SIZE, CACHE_SIZE);
		}

		@Override
		public CollectionMetricInitialTokenizerStep simplifierCache(
				SimplifyingSimplifier cache) {
			checkNotNull(cache);
			this.stringCache = cache;
			return this;
		}

		@Override
		public CollectionMetricInitialTokenizerStep simplifierCache(
				int initialCapacity, int maximumSize) {
			return simplifierCache(new CachingSimplifier(initialCapacity,
					maximumSize));
		}

		@Override
		public CollectionMetricInitialTokenizerStep simplifierCache() {
			return simplifierCache(CACHE_SIZE, CACHE_SIZE);
		}

		@Override
		public CollectionMetricSimplifierStep simplify(Simplifier simplifier) {
			checkNotNull(simplifier);
			simplifiers.add(simplifier);
			return this;
		}

		@Override
		public CollectionMetricTokenizerStep tokenize(Tokenizer tokenizer) {
			checkNotNull(tokenizer);
			tokenizers.add(tokenizer);
			return this;
		}

		@Override
		public CollectionMetricTokenizerStep filter(Predicate<String> predicate) {
			checkNotNull(predicate);

			final Tokenizer filter = Tokenizers.filter(chainTokenizers(),
					predicate);

			tokenizers.add(filter);

			return this;
		}

		@Override
		public CollectionMetricTokenizerStep transform(
				Function<String, String> function) {
			checkNotNull(function);
			final Tokenizer transform = Tokenizers.transform(chainTokenizers(),
					function);
			tokenizers.add(transform);

			return this;
		}

		private Tokenizer chainTokenizers() {
			final Tokenizer tokenizer = Tokenizers.chain(tokenizers);
			tokenizers.clear();
			return tokenizer;
		}

		private Simplifier chainSimplifiers() {
			final Simplifier simplifier = Simplifiers.chain(simplifiers);
			simplifiers.clear();
			return simplifier;
		}

	}

	private static final class CompositeListMetricBuilder extends
			CompositeCollectionMetricBuilder<List<String>> {

		CompositeListMetricBuilder(Metric<List<String>> metric) {
			super(metric);
		}

		@Override
		StringMetric build(Metric<List<String>> metric, Simplifier simplifier,
				Tokenizer tokenizer) {
			return createForListMetric(metric, simplifier, tokenizer);
		}

		@Override
		StringMetric build(Metric<List<String>> metric, Tokenizer tokenizer) {
			return createForListMetric(metric, tokenizer);
		}

	}

	private static final class CompositeSetMetricBuilder extends
			CompositeCollectionMetricBuilder<Set<String>> {

		CompositeSetMetricBuilder(Metric<Set<String>> metric) {
			super(metric);
		}

		@Override
		StringMetric build(Metric<Set<String>> metric, Simplifier simplifier,
				Tokenizer tokenizer) {
			return createForSetMetric(metric, simplifier, tokenizer);
		}

		@Override
		StringMetric build(Metric<Set<String>> metric, Tokenizer tokenizer) {
			return createForSetMetric(metric, tokenizer);
		}

	}

}
