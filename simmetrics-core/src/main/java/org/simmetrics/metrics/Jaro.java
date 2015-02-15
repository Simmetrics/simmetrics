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

import static java.lang.Math.floor;
import static java.lang.Math.max;

import org.simmetrics.StringMetric;

/**
 * Implements the Jaro algorithm providing a similarity measure between two
 * strings allowing character transpositions to a degree.
 *
 * @author Sam Chapman
 * @version 1.1
 */
public class Jaro implements StringMetric {

	@Override
	public float compare(final String string1, final String string2) {

		if (string1.isEmpty() && string2.isEmpty()) {
			return 1.0f;
		}

		if (string1.isEmpty() || string2.isEmpty()) {
			return 0.0f;
		}

		// get half the length of the string rounded up - (this is the distance
		// used for acceptable transpositions)
		final int halflen = max(0,
				(int) floor(-1 + 0.5 * max(string1.length(), string2.length())));

		// get common characters
		final String common1 = getCommonCharacters(string1, string2, halflen);
		final String common2 = getCommonCharacters(string2, string1, halflen);

		// check for zero in common
		if (common1.length() == 0 || common2.length() == 0) {
			return 0.0f;
		}

		// get the number of transpositions
		float transpositions = 0;
		for (int i = 0; i < common1.length(); i++) {
			if (common1.charAt(i) != common2.charAt(i))
				transpositions++;
		}
		transpositions /= 2.0f;

		float string1Common = common1.length() / (float) string1.length();
		float string2Common = common2.length() / (float) string2.length();
		float transpositionRatio = (common1.length() - transpositions)
				/ common1.length();

		return (string1Common + string2Common + transpositionRatio) / 3.0f;
	}

	/**
	 * returns a string buffer of characters from <code>a</code> within
	 * <code>b</code> if they are of a given distance <code>separation</code>
	 * from the position in <code>a</code>.
	 *
	 * @param a
	 * @param b
	 * @param separation
	 * @return a string buffer of characters from a within b
	 */
	private static String getCommonCharacters(final String a, final String b,
			final int separation) {
		final StringBuilder common = new StringBuilder(a.length() + b.length());
		final StringBuilder copyOfB = new StringBuilder(b);

		// Iterate of string a and find all characters that occur in b within
		// the separation distance. Zero out any matches found to avoid
		// duplicate matchings.
		for (int i = 0; i < a.length(); i++) {
			final char character = a.charAt(i);

			int index = indexOf(character, copyOfB, i - separation, i
					+ separation + 1);

			if (index > -1) {
				common.append(character);
				copyOfB.setCharAt(index, (char) 0);
			}

		}
		return common.toString();
	}

	/**
	 * Search for <code>character</code> in <code>buffer</code> starting
	 * <code>fromIndex<code> to <code>toIndex - 1</code>. Returns -1 when not
	 * found.
	 * 
	 * @param character
	 * @param buffer
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	private static int indexOf(char character, StringBuilder buffer,
			int fromIndex, int toIndex) {

		// compare char with range of characters to either side
		for (int j = Math.max(0, fromIndex); j < Math.min(toIndex,
				buffer.length()); j++) {
			// check if found
			if (buffer.charAt(j) == character) {
				return j;
			}
		}

		return -1;
	}

	@Override
	public String toString() {
		return "Jaro";
	}

}
