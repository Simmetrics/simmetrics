/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
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

package org.simmetrics.builders;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.simmetrics.builders.StringMetrics.create;
import static org.simmetrics.builders.StringMetrics.createForListMetric;
import static org.simmetrics.builders.StringMetrics.createForMultisetMetric;
import static org.simmetrics.builders.StringMetrics.createForSetMetric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.simmetrics.ListMetric;
import org.simmetrics.Metric;
import org.simmetrics.MultisetMetric;
import org.simmetrics.SetMetric;
import org.simmetrics.StringMetric;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.collect.Multiset;

/**
 * Convenience tool to build string similarity metrics. Any class implementing
 * {@link StringMetric}, {@link ListMetric}, {@link SetMetric} or
 * {@link MultisetMetric} can be used to build a string similarity metric.
 * Supports the addition of simplification, tokenization, token-filtering,
 * token-transformation and caching to a metric.
 * <p>
 * The created similarity metrics are immutable and thread-safe provided all
 * their components are also immutable and thread-safe.
 * <p>
 * For usage examples see the simmetrics-example module.
 */
public final class StringMetricBuilder {

	private StringMetricBuilder() {
		// Utility class
	}

	/**
	 * Starts building a metric with a string similarity metric.
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
	public static CollectionMetricInitialSimplifierStep<List<String>> with(
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
	public static CollectionMetricInitialSimplifierStep<Set<String>> with(
			SetMetric<String> metric) {
		return new CompositeSetMetricBuilder(metric);

	}

	/**
	 * Starts building a metric with a multiset metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public static CollectionMetricInitialSimplifierStep<Multiset<String>> with(
			MultisetMetric<String> metric) {
		return new CompositeMultisetMetricBuilder(metric);

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
		 * result of all previous simplification steps.
		 * 
		 * @param cache
		 *            a cache to add
		 * @return this for fluent chaining
		 */
		BuildStep cacheStrings(Cache<String, String> cache);

