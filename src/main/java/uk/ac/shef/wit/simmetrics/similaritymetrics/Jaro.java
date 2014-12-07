/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package uk.ac.shef.wit.simmetrics.similaritymetrics;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;

/**
 * Implements the Jaro algorithm providing a similarity measure between two
 * strings allowing character transpositions to a degree
 *
 * @author Sam Chapman
 * @version 1.1
 */
public final class Jaro extends AbstractStringMetric   {


	private final float ESTIMATEDTIMINGCONST = 4.12e-5f;

	public String getLongDescriptionString() {
		return "Implements the Jaro algorithm providing a similarity measure between two strings allowing character transpositions to a degree";
	}
	@Deprecated
	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {
		
		final float str1Length = string1.length();
		final float str2Length = string2.length();
		return (str1Length * str2Length) * ESTIMATEDTIMINGCONST;
	}

	
	public float getSimilarity(final String string1, final String string2) {

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

	public float getUnNormalisedSimilarity(String string1, String string2) {
		// TODO: should check this is correct (think normal metric is 0-1 scaled
		// but unsure)
		return getSimilarity(string1, string2);
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
}
