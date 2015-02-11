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

package org.simmetrics;

/**
 * Measures the similarity between two strings. The measurement results in a
 * value between 0 and 1 (inclusive). A value of zero indicates that the strings are
 * dissimilar, a a value of 1 indicates they are similar.
 * 
 * @author mpkorstanje
 *
 */

public interface StringMetric {
	/**
	 * Measures the similarity between strings a and b. The measurement results
	 * in a value between 0 and 1 (inclusive). A value of zero indicates that the strings
	 * are dissimilar, a value of 1 indicates they are similar.
	 * 
	 * @param a
	 *            string a to compare
	 * @param b
	 *            string b to compare
	 * @return a value between 0 and 1 inclusive indicating similarity
	 */
	public float compare(String a, String b);

}
