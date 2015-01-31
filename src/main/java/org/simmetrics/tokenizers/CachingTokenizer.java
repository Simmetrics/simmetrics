/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */


package org.simmetrics.tokenizers;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CachingTokenizer implements TokenizingTokenizer {

	private static final int CACHE_SIZE = 2;

	private Tokenizer tokenizer;

	private final LoadingCache<String, List<String>> arrayCache = CacheBuilder
			.newBuilder().initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE)
			.build(new CacheLoader<String, List<String>>() {

				@Override
				public List<String> load(String key) throws Exception {
					return getTokenizer().tokenizeToList(key);
				}

			});

	private final LoadingCache<String, Set<String>> setCache = CacheBuilder
			.newBuilder().initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE)
			.build(new CacheLoader<String, Set<String>>() {

				@Override
				public Set<String> load(String key) throws Exception {
					return getTokenizer().tokenizeToSet(key);
				}

			});

	public CachingTokenizer() {
		
	}

	public CachingTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public LoadingCache<String, List<String>> getArrayCache() {
		return arrayCache;
	}

	public LoadingCache<String, Set<String>> getSetCache() {
		return setCache;
	}

	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public List<String> tokenizeToList(final String input) {

		try {
			// Return copy of list to preserve state of cached version. Callers
			// may modify the list.
			return new ArrayList<>(arrayCache.get(input));
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	public Set<String> tokenizeToSet(final String input) {
		try {
			// Return copy of set to preserve state of cached set. Callers
			// may modify the set.
			return new HashSet<>(setCache.get(input));
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String toString() {
		return "CachingTokenizer [" + tokenizer + "]";
	}

}
