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

package org.simmetrics.metrics.functions;

/**
 * A substitution function assigns value to the substitution of one character
 * for another matching against another string.
 * <p>
 * A positive value indicates the characters are similar, a negative value
 * indicates they are dissimilar.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see Gap
 * @see <a
 *      href="https://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm">Wikipedia
 *      - Smith–Waterman algorithm</a>
 *
 */
public interface Substitution {
	/**
	 * Rates the substitution of character <code>a.getChar(aIndex)</code>
	 * against <code>b.getChar(bIndex)</code>.
	 * 
	 * @param a
	 *            a string to check in
	 * @param aIndex
	 *            index of character at string <code>a</code> to compare
	 * @param b
	 *            another string to check in
	 * @param bIndex
	 *            index of character at string <code>b</code> to compare
	 * @return a score indicating the characters (dis) similarity
	 */
	public float compare(String a, int aIndex, String b, int bIndex);

	/**
	 * Returns the maximum value a gap can have
	 * 
	 * @return the maximum value a gap can have
	 */
	public float max();

	/**
	 * Returns the minimum value a gap can have
	 * 
	 * @return the minimum value a gap can have
	 */
	public float min();
}
