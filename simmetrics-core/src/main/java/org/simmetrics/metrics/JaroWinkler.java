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

import static org.simmetrics.utils.Math.min3;

import org.simmetrics.StringMetric;

/**
 * Implements the Jaro-Winkler algorithm providing a similarity measure between
 * two strings allowing character transpositions to a degree adjusting the
 * weighting for common prefixes.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class JaroWinkler implements StringMetric {

	private final Jaro jaro = new Jaro();

	private static final int MINPREFIXTESTLENGTH = 6;

	private static final float PREFIXADUSTMENTSCALE = 0.1f;

	@Override
	public float compare(final String string1, final String string2) {
		// gets normal Jaro Score
		final float dist = jaro.compare(string1, string2);

		// This extension modifies the weights of poorly matching pairs string1,
		// string2 which share a common prefix
		final int prefixLength = getPrefixLength(string1, string2);
		return dist + (prefixLength * PREFIXADUSTMENTSCALE * (1.0f - dist));
	}

	/**
	 * gets the prefix length found of common characters at the begining of the
	 * strings.
	 *
	 * @param string1
	 * @param string2
	 * @return the prefix length found of common characters at the begining of
	 *         the strings
	 */
	private static int getPrefixLength(final String string1,
			final String string2) {
		final int n = min3(MINPREFIXTESTLENGTH, string1.length(),
				string2.length());
		// check for prefix similarity of length n
		for (int i = 0; i < n; i++) {
			// check the prefix is the same so far
			if (string1.charAt(i) != string2.charAt(i)) {
				// not the same so return as far as got
				return i;
			}
		}
		return n; // first n characters are the same
	}

	@Override
	public String toString() {
		return "JaroWinkler";
	}
}
