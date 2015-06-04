package org.simmetrics.metrics;

import org.simmetrics.Metric;
import org.simmetrics.StringMetric;

import static java.lang.Math.*;
import static java.util.Arrays.copyOf;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;

public class JaroCaliper {

	enum Value {
		Shakespear("This happy breed of men"), Rumi(
				"Sell your cleverness and buy bewilderment.");

		final String s;

		Value(String s) {
			this.s = s;
		}
	}

	enum Method {

		latest(new Jaro()), v3_0_1(new JaroV3_0_1()), v3_0_2(new JaroV3_0_2());

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
