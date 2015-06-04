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
package org.simmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

import static java.lang.Math.sqrt;

/**
 * Cosine Similarity algorithm providing a similarity measure between two set
 * from the angular divergence within token based vector space.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Cosine_similarity">Wikipedia
 *      Cosine similarity</a>
 
 * @param <T>
 *            type of the token
 */
public class CosineSimilarity<T> implements SetMetric<T> {

	@Override
	public float compare(Set<T> a, Set<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		final Set<Object> all = new HashSet<>(a.size() + b.size());
		all.addAll(a);
		all.addAll(b);

		// Implementation note: Dot product of two binary vectors is the
		// intersection of two sets
		final int commonTerms = (a.size() + b.size()) - all.size();

		// Implementation note: Magnitude of a binary vectors is sqrt of its
		// size.
		return (float) (commonTerms / (sqrt(a.size()) * sqrt(b.size())));
	}

	@Override
	public String toString() {
		return "CosineSimilarity";
	}

}
