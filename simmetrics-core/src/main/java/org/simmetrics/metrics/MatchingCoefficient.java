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

import java.util.ArrayList;
import java.util.List;

import org.simmetrics.ListMetric;

import static java.lang.Math.max;

/**
 * Implements the Matching Coefficient algorithm providing a similarity measure
 * between two lists.
 * 
 * @author Sam Chapman
 * 
 * @param <T>
 *            type of the token
 * 
 */
public class MatchingCoefficient<T> implements ListMetric<T> {
	@Override
	public float compare(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		// Copy for destructive list difference
		b = new ArrayList<>(b);
		int union = a.size() + b.size();

		// Count elements in the list intersection.
		// Elements are counted only once in both lists.
		// E.g. the intersection of [ab,ab,ab] and [ab,ab,ac,ad] is [ab,ab].
		// Note: this is not the same as b.retainAll(a).size()
		int intersection = 0;
		
		for (T token : a) {
			if (b.remove(token)) {
				intersection++;
			}
		}

		// matching co-efficient as common elements  / (common elements + non-common elements )
		return intersection / (float)(union - intersection) ;
	}

	@Override
	public String toString() {
		return "MatchingCoefficient";
	}

}
