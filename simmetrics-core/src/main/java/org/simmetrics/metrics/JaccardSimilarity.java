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
package org.simmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

/**
 * Implements the Jaccard Similarity algorithm providing a similarity measure
 * between two sets.
 * <p>
 * Each instance is represented as a Jaccard vector similarity function. The
 * Jaccard between two vectors X and Y is (X*Y) / (|X||Y|-(X*Y)) where (X*Y) is
 * the inner product of X and Y, and |X| = (X*X)^1/2, i.e. the Euclidean norm of
 * X.
 * <p>
 * This can more easily be described as <code>( |X & Y| ) / ( | X or Y | )</code>
 * 
 * 
 * @author Sam Chapman
 * 
 * @param <T>
 *            type of the token
 * 
 */
public final class JaccardSimilarity<T> implements SetMetric<T> {

	@Override
	public float compare(Set<T> a, Set<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		final Set<T> all = new HashSet<>();
		all.addAll(a);
		all.addAll(b);

		final int common = (a.size() + b.size()) - all.size();

		// return JaccardSimilarity
		return (float) (common) / (float) (all.size());
	}

	@Override
	public String toString() {
		return "JaccardSimilarity";
	}

}
