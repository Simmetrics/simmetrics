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

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

/**
 * Jaccard similarity algorithm providing a similarity measure between two sets
 * using the vector space of presented tokens.
 * 
 * <p>
 * <code>jaccard index(a,b) = ( |a & b| ) / ( | a or b | )</code>
 * <p>
 * 
 * This metric is identical to the matching coefficient which operates on lists.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Jaccard_index">Wikipedia - Jaccard
 *      index</a>
 * 
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
		
		final int total = a.size() + b.size();
		final Set<T> union = new HashSet<>(total);
		union.addAll(a);
		union.addAll(b);

		final int intersection = total - union.size();

		//  ( |a & b| ) / ( | a or b | )
		return intersection / (float) union.size();
	}

	@Override
	public String toString() {
		return "JaccardSimilarity";
	}

}
