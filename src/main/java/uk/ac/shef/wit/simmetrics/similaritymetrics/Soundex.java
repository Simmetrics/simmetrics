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
 * Implements the Soundex algorithm providing a similarity measure between two
 * soundex codes.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class Soundex extends AbstractStringMetric   {

	private final float ESTIMATEDTIMINGCONST = 0.00052f;

	private final AbstractStringMetric internalStringMetric;

	/**
	 * Defines the soundex length in characters e.g. S-2433 is 6 long.
	 */
	private final static int SOUNDEXLENGTH = 6;

	/**
	 * Constructs a Soundex metric with a {@link JaroWinkler} metric to compare
	 * soundex strings.
	 */
	public Soundex() {
		internalStringMetric = new JaroWinkler();
	}

	/**
	 * Constructs a Soundex metric with a {@link JaroWinkler} metric to compare
	 * soundex strings.
	 * 
	 * @param metric
	 *            the metric used to compare two soundex strings
	 */
	public Soundex(final AbstractStringMetric metric) {
		internalStringMetric = metric;
	}

	public String getLongDescriptionString() {
		return "Implements the Soundex algorithm providing a similarity measure between two soundex codes";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {
		final float str1Length = string1.length();
		final float str2Length = string2.length();
		final String testString = "abcdefghijklmnopq";
		return ((str1Length + str2Length) * ESTIMATEDTIMINGCONST)
				+ internalStringMetric.getSimilarityTimingEstimated(
						testString.substring(0, SOUNDEXLENGTH),
						testString.substring(0, SOUNDEXLENGTH));
	}

	public float getSimilarity(final String string1, final String string2) {
		final String soundex1 = calcSoundEx(string1, SOUNDEXLENGTH);
		final String soundex2 = calcSoundEx(string2, SOUNDEXLENGTH);
		// convert into zero to one return using attached string metric to score
		// comparison
		return internalStringMetric.getSimilarity(soundex1, soundex2);
	}

	public float getUnNormalisedSimilarity(String string1, String string2) {
		// TODO: Why is soundex not calculated here?
		return internalStringMetric.getUnNormalisedSimilarity(string1, string2);
	}

	/**
	 * calculates a soundex code for a given string/name.
	 *
	 * @param wordString
	 * @param soundExLen
	 *            - the length of the soundex code to create
	 * @return a soundex code for a given string/name
	 */
	private static String calcSoundEx(String wordString, int soundExLen) {
		String tmpStr;
		String wordStr;
		char curChar;
		char lastChar;
		final int wsLen;
		final char firstLetter;

		// ensure soundexLen is in a valid range
		if (soundExLen > 10) {
			soundExLen = 10;
		}
		if (soundExLen < 4) {
			soundExLen = 4;
		}

		// check for empty input
		if (wordString.length() == 0) {
			return ("");
		}

		// remove case
		wordString = wordString.toUpperCase();

		/*
		 * Clean and tidy
		 */
		wordStr = wordString;
		wordStr = wordStr.replaceAll("[^A-Z]", " "); // rpl non-chars w space
		wordStr = wordStr.replaceAll("\\s+", ""); // remove spaces

		// check for empty input again the previous clean and tidy could of
		// shrunk it to zero.
		if (wordStr.length() == 0) {
			return ("");
		}

		/*
		 * The above improvements may change this first letter
		 */
		firstLetter = wordStr.charAt(0);

		// uses the assumption that enough valid characters are in the first 4
		// times the soundex required length
		if (wordStr.length() > (SOUNDEXLENGTH * 4) + 1) {
			wordStr = "-" + wordStr.substring(1, SOUNDEXLENGTH * 4);
		} else {
			wordStr = "-" + wordStr.substring(1);
		}
		// Begin Classic SoundEx
		// 1 <- B,P,F,V
		// 2 <- C,S,K,G,J,Q,X,Z
		// 3 <- D,T
		// 4 <- L
		// 5 <- M,N
		// 6 <- R

		wordStr = wordStr.replaceAll("[AEIOUWH]", "0");
		wordStr = wordStr.replaceAll("[BPFV]", "1");
		wordStr = wordStr.replaceAll("[CSKGJQXZ]", "2");
		wordStr = wordStr.replaceAll("[DT]", "3");
		wordStr = wordStr.replaceAll("[L]", "4");
		wordStr = wordStr.replaceAll("[MN]", "5");
		wordStr = wordStr.replaceAll("[R]", "6");

		// Remove extra equal adjacent digits
		wsLen = wordStr.length();
		lastChar = '-';
		tmpStr = "-"; /* replacing skipped first character */
		for (int i = 1; i < wsLen; i++) {
			curChar = wordStr.charAt(i);
			if (curChar != lastChar) {
				tmpStr += curChar;
				lastChar = curChar;
			}
		}
		wordStr = tmpStr;
		wordStr = wordStr.substring(1); /* Drop first letter code */
		wordStr = wordStr.replaceAll("0", ""); /* remove zeros */
		wordStr += "000000000000000000"; /* pad with zeros on right */
		wordStr = firstLetter + "-" + wordStr; /* Add first letter of word */
		wordStr = wordStr.substring(0, soundExLen); /* size to taste */
		return (wordStr);
	}
}
