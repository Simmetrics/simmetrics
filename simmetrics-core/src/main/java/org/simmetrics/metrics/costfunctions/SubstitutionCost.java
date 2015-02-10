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

package org.simmetrics.metrics.costfunctions;

/**
 * SubstitutionCost is an interface for a cost function <code>d(i,j)</code>.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public interface SubstitutionCost {

	/**
	 * Get cost between characters.
	 *
	 * @param str1
	 *            - the string1 to evaluate the cost
	 * @param string1Index
	 *            - the index within the string1 to test
	 * @param str2
	 *            - the string2 to evaluate the cost
	 * @param string2Index
	 *            - the index within the string2 to test
	 *
	 * @return the cost of a given substitution <code>d(i,j)</code>
	 */
	public float getCost(String str1, int string1Index, String str2,
			int string2Index);

	/**
	 * Returns the maximum possible cost.
	 *
	 * @return the maximum possible cost
	 */
	public float getMaxCost();

	/**
	 * Returns the minimum possible cost.
	 *
	 * @return the minimum possible cost
	 */
	public float getMinCost();
}