		/**
		 * Builds a metric with the given simplifier.
		 * 
		 * @return a metric
		 */
		@Override
		StringMetric build();

	}

	@SuppressWarnings("javadoc")
	public interface CollectionMetricInitialSimplifierStep<T extends Collection<String>> {
		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		CollectionMetricSimplifierStep<T> simplify(Simplifier simplifier);

		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return this for fluent chaining
		 */
		CollectionMetricTokenizerStep<T> tokenize(Tokenizer tokenizer);

	}

	@SuppressWarnings("javadoc")
	public interface CollectionMetricSimplifierStep<T extends Collection<String>>
			extends CollectionMetricInitialSimplifierStep<T> {
		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		@Override
		CollectionMetricSimplifierStep<T> simplify(Simplifier simplifier);

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all previous simplification steps.
		 * 
		 * @param cache
		 *            a cache to add
		 * @return this for fluent chaining
		 */
		CollectionMetricInitialTokenizerStep<T> cacheStrings(
				Cache<String, String> cache);

		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return this for fluent chaining
		 */
		@Override
		CollectionMetricTokenizerStep<T> tokenize(Tokenizer tokenizer);

	}

	@SuppressWarnings("javadoc")
	public interface CollectionMetricInitialTokenizerStep<T extends Collection<String>> {
		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return a builder for fluent chaining
		 */
		CollectionMetricTokenizerStep<T> tokenize(Tokenizer tokenizer);

	}

	@SuppressWarnings("javadoc")
	public interface CollectionMetricTokenizerStep<T extends Collection<String>>
			extends BuildStep, CollectionMetricInitialTokenizerStep<T> {
		/**
		 * Adds a tokenization step to the metric.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return a builder for fluent chaining
		 */
		@Override
		CollectionMetricTokenizerStep<T> tokenize(Tokenizer tokenizer);

		/**
		 * Adds a filter step to the metric. All tokens that match the predicate
		 * are kept.
		 * 
		 * @param predicate
		 *            a predicate for tokens to keep
		 * @return this for fluent chaining
		 */
		CollectionMetricTokenizerStep<T> filter(Predicate<String> predicate);

		/**
		 * Adds a transform step to the metric. All tokens are transformed by
		 * the function. The function may not return null.
		 * 
		 * @param function
		 *            a function to transform tokens
		 * @return this for fluent chaining
		 */
		CollectionMetricTokenizerStep<T> transform(
				Function<String, String> function);

		/**
		 * Sets a cache for tokenization chain. The cache will store the result
		 * of all previous tokenization steps.
		 * 
		 * @param cache
		 *            a cache to add
		 * @return this for fluent chaining
		 * 
		 */
		BuildStep cacheTokens(Cache<String, T> cache);

		/**
		 * Builds a similarity metric that will use the given simplification,
		 * tokenization and filtering steps.
		 * 
		 * @return a string similarity metric.
		 */
		@Override
		StringMetric build();

	}

	private static final class CompositeStringMetricBuilder implements
			StringMetricSimplifierStep {

		private final Metric<String> metric;

		private final List<Simplifier> simplifiers = new ArrayList<>();

		CompositeStringMetricBuilder(Metric<String> metric) {
			checkNotNull(metric);
			this.metric = metric;
		}

		@Override
		public StringMetric build() {

			if (simplifiers.isEmpty()) {
				return create(metric);
			}
			return create(metric, chainSimplifiers());
		}

		private Simplifier chainSimplifiers() {
			final Simplifier simplifier = Simplifiers.chain(simplifiers);
			simplifiers.clear();

			return simplifier;
		}

		@Override
		public BuildStep cacheStrings(Cache<String, String> cache) {
			checkNotNull(cache);
			CachingSimplifier cachingSimplifier = new CachingSimplifier(cache,
					chainSimplifiers());
			this.simplifiers.add(cachingSimplifier);
			return this;
		}

		@Override
		public StringMetricSimplifierStep simplify(Simplifier simplifier) {
			checkNotNull(simplifier);
			this.simplifiers.add(simplifier);
			return this;
		}

	}

	private static abstract class CompositeCollectionMetricBuilder<T extends Collection<String>>
			implements CollectionMetricSimplifierStep<T>,
			CollectionMetricTokenizerStep<T> {

		private final Metric<T> metric;

		private final List<Simplifier> simplifiers = new ArrayList<>();
		private final List<Tokenizer> tokenizers = new ArrayList<>();

		CompositeCollectionMetricBuilder(Metric<T> metric) {
			checkNotNull(metric);
			this.metric = metric;
		}

		@Override
		public final StringMetric build() {

			Tokenizer tokenizer = chainTokenizers();

			if (simplifiers.isEmpty()) {
				return build(metric, tokenizer);
			}

			return build(metric, chainSimplifiers(), tokenizer);
		}

		abstract StringMetric build(Metric<T> metric, Simplifier simplifier,
				Tokenizer tokenizer);

		abstract StringMetric build(Metric<T> metric, Tokenizer tokenizer);

		@Override
		public final BuildStep cacheTokens(Cache<String, T> cache) {
			checkNotNull(cache);
			tokenizers.add(createCachingTokenizer(cache, chainTokenizers()));
			return this;
		}

		protected abstract Tokenizer createCachingTokenizer(
				Cache<String, T> cache, Tokenizer tokenizer);

		@Override
		public final CollectionMetricInitialTokenizerStep<T> cacheStrings(
				Cache<String, String> cache) {
			checkNotNull(cache);

			CachingSimplifier cachingSimplifier = new CachingSimplifier(cache,
					chainSimplifiers());
			this.simplifiers.add(cachingSimplifier);

			return this;
		}

		@Override
		public final CollectionMetricSimplifierStep<T> simplify(
				Simplifier simplifier) {
			checkNotNull(simplifier);
			simplifiers.add(simplifier);
			return this;
		}

		@Override
		public final CollectionMetricTokenizerStep<T> tokenize(
				Tokenizer tokenizer) {
			checkNotNull(tokenizer);
			tokenizers.add(tokenizer);
			return this;
		}

		@Override
		public final CollectionMetricTokenizerStep<T> filter(
				Predicate<String> predicate) {
			checkNotNull(predicate);

			final Tokenizer filter = Tokenizers.filter(chainTokenizers(),
					predicate);

			tokenizers.add(filter);

			return this;
		}

		@Override
		public final CollectionMetricTokenizerStep<T> transform(
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

		@Override
		protected Tokenizer createCachingTokenizer(
				Cache<String, List<String>> cache, Tokenizer tokenizer) {
			return new CachingListTokenizer(cache, tokenizer);
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

		@Override
		protected Tokenizer createCachingTokenizer(
				Cache<String, Set<String>> cache, Tokenizer tokenizer) {
			return new CachingSetTokenizer(cache, tokenizer);
		}

	}

	private static final class CompositeMultisetMetricBuilder extends
			CompositeCollectionMetricBuilder<Multiset<String>> {

		CompositeMultisetMetricBuilder(Metric<Multiset<String>> metric) {
			super(metric);
		}

		@Override
		StringMetric build(Metric<Multiset<String>> metric,
				Simplifier simplifier, Tokenizer tokenizer) {
			return createForMultisetMetric(metric, simplifier, tokenizer);
		}

		@Override
		StringMetric build(Metric<Multiset<String>> metric, Tokenizer tokenizer) {
			return createForMultisetMetric(metric, tokenizer);
		}

		@Override
		protected Tokenizer createCachingTokenizer(
				Cache<String, Multiset<String>> cache, Tokenizer tokenizer) {
			return new CachingMultisetTokenizer(cache, tokenizer);
		}

	}

	static final class CachingSimplifier implements Simplifier {

		private final Cache<String, String> cache;
		final Simplifier simplifier;

		CachingSimplifier(Cache<String, String> cache, Simplifier simplifier) {
			this.cache = cache;
			this.simplifier = simplifier;
		}

		@Override
		public String simplify(final String input) {
			try {
				return cache.get(input, new Callable<String>() {

					@Override
					public String call() throws Exception {
						return simplifier.simplify(input);
					}

				});
			} catch (ExecutionException e) {
				// Can't happen. Simplifier may not throw checked exceptions
				throw new IllegalStateException(e);
			}
		}

		@Override
		public String toString() {
			return "CachingSimplifier [" + simplifier + "]";
		}

	}

	static final class CachingMultisetTokenizer implements Tokenizer {

		private final Cache<String, Multiset<String>> cache;
		final Tokenizer tokenizer;

		CachingMultisetTokenizer(Cache<String, Multiset<String>> cache,
				Tokenizer tokenizer) {
			this.cache = cache;
			this.tokenizer = tokenizer;
		}

		@Override
		public List<String> tokenizeToList(final String input) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Set<String> tokenizeToSet(final String input) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Multiset<String> tokenizeToMultiset(final String input) {
			try {
				return cache.get(input, new Callable<Multiset<String>>() {

					@Override
					public Multiset<String> call() {
						return tokenizer.tokenizeToMultiset(input);
					}
				});
			} catch (ExecutionException e) {
				// Can't happen. Tokenizer may not throw checked exceptions
				throw new IllegalStateException(e);
			}
		}

		@Override
		public String toString() {
			return "CachingMultisetTokenizer [" + cache + ", " + tokenizer
					+ "]";
		}
	}

	static final class CachingSetTokenizer implements Tokenizer {

		private final Cache<String, Set<String>> cache;
		final Tokenizer tokenizer;

		CachingSetTokenizer(Cache<String, Set<String>> cache,
				Tokenizer tokenizer) {
			this.cache = cache;
			this.tokenizer = tokenizer;
		}

		@Override
		public List<String> tokenizeToList(final String input) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Set<String> tokenizeToSet(final String input) {
			try {
				return cache.get(input, new Callable<Set<String>>() {

					@Override
					public Set<String> call() {
						return tokenizer.tokenizeToSet(input);
					}
				});
			} catch (ExecutionException e) {
				// Can't happen. Tokenizer may not throw checked exceptions
				throw new IllegalStateException(e);
			}
		}

		@Override
		public Multiset<String> tokenizeToMultiset(final String input) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString() {
			return "CachingSetTokenizer [" + cache + ", " + tokenizer + "]";
		}
	}

	static final class CachingListTokenizer implements Tokenizer {

		private final Cache<String, List<String>> cache;
		final Tokenizer tokenizer;

		CachingListTokenizer(Cache<String, List<String>> cache,
				Tokenizer tokenizer) {
			this.cache = cache;
			this.tokenizer = tokenizer;
		}

		@Override
		public List<String> tokenizeToList(final String input) {
			try {
				return cache.get(input, new Callable<List<String>>() {

					@Override
					public List<String> call() {
						return tokenizer.tokenizeToList(input);
					}
				});
			} catch (ExecutionException e) {
				// Can't happen. Tokenizer may not throw checked exceptions
				throw new IllegalStateException(e);
			}
		}

		@Override
		public Set<String> tokenizeToSet(final String input) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Multiset<String> tokenizeToMultiset(final String input) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString() {
			return "CachingListTokenizer [" + cache + ", " + tokenizer + "]";
		}
	}

}
