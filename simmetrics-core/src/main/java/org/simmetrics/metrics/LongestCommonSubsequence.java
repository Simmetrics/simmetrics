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
 * Applies the longest common subsequence algorithm to calculate the similarity
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
 *      href="https://en.wikipedia.org/wiki/Longest_common_subsequence_problem">Wikipedia
 *      - Longest common subsequence problem</a>
 */
public final class LongestCommonSubsequence implements StringMetric,
		StringDistance {

	@Override
	public float compare(String a, String b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		return lcs(a, b) / (float)max(a.length() ,b.length());
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

		final int n = a.length();
		final int m = b.length();

		// We're only interested in the actual longest common subsequence This
		// means we don't have to backtrack through the n-by-m matrix and can
		// safe some space by reusing v0 for row i-1.
		int[] v0 = new int[m + 1];
		int[] v1 = new int[m + 1];

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (a.charAt(i - 1) == b.charAt(j - 1)) {
					v1[j] = v0[j - 1] + 1;
				} else {
					v1[j] = max(v1[j - 1], v0[j]);
				}
			}
			int[] swap = v0; v0 = v1; v1 = swap; 
		}

		// Because we swapped the results are in v0.
		return v0[m];
	}

	@Override
	public String toString() {
		return "LongestCommonSubsequence";
	}

}
