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

import static java.lang.Math.max;
import static java.lang.Math.min;

import org.simmetrics.StringMetric;

/**
 * Jaro algorithm providing a similarity measure between two strings.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">Wikipedia
 *      - Jaro-Winkler distance</a>
 * @see JaroWinkler
 *
 *
 *
 */
public class Jaro implements StringMetric {

	@Override
	public float compare(final String a, final String b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		// Intentional integer division to round down.
		final int halfLength = max(0, max(a.length(), b.length()) / 2 - 1);

		// Second argument of getCommonCharacters is modified. So we can reuse
		// the first argument.
		final char[] charsA = a.toCharArray();
		final char[] commonA = getCommonCharacters(charsA, b.toCharArray(),
				halfLength);
		final char[] commonB = getCommonCharacters(b.toCharArray(), charsA,
				halfLength);

		if (commonA.length == 0 || commonB.length == 0) {
			return 0.0f;
		}

		// commonA and commonB will always contain the same multi-set of
		// characters. Because getCommonCharacters has been optimized, commonA
		// and commonB are zero-padded. So in this loop we count transposition 
		// and use commonCharacters to determine the length of the multi-set.
		float transpositions = 0;
		int commonCharacters = 0;
		for (int length = commonA.length; commonCharacters < length
				&& commonA[commonCharacters] > 0; commonCharacters++) {
			if (commonA[commonCharacters] != commonB[commonCharacters]) {
				transpositions++;
			}
		}
		float aCommonRatio = commonCharacters / (float) a.length();
		float bCommonRatio = commonCharacters / (float) b.length();
		float transpositionRatio = (commonCharacters - transpositions / 2.0f)
				/ commonCharacters;

		return (aCommonRatio + bCommonRatio + transpositionRatio) / 3.0f;
	}

	/*
	 * Returns an array of characters from a within b. A character in b is
	 * counted as common when it is within separation distance from the position
	 * in a.
	 */
	private static char[] getCommonCharacters(final char[] charsA,
			final char[] charsB, final int separation) {
		final char[] common = new char[min(charsA.length, charsB.length)];

		// Iterate of string a and find all characters that occur in b within
		// the separation distance. Zero out any matches found to avoid
		// duplicate matchings.
		int commonIndex = 0;
		for (int i = 0, length = charsA.length; i < length; i++) {
			final char character = charsA[i];

			int index = indexOf(character, charsB, i - separation, i + separation + 1);
			if (index > -1) {
				common[commonIndex++] = character;
				charsB[index] = (char) 0;
			}
		}

		// Both invocations will yield the same multi-set with the same amount
		// of zero-padding, so they can be compared for transposition without
		// making a copy.
		return common;
	}

	/*
	 * Search for character in buffer starting at fromIndex to toIndex - 1.
	 * 
	 * Returns -1 when not found.
	 */
	private static int indexOf(char character, char[] buffer, int fromIndex,
			int toIndex) {

		// compare char with range of characters to either side
		for (int j = max(0, fromIndex), length = min(toIndex, buffer.length); j < length; j++) {
			// check if found
			if (buffer[j] == character) {
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
