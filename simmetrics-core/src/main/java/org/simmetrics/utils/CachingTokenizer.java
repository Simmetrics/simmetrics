/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


package org.simmetrics.utils;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.simmetrics.StringMetricBuilder;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Tokenizer that caches tokenized results. Can be used to improve performance
 * when repeatedly processing identical strings. Least used cache entries are
 * evicted once the cache reaches its maximum size.
 * <p>
 * This class is thread-safe provided its components are and the
 * {@link CachingTokenizer#setTokenizer(Tokenizer)} is not called in a
 * concurrent context.
 * 
 * @see StringMetricBuilder
 */
public class CachingTokenizer implements TokenizingTokenizer {

	private Tokenizer tokenizer;

	private final LoadingCache<String, List<String>> arrayCache;

	private final LoadingCache<String, Set<String>> setCache;

//	private final LoadingCache<String, Multiset<String>> multisetCache;

	/**
	 * Creates a caching tokenizer with {@code initialCapacity} and
	 * {@code maximumSize}. Least used cache entries are evicted once the cache
	 * reaches its maximum size.
	 * <p>
	 * Note: A delegated tokenizer must be set through through
	 * {@link CachingTokenizer#setTokenizer(Tokenizer)}
	 * 
	 * @param initialCapacity
	 *            initial size of the cache
	 * @param maximumSize
	 *            maximum size of the cache
	 *
	 */
	public CachingTokenizer(int initialCapacity, int maximumSize) {
		this.arrayCache = CacheBuilder.newBuilder()
				.initialCapacity(initialCapacity).maximumSize(maximumSize)
				.build(new CacheLoader<String, List<String>>() {

					@Override
					public List<String> load(String key) throws Exception {
						return unmodifiableList(getTokenizer().tokenizeToList(
								key));
					}

				});
		this.setCache = CacheBuilder.newBuilder()
				.initialCapacity(initialCapacity).maximumSize(maximumSize)
				.build(new CacheLoader<String, Set<String>>() {

					@Override
					public Set<String> load(String key) throws Exception {
						return unmodifiableSet(getTokenizer()
								.tokenizeToSet(key));
					}

				});
		
//		this.multisetCache = CacheBuilder.newBuilder()
//				.initialCapacity(initialCapacity).maximumSize(maximumSize)
//				.build(new CacheLoader<String, Multiset<String>>() {
//
//					@Override
//					public Multiset<String> load(String key) throws Exception {
//						return unmodifiableMultiset(getTokenizer().tokenizeToMultiset(key));
//					}
//
//				});
	}

	/**
	 * Creates a caching tokenizer with {@code initialCapacity} and
	 * {@code maximumSize}. Least used cache entries are evicted once the cache
	 * reaches its maximum size.
	 * <p>
	 * Uses the delegated tokenizer to perform the tokenization.
	 * 
	 * @param initialCapacity
	 *            initial size of the cache
	 * @param maximumSize
	 *            maximum size of the cache
	 *
	 * @param tokenizer
	 *            the delegate tokenizer
	 */
	public CachingTokenizer(int initialCapacity, int maximumSize,
			Tokenizer tokenizer) {
		this(initialCapacity, maximumSize);
		this.tokenizer = tokenizer;
	}

	@Override
	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	@Override
	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	@Override
	public List<String> tokenizeToList(final String input) {

		try {
			return arrayCache.get(input);
		} catch (ExecutionException e) {
			// Can't happen. Tokenizer may not throw checked exceptions
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Set<String> tokenizeToSet(final String input) {
		try {
			return setCache.get(input);
		} catch (ExecutionException e) {
			// Can't happen. Tokenizer may not throw checked exceptions
			throw new IllegalStateException(e);
		}
	}
	
//	@Override
//	public Multiset<String> tokenizeToMultiset(String input) {
//		try {
//			return multisetCache.get(input);
//		} catch (ExecutionException e) {
//			throw new IllegalStateException(e);
//		}
//	}

	@Override
	public String toString() {
		return "CachingTokenizer [" + tokenizer + "]";
	}



}
