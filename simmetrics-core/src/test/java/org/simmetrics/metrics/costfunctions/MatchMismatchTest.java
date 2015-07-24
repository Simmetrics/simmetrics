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
package org.simmetrics.metrics.costfunctions;

import org.simmetrics.metrics.functions.MatchMismatch;
import org.simmetrics.metrics.functions.Substitution;

@SuppressWarnings("javadoc")
public class MatchMismatchTest extends SubstitutionTest {
	
	@Override
	public Substitution getCost() {
		return new MatchMismatch(1.0f, -0.25f);
	}

	@Override
	public T[] getTests() {
		return new T[]{
				new T(1.000f, "a", 0, "a", 0),
				new T(-0.25f, "a", 0, "b", 0),
				new T(1.000f, "ab", 0, "ba", 1),
				new T(-0.25f, "ab", 1, "ba", 1),
				
		};
	}

}
