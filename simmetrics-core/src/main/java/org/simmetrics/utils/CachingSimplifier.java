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
						Simplifier s = getSimplifier();
						if (s == null) {
							throw new NullPointerException(
									"No simplifier was set");
						}
						return s.simplify(key);
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
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String toString() {
		return "CachingSimplifier [" + simplifier + "]";
	}

}
