/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * #L%
 */
package org.simmetrics.metrics;

import static java.lang.Math.max;

import org.simmetrics.StringDistance;
import org.simmetrics.StringMetric;

/**
 * Applies the longest common substring algorithm to calculate the similarity
 * and distance between two strings.
 * <p>
 * <code>
 * similarity(a,b) = ∣lcs(a,b)∣ / max{∣a∣, ∣b∣}
 * <br>
 * distance(a,b) = ∣a∣ + ∣b∣ - 2 * ∣lcs(a,b)∣  
 * </code>
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a
 *      href="https://en.wikipedia.org/wiki/Longest_common_substring_problem">Wikipedia
 *      - Longest common substring problem</a>
 */
public final class LongestCommonSubstring implements StringMetric,
		StringDistance {

	@Override
	public float compare(String a, String b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		return lcs(a, b) / (float) max(a.length(), b.length());
	}

	@Override
	public float distance(String a, String b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 0.0f;
		}
		if (a.isEmpty()) {
			return b.length();
		}
		if (b.isEmpty()) {
			return a.length();
		}
		return a.length() + b.length() - 2 * lcs(a, b);
	}

	private static int lcs(String a, String b) {

		final int m = a.length();
		final int n = b.length();

		int[] v0 = new int[n];
		int[] v1 = new int[n];

		int z = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (a.charAt(i) == b.charAt(j)) {
					if (i == 0 || j == 0) {
						v1[j] = 1;
					} else {
						v1[j] = v0[j - 1] + 1;
					}
					if (v1[j] > z) {
						z = v1[j];
					}
				} else {
					v1[j] = 0;
				}
			}
			final int[] swap = v0; v0 = v1; v1 = swap;
		}
		return z;
	}

	@Override
	public String toString() {
		return "LongestCommonSubstring";
	}

}
