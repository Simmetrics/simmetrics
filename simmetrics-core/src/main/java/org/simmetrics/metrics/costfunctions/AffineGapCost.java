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
 * InterfaceAffineGapCost defines an Interface for AffineGapCost functions to be
 * interchanged.
 *
 * @author Sam Chapman
 * @version 1.1
 */
public interface AffineGapCost {



	/**
	 * Get cost between characters.
	 *
	 * @param stringToGap
	 *            - the string to get the cost of a gap
	 * @param stringIndexStartGap
	 *            - the index within the string to test a start gap from
	 * @param stringIndexEndGap
	 *            - the index within the string to test a end gap to
	 *
	 * @return the cost of a Gap G
	 */
	public float getCost(String stringToGap, int stringIndexStartGap,
			int stringIndexEndGap);

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
