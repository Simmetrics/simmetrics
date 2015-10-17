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

package org.simmetrics.utils;

import java.util.concurrent.ExecutionException;

import org.simmetrics.StringMetricBuilder;
import org.simmetrics.simplifiers.Simplifier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Simplifier that caches simplified results. Can be used to improve performance
 * when repeatedly processing identical strings. Least used cache entries are
 * evicted once the cache reaches its maximum size.
 * <p>
 * This class is thread-safe provided its components are and
 * {@link CachingSimplifier#setSimplifier(Simplifier)} is not used in a
 * concurrent context.
 * 
 * @see StringMetricBuilder
 */
@Deprecated
public class CachingSimplifier implements SimplifyingSimplifier {

	private Simplifier simplifier;

	private final LoadingCache<String, String> cache;

	/**
	 * Creates a caching simplifier with {@code initialCapacity} and
	 * {@code maximumSize}. Least used cache entries are evicted once the cache
	 * reaches its maximum size.
	 * <p>
	 * Uses the delegated simplifier to perform the simplification.
	 * 
	 * @param initialCapacity
	 *            initial size of the cache
	 * @param maximumSize
	 *            maximum size of the cache
	 *
	 * @param simplifier
	 *            the delegate simplifier
	 */
	public CachingSimplifier(int initialCapacity, int maximumSize,
			Simplifier simplifier) {
		this(initialCapacity, maximumSize);
		this.simplifier = simplifier;
	}

	/**
	 * Creates a caching simplifier with {@code initialCapacity} and
	 * {@code maximumSize}. Least used cache entries are evicted once the cache
	 * reaches its maximum size.
	 * <p>
	 * Note: A delegated simplifier must be set through through
	 * {@link CachingSimplifier#setSimplifier(Simplifier)}
	 * 
	 * @param initialCapacity
	 *            initial size of the cache
	 * @param maximumSize
	 *            maximum size of the cache
	 */
	public CachingSimplifier(int initialCapacity, int maximumSize) {
		this.cache = CacheBuilder.newBuilder().initialCapacity(initialCapacity)
				.maximumSize(maximumSize)
				.build(new CacheLoader<String, String>() {

					@Override
					public String load(String key) throws Exception {
						return getSimplifier().simplify(key);
					}

				});
	}

	@Override
	public Simplifier getSimplifier() {
		return simplifier;
	}

	@Override
	public void setSimplifier(Simplifier simplifier) {
		this.simplifier = simplifier;
	}

	@Override
	public String simplify(String input) {
		try {
			return cache.get(input);
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
