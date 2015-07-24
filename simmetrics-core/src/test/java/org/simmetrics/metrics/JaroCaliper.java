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

import org.simmetrics.Metric;
import org.simmetrics.StringMetric;

import static java.lang.Math.*;
import static java.util.Arrays.copyOf;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;

@SuppressWarnings("javadoc")
public final class JaroCaliper {

	enum Value {
		Shakespear("This happy breed of men"), Rumi(
				"Sell your cleverness and buy bewilderment.");

		final String s;

		Value(String s) {
			this.s = s;
		}
	}

	enum Method {

		latest(new Jaro()), 
		v3_0_1(new JaroV3_0_1()),
		v3_0_2(new JaroV3_0_2()), 
		v3_0_3(new JaroV3_0_3());

		final Metric<String> metric;

		private Method(Metric<String> metric) {
			this.metric = metric;
		}
	}

	@Param
	Value a;

	@Param
	Value b;

	@Param
	Method method;

	@Benchmark
	float compare(int reps) {
		final Metric<String> m = method.metric;

		float dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += m.compare(a.s, b.s);
		}
		return dummy;
	}

	public static void main(String[] args) {
		CaliperMain.main(JaroCaliper.class, args);
	}
	
	private static class JaroV3_0_3 implements StringMetric {

		public JaroV3_0_3() {
		}

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

			// Second argument of getCommonCharacters is modified. So we can
			// reuse
			// the first argument.
			final char[] charsA = a.toCharArray();
			final char[] commonA = getCommonCharacters(charsA, b.toCharArray(),
					halfLength);
			final char[] commonB = getCommonCharacters(b.toCharArray(), charsA,
					halfLength);

			// commonA and commonB will always contain the same multi-set of
			// characters. Because getCommonCharacters has been optimized,
			// commonA
			// and commonB are zero-padded. So in this loop we count
			// transposition
			// and use commonCharacters to determine the length of the
			// multi-set.
			float transpositions = 0;
			int commonCharacters = 0;
			for (int length = commonA.length; commonCharacters < length
					&& commonA[commonCharacters] > 0; commonCharacters++) {
				if (commonA[commonCharacters] != commonB[commonCharacters]) {
					transpositions++;
				}
			}

			if (commonCharacters == 0) {
				return 0.0f;
			}

			float aCommonRatio = commonCharacters / (float) a.length();
			float bCommonRatio = commonCharacters / (float) b.length();
			float transpositionRatio = (commonCharacters - transpositions / 2.0f)
					/ commonCharacters;

			return (aCommonRatio + bCommonRatio + transpositionRatio) / 3.0f;
		}

		/*
		 * Returns an array of characters from a within b. A character in b is
		 * counted as common when it is within separation distance from the
		 * position in a.
		 */
		private static char[] getCommonCharacters(final char[] charsA,
				final char[] charsB, final int separation) {
			final char[] common = new char[min(charsA.length, charsB.length)];

			// Iterate of string a and find all characters that occur in b
			// within
			// the separation distance. Zero out any matches found to avoid
			// duplicate matchings.
			int commonIndex = 0;
			for (int i = 0, length = charsA.length; i < length; i++) {
				final char character = charsA[i];

				int index = indexOf(character, charsB, i - separation, i
						+ separation + 1);
				if (index > -1) {
					common[commonIndex++] = character;
					charsB[index] = (char) 0;
				}
			}

			// Both invocations will yield the same multi-set with the same
			// amount
			// of zero-padding, so they can be compared for transposition
			// without
			// making a copy.
			return common;
		}

		/*
		 * Search for character in buffer starting at fromIndex to toIndex - 1.
		 * 
		 * Returns -1 when not found.
		 */
		private static int indexOf(char character, char[] buffer,
				int fromIndex, int toIndex) {

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

	private static class JaroV3_0_2 implements StringMetric {

		public JaroV3_0_2() {
		}

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

			final char[] commonA = getCommonCharacters(a, b, halfLength);
			final char[] commonB = getCommonCharacters(b, a, halfLength);

			if (commonA.length == 0 || commonB.length == 0) {
				return 0.0f;
			}

			// Only need to check a single array for length. commonA and commonB
			// will always contain the same multi-set of characters
			float transpositions = 0;
			for (int i = 0, length = commonA.length; i < length; i++) {
				if (commonA[i] != commonB[i]) {
					transpositions++;
				}
			}
			transpositions /= 2.0f;

			float aCommonRatio = commonA.length / (float) a.length();
			float bCommonRatio = commonB.length / (float) b.length();
			float transpositionRatio = (commonA.length - transpositions)
					/ commonA.length;

			return (aCommonRatio + bCommonRatio + transpositionRatio) / 3.0f;
		}

		/*
		 * Returns a string of characters from a within b A character in b is
		 * counted as common when it is within separation distance from the
		 * position in a.
		 */
		private static char[] getCommonCharacters(final String a,
				final String b, final int separation) {
			final char[] charsA = a.toCharArray();
			final char[] charsB = b.toCharArray();
			final char[] common = new char[min(charsA.length, charsB.length)];

			// Iterate of string a and find all characters that occur in b
			// within
			// the separation distance. Zero out any matches found to avoid
			// duplicate matchings.
			int commonIndex = 0;
			for (int i = 0, length = charsA.length; i < length; i++) {
				final char character = charsA[i];

				int index = indexOf(character, charsB, i - separation, i
						+ separation + 1);

				if (index > -1) {
					common[commonIndex++] = character;
					charsB[index] = (char) 0;
				}

			}

			return copyOf(common, commonIndex);
		}

		/*
		 * Search for character in buffer starting at fromIndex to toIndex - 1.
		 * 
		 * Returns -1 when not found.
		 */
		private static int indexOf(char character, char[] buffer,
				int fromIndex, int toIndex) {

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

	private static class JaroV3_0_1 implements StringMetric {

		public JaroV3_0_1() {

		}

		@Override
		public float compare(final String a, final String b) {

			if (a.isEmpty() && b.isEmpty()) {
				return 1.0f;
			}

			if (a.isEmpty() || b.isEmpty()) {
				return 0.0f;
			}

			final int halfLength = max(0,
					(int) floor(-1 + 0.5 * max(a.length(), b.length())));

			final String commonA = getCommonCharacters(a, b, halfLength);
			final String commonB = getCommonCharacters(b, a, halfLength);

			if (commonA.isEmpty() || commonB.isEmpty()) {
				return 0.0f;
			}

			float transpositions = 0;
			for (int i = 0; i < commonA.length(); i++) {
				if (commonA.charAt(i) != commonB.charAt(i))
					transpositions++;
			}
			transpositions /= 2.0f;

			float aCommonRatio = commonA.length() / (float) a.length();
			float bCommonRatio = commonB.length() / (float) b.length();
			float transpositionRatio = (commonA.length() - transpositions)
					/ commonA.length();

			return (aCommonRatio + bCommonRatio + transpositionRatio) / 3.0f;
		}

		/*
		 * Returns a string of characters from a within b A character in b is
		 * counted as common when it is within separation distance from the
		 * position in a.
		 */
		private static String getCommonCharacters(final String a,
				final String b, final int separation) {
			final StringBuilder common = new StringBuilder(a.length()
					+ b.length());
			final StringBuilder copyOfB = new StringBuilder(b);

			// Iterate of string a and find all characters that occur in b
			// within
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

		/*
		 * Search for character in buffer starting at fromIndex to toIndex - 1.
		 * 
		 * Returns -1 when not found.
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

}
