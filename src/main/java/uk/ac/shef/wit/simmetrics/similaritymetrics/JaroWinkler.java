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
import uk.ac.shef.wit.simmetrics.utils.Math;

/**
 * Implements the Jaro-Winkler algorithm providing a similarity measure between
 * two strings allowing character transpositions to a degree adjusting the
 * weighting for common prefixes.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class JaroWinkler extends AbstractStringMetric {

	private final float ESTIMATEDTIMINGCONST = 4.342e-5f;

	private final AbstractStringMetric jaro = new Jaro();

	private static final int MINPREFIXTESTLENGTH = 6;

	private static final float PREFIXADUSTMENTSCALE = 0.1f;

	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Jaro-Winkler algorithm providing a similarity measure between two strings allowing character transpositions to a degree adjusting the weighting for common prefixes";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Length = string1.length();
		final float str2Length = string2.length();
		return (str1Length * str2Length) * ESTIMATEDTIMINGCONST;
	}

	public float getSimilarity(final String string1, final String string2) {
		// gets normal Jaro Score
		final float dist = jaro.getSimilarity(string1, string2);

		// This extension modifies the weights of poorly matching pairs string1,
		// string2 which share a common prefix
		final int prefixLength = getPrefixLength(string1, string2);
		return dist
				+ ((float) prefixLength * PREFIXADUSTMENTSCALE * (1.0f - dist));
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
		final int n = Math.min3(MINPREFIXTESTLENGTH, string1.length(),
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
}
