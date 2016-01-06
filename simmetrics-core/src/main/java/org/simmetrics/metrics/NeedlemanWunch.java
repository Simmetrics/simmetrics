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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.simmetrics.metrics.Math.min;

import java.util.Objects;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.functions.MatchMismatch;
import org.simmetrics.metrics.functions.Substitution;

/**
 * Applies the Needleman-Wunsch algorithm to calculate the similarity
 * between two strings. This implementation uses linear space.
 * <p>
 * This class is immutable and thread-safe if its substitution function is.
 * 
 * @see SmithWatermanGotoh
 * @see SmithWaterman
 * @see <a
 *      href="https://en.wikipedia.org/wiki/Needleman%E2%80%93Wunsch_algorithm">Wikipedia
 *      - Needleman-Wunsch algorithm</a>
 */
public final class NeedlemanWunch implements StringMetric {

	private static final Substitution MATCH_0_MISMATCH_1 = new MatchMismatch(
			0.0f, -1.0f);

	private final Substitution substitution;

	private final float gapValue;

	/**
	 * Constructs a new Needleman-Wunch metric. Uses an gap of <code>-2.0</code>
	 * a <code>-1.0</code> substitution penalty for mismatches, <code>0</code>
	 * for matches.
	 * 
	 */
	public NeedlemanWunch() {
		this(-2.0f, MATCH_0_MISMATCH_1);
	}

	/**
	 * Constructs a new Needleman-Wunch metric.
	 * 
	 * @param gapValue
	 *            a non-positive penalty for gaps
	 * @param substitution
	 *            a substitution function for mismatched characters
	 */
	public NeedlemanWunch(float gapValue, Substitution substitution) {
		checkArgument(gapValue <= 0.0f);
		checkNotNull(substitution);
		this.gapValue = gapValue;
		this.substitution = substitution;
	}

	@Override
	public float compare(String a, String b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		float maxDistance = max(a.length(), b.length())
				* max(substitution.max(), gapValue);
		float minDistance = max(a.length(), b.length())
				* min(substitution.min(), gapValue);

		return (-needlemanWunch(a, b) - minDistance)
				/ (maxDistance - minDistance);

	}

	private float needlemanWunch(final String s, final String t) {

		if (Objects.equals(s, t)) {
			return 0;
		}

		if (s.isEmpty()) {
			return -gapValue * t.length();
		}
		if (t.isEmpty()) {
			return -gapValue * s.length();
		}
		
		final int n = s.length();
		final int m = t.length();

		// We're only interested in the alignment penalty between s and t
		// and not their actual alignment. This means we don't have to backtrack
		// through the n-by-m matrix and can safe some space by reusing v0 for
		// row i-1.
		float[] v0 = new float[m + 1];
		float[] v1 = new float[m + 1];

		for (int j = 0; j <= m; j++) {
			v0[j] = j;
		}

		for (int i = 1; i <= n; i++) {
			v1[0] = i;

			for (int j = 1; j <= m; j++) {
				v1[j] = min(
						v0[j]     - gapValue, 
						v1[j - 1] - gapValue, 
						v0[j - 1] - substitution.compare(s, i - 1, t, j - 1));
			}
			
			final float[] swap = v0; v0 = v1; v1 = swap;

		}
		
		// Because we swapped the results are in v0.
		return v0[m];
	}

	@Override
	public String toString() {
		return "NeedlemanWunch [costFunction=" + substitution + ", gapCost="
				+ gapValue + "]";
	}

}
