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

import static java.lang.Math.min;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

/**
 * Overlap Coefficient algorithm providing a similarity measure between two sets
 * where it is determined to what degree a set is a subset of another.
 * <p>
 * <code>overlap_coefficient(q,r) = (|q & r|) / min{|q|, |r|}</code>
 * <p>
 * Related to the Jaccard index.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @param <T>
 *            type of the token
 * 
 * @see JaccardSimilarity
 * @see <a href="http://en.wikipedia.org/wiki/Overlap_coefficient">Wikipedia -
 *      Overlap Coefficient</a>
 */
public final class OverlapCoefficient<T> implements SetMetric<T> {

	@Override
	public float compare(Set<T> a, Set<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		final Set<T> all = new HashSet<>(a.size() + b.size());
		all.addAll(a);
		all.addAll(b);

		final int intersection = a.size() + b.size() - all.size();
		return intersection / (float) min(a.size(), b.size());
	}

	@Override
	public String toString() {
		return "OverlapCoefficient";
	}

}
