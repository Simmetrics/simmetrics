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

		final int total = a.size() + b.size();
		final Set<T> union = new HashSet<>(total);
		union.addAll(a);
		union.addAll(b);

		final int intersection = total - union.size();
		// (|q & r|) / min{|q|, |r|}
		return intersection / (float) min(a.size(), b.size());
	}

	@Override
	public String toString() {
		return "OverlapCoefficient";
	}

}
