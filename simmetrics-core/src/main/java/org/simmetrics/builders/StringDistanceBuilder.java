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
import static org.simmetrics.builders.StringDistances.create;
import static org.simmetrics.builders.StringDistances.createForListDistance;
import static org.simmetrics.builders.StringDistances.createForMultisetDistance;
import static org.simmetrics.builders.StringDistances.createForSetDistance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.simmetrics.ListDistance;
import org.simmetrics.Distance;
import org.simmetrics.MultisetDistance;
import org.simmetrics.SetDistance;
import org.simmetrics.StringDistance;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.collect.Multiset;

/**
 * Convenience tool to build string distance metrics. Any class implementing
 * {@link StringDistance}, {@link ListDistance}, {@link SetDistance} or
 * {@link MultisetDistance} can be used to build a string distance metric.
 * Supports the addition of simplification, tokenization, token-filtering,
 * token-transformation and caching to a distance.
 * <p>
 * The created distance metrics are immutable and thread-safe provided all their components
 * are also immutable and thread-safe.
 * <p>
 * For usage examples see the simmetrics-example module.
 */
public final class StringDistanceBuilder {

	private StringDistanceBuilder() {
		// Utility class
	}

	/**
	 * Starts building a distance metric with a string distance metric.
	 * 
	 * @param distance
	 *            the distance to use as a base
	 * @return a builder for fluent chaining
	 */
	public static StringDistanceInitialSimplifierStep with(StringDistance distance) {
		return new CompositeStringDistanceBuilder(distance);
	}

	/**
	 * Starts building a distance with a list distance.
	 * 
	 * @param distance
	 *            the distance to use as a base
	 * @return a builder for fluent chaining
	 */
	public static CollectionDistanceInitialSimplifierStep<List<String>> with(ListDistance<String> distance) {
		return new CompositeListDistanceBuilder(distance);

	}

	/**
	 * Starts building a distance with a set distance.
	 * 
	 * @param distance
	 *            the distance to use as a base
	 * @return a builder for fluent chaining
	 */
	public static CollectionDistanceInitialSimplifierStep<Set<String>> with(SetDistance<String> distance) {
		return new CompositeSetDistanceBuilder(distance);

	}

	/**
	 * Starts building a distance with a multiset distance.
	 * 
	 * @param distance
	 *            the distance to use as a base
	 * @return a builder for fluent chaining
	 */
	public static CollectionDistanceInitialSimplifierStep<Multiset<String>> with(MultisetDistance<String> distance) {
		return new CompositeMultisetDistanceBuilder(distance);

	}

	@SuppressWarnings("javadoc")
	public interface BuildStep {
		/**
		 * Builds a distance with the given steps.
		 * 
		 * @return a distance
		 */
		StringDistance build();

	}

	@SuppressWarnings("javadoc")
	public interface StringDistanceInitialSimplifierStep extends BuildStep {
		/**
		 * Adds a simplifier to the distance.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		StringDistanceSimplifierStep simplify(Simplifier simplifier);

		/**
		 * Builds a distance with the given simplifier.
		 * 
		 * @return a distance
		 */
		@Override
		StringDistance build();

	}

	@SuppressWarnings("javadoc")
	public interface StringDistanceSimplifierStep extends StringDistanceInitialSimplifierStep {
		/**
		 * Adds a simplifier to the distance.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		@Override
		StringDistanceSimplifierStep simplify(Simplifier simplifier);

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
		 * Builds a distance with the given simplifier.
		 * 
		 * @return a distance
		 */
		@Override
		StringDistance build();

	}

	@SuppressWarnings("javadoc")
	public interface CollectionDistanceInitialSimplifierStep<T extends Collection<String>> {
		/**
		 * Adds a simplifier to the distance.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		CollectionDistanceSimplifierStep<T> simplify(Simplifier simplifier);

		/**
		 * Adds a tokenization step to the distance.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return this for fluent chaining
		 */
		CollectionDistanceTokenizerStep<T> tokenize(Tokenizer tokenizer);

	}

