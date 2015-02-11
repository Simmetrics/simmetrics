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

import org.simmetrics.StringMetric;


/**
 * Implements the Jaro algorithm providing a similarity measure between two
 * strings allowing character transpositions to a degree.
 *
 * @author Sam Chapman
 * @version 1.1
 */
public  class Jaro implements StringMetric {


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
		final int halflen = ((Math.min(string1.length(), string2.length())) / 2)
				+ ((Math.min(string1.length(), string2.length())) % 2);

		// get common characters
		final StringBuffer common1 = getCommonCharacters(string1, string2,
				halflen);
		final StringBuffer common2 = getCommonCharacters(string2, string1,
				halflen);

		// check for zero in common
		if (common1.length() == 0 || common2.length() == 0) {
			return 0.0f;
		}

		// check for same length common strings returning 0.0f is not the same
		if (common1.length() != common2.length()) {
			return 0.0f;
		}

		// get the number of transpositions
		int transpositions = 0;
		for (int i = 0; i < common1.length(); i++) {
			if (common1.charAt(i) != common2.charAt(i))
				transpositions++;
		}
		transpositions /= 2.0f;

		// calculate jaro metric
		return (common1.length() / ((float) string1.length())
				+ common2.length() / ((float) string2.length()) + (common1
				.length() - transpositions) / ((float) common1.length())) / 3.0f;
	}

	/**
	 * returns a string buffer of characters from string1 within string2 if they
	 * are of a given distance seperation from the position in string1.
	 *
	 * @param string1
	 * @param string2
	 * @param distanceSep
	 * @return a string buffer of characters from string1 within string2 if they
	 *         are of a given distance seperation from the position in string1
	 */
	private static StringBuffer getCommonCharacters(final String string1,
			final String string2, final int distanceSep) {
		// create a return buffer of characters
		final StringBuffer returnCommons = new StringBuffer();
		// create a copy of string2 for processing
		final StringBuffer copy = new StringBuffer(string2);
		// iterate over string1
		for (int i = 0; i < string1.length(); i++) {
			final char ch = string1.charAt(i);
			// set boolean for quick loop exit if found
			boolean foundIt = false;
			// compare char with range of characters to either side
			for (int j = Math.max(0, i - distanceSep); !foundIt
					&& j < Math.min(i + distanceSep, string2.length() - 1); j++) {
				// check if found
				if (copy.charAt(j) == ch) {
					foundIt = true;
					// append character found
					returnCommons.append(ch);
					// alter copied string2 for processing
					copy.setCharAt(j, (char) 0);
				}
			}
		}
		return returnCommons;
	}
	@Override
	public String toString() {
		return "Jaro";
	}

}
