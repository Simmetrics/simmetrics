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
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.utils;

import java.util.concurrent.ExecutionException;

import org.simmetrics.simplifiers.Simplifier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CachingSimplifier implements SimplifyingSimplifier {


	private Simplifier simplifier;

	private final LoadingCache<String, String> cache;

	public CachingSimplifier(int initialCapacity,
			int maximumSize) {
		this.cache = CacheBuilder.newBuilder()
				.initialCapacity(initialCapacity)
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
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String toString() {
		return "CachingSimplifier [" + simplifier + "]";
	}

}