	@SuppressWarnings("javadoc")
	public interface CollectionDistanceSimplifierStep<T extends Collection<String>>
			extends CollectionDistanceInitialSimplifierStep<T> {
		/**
		 * Adds a simplifier to the distance.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		@Override
		CollectionDistanceSimplifierStep<T> simplify(Simplifier simplifier);

		/**
		 * Sets a cache for simplification chain. The cache will store the
		 * result of all previous simplification steps.
		 * 
		 * @param cache
		 *            a cache to add
		 * @return this for fluent chaining
		 */
		CollectionDistanceInitialTokenizerStep<T> cacheStrings(Cache<String, String> cache);

		/**
		 * Adds a tokenization step to the distance.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return this for fluent chaining
		 */
		@Override
		CollectionDistanceTokenizerStep<T> tokenize(Tokenizer tokenizer);

	}

	@SuppressWarnings("javadoc")
	public interface CollectionDistanceInitialTokenizerStep<T extends Collection<String>> {
		/**
		 * Adds a tokenization step to the distance.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return a builder for fluent chaining
		 */
		CollectionDistanceTokenizerStep<T> tokenize(Tokenizer tokenizer);

	}

	@SuppressWarnings("javadoc")
	public interface CollectionDistanceTokenizerStep<T extends Collection<String>>
			extends BuildStep, CollectionDistanceInitialTokenizerStep<T> {
		/**
		 * Adds a tokenization step to the distance.
		 * 
		 * @param tokenizer
		 *            a tokenizer to add
		 * @return a builder for fluent chaining
		 */
		@Override
		CollectionDistanceTokenizerStep<T> tokenize(Tokenizer tokenizer);

		/**
		 * Adds a filter step to the distance. All tokens that match the
		 * predicate are kept.
		 * 
		 * @param predicate
		 *            a predicate for tokens to keep
		 * @return this for fluent chaining
		 */
		CollectionDistanceTokenizerStep<T> filter(Predicate<String> predicate);

		/**
		 * Adds a transform step to the distance. All tokens are transformed by
		 * the function. The function may not return null.
		 * 
		 * @param function
		 *            a function to transform tokens
		 * @return this for fluent chaining
		 */
		CollectionDistanceTokenizerStep<T> transform(Function<String, String> function);

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
		 * Builds a string distance metric that will use the given
		 * simplification, tokenization and filtering steps.
		 * 
		 * @return a string distance metric
		 */
		@Override
		StringDistance build();

	}

	private static final class CompositeStringDistanceBuilder implements StringDistanceSimplifierStep {

		private final Distance<String> distance;

		private final List<Simplifier> simplifiers = new ArrayList<>();

		CompositeStringDistanceBuilder(Distance<String> distance) {
			checkNotNull(distance);
			this.distance = distance;
		}

		@Override
		public StringDistance build() {

			if (simplifiers.isEmpty()) {
				return create(distance);
			}
			return create(distance, chainSimplifiers());
		}

		private Simplifier chainSimplifiers() {
			final Simplifier simplifier = Simplifiers.chain(simplifiers);
			simplifiers.clear();

			return simplifier;
		}

		@Override
		public BuildStep cacheStrings(Cache<String, String> cache) {
			checkNotNull(cache);
			CachingSimplifier cachingSimplifier = new CachingSimplifier(cache, chainSimplifiers());
			this.simplifiers.add(cachingSimplifier);
			return this;
		}

		@Override
		public StringDistanceSimplifierStep simplify(Simplifier simplifier) {
			checkNotNull(simplifier);
			this.simplifiers.add(simplifier);
			return this;
		}

	}

