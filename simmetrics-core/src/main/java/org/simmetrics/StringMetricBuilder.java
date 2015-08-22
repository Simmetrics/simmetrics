/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This
 * program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
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

/**
 * Convenience tool to build string metrics. Any class implementing
 * {@link StringMetric}, {@link ListMetric} or {@link SetMetric} can be used to
 * build a string metric. Supports the addition of simplification, tokenization,
 * token-filtering, token-transformation and caching to a metric.
 * <p>
 * For usage examples see the simmetrics-example module.
 */
public class StringMetricBuilder {

	StringMetricBuilder() {
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

	private static final class CompositeStringMetricBuilder extends
			StringMetricBuilder implements StringMetricSimplifierStep {

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
			extends StringMetricBuilder implements
			CollectionMetricSimplifierStep, CollectionMetricTokenizerStep {

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