	private static abstract class CompositeCollectionDistanceBuilder<T extends Collection<String>>
			implements CollectionDistanceSimplifierStep<T>, CollectionDistanceTokenizerStep<T> {

		private final Distance<T> distance;

		private final List<Simplifier> simplifiers = new ArrayList<>();
		private final List<Tokenizer> tokenizers = new ArrayList<>();

		CompositeCollectionDistanceBuilder(Distance<T> distance) {
			checkNotNull(distance);
			this.distance = distance;
		}

		@Override
		public final StringDistance build() {

			Tokenizer tokenizer = chainTokenizers();

			if (simplifiers.isEmpty()) {
				return build(distance, tokenizer);
			}

			return build(distance, chainSimplifiers(), tokenizer);
		}

		abstract StringDistance build(Distance<T> distance, Simplifier simplifier, Tokenizer tokenizer);

		abstract StringDistance build(Distance<T> distance, Tokenizer tokenizer);

		@Override
		public final BuildStep cacheTokens(Cache<String, T> cache) {
			checkNotNull(cache);
			tokenizers.add(createCachingTokenizer(cache, chainTokenizers()));
			return this;
		}

		protected abstract Tokenizer createCachingTokenizer(Cache<String, T> cache, Tokenizer tokenizer);

		@Override
		public final CollectionDistanceInitialTokenizerStep<T> cacheStrings(Cache<String, String> cache) {
			checkNotNull(cache);

			CachingSimplifier cachingSimplifier = new CachingSimplifier(cache, chainSimplifiers());
			this.simplifiers.add(cachingSimplifier);

			return this;
		}

		@Override
		public final CollectionDistanceSimplifierStep<T> simplify(Simplifier simplifier) {
			checkNotNull(simplifier);
			simplifiers.add(simplifier);
			return this;
		}

		@Override
		public final CollectionDistanceTokenizerStep<T> tokenize(Tokenizer tokenizer) {
			checkNotNull(tokenizer);
			tokenizers.add(tokenizer);
			return this;
		}

		@Override
		public final CollectionDistanceTokenizerStep<T> filter(Predicate<String> predicate) {
			checkNotNull(predicate);

			final Tokenizer filter = Tokenizers.filter(chainTokenizers(), predicate);

			tokenizers.add(filter);

			return this;
		}

		@Override
		public final CollectionDistanceTokenizerStep<T> transform(Function<String, String> function) {
			checkNotNull(function);
			final Tokenizer transform = Tokenizers.transform(chainTokenizers(), function);
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

	private static final class CompositeListDistanceBuilder extends CompositeCollectionDistanceBuilder<List<String>> {

		CompositeListDistanceBuilder(Distance<List<String>> distance) {
			super(distance);
		}

		@Override
		StringDistance build(Distance<List<String>> distance, Simplifier simplifier, Tokenizer tokenizer) {
			return createForListDistance(distance, simplifier, tokenizer);
		}

		@Override
		StringDistance build(Distance<List<String>> distance, Tokenizer tokenizer) {
			return createForListDistance(distance, tokenizer);
		}

		@Override
		protected Tokenizer createCachingTokenizer(Cache<String, List<String>> cache, Tokenizer tokenizer) {
			return new CachingListTokenizer(cache, tokenizer);
		}

	}

	private static final class CompositeSetDistanceBuilder extends CompositeCollectionDistanceBuilder<Set<String>> {

		CompositeSetDistanceBuilder(Distance<Set<String>> distance) {
			super(distance);
		}

		@Override
		StringDistance build(Distance<Set<String>> distance, Simplifier simplifier, Tokenizer tokenizer) {
			return createForSetDistance(distance, simplifier, tokenizer);
		}

		@Override
		StringDistance build(Distance<Set<String>> distance, Tokenizer tokenizer) {
			return createForSetDistance(distance, tokenizer);
		}

		@Override
		protected Tokenizer createCachingTokenizer(Cache<String, Set<String>> cache, Tokenizer tokenizer) {
			return new CachingSetTokenizer(cache, tokenizer);
		}

	}

	private static final class CompositeMultisetDistanceBuilder
			extends CompositeCollectionDistanceBuilder<Multiset<String>> {

		CompositeMultisetDistanceBuilder(Distance<Multiset<String>> distance) {
			super(distance);
		}

		@Override
		StringDistance build(Distance<Multiset<String>> distance, Simplifier simplifier, Tokenizer tokenizer) {
			return createForMultisetDistance(distance, simplifier, tokenizer);
		}

		@Override
		StringDistance build(Distance<Multiset<String>> distance, Tokenizer tokenizer) {
			return createForMultisetDistance(distance, tokenizer);
		}

		@Override
		protected Tokenizer createCachingTokenizer(Cache<String, Multiset<String>> cache, Tokenizer tokenizer) {
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

		CachingMultisetTokenizer(Cache<String, Multiset<String>> cache, Tokenizer tokenizer) {
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
			return "CachingMultisetTokenizer [" + cache + ", " + tokenizer + "]";
		}
	}

	static final class CachingSetTokenizer implements Tokenizer {

		private final Cache<String, Set<String>> cache;
		final Tokenizer tokenizer;

		CachingSetTokenizer(Cache<String, Set<String>> cache, Tokenizer tokenizer) {
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

		CachingListTokenizer(Cache<String, List<String>> cache, Tokenizer tokenizer) {
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
