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
import static org.simmetrics.metrics.Math.max;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.functions.AffineGap;
import org.simmetrics.metrics.functions.Gap;
import org.simmetrics.metrics.functions.MatchMismatch;
import org.simmetrics.metrics.functions.Substitution;

/**
 * Applies the Smith-Waterman algorithm to calculate the similarity between two
 * strings. Implementation uses the implementation as described by Smith and
 * Waterman. This implementation uses quadratic space and cubic time.
 * <p>
 * This class is immutable and thread-safe if its substitution and gap functions
 * are.
 * 
 * @see NeedlemanWunch
 * @see SmithWatermanGotoh
 * @see <a href="https://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm"
 *      >Wikipedia - Smith-Waterman algorithm</a>
 */
public final class SmithWaterman implements StringMetric {

	private final Gap gap;
	private final Substitution substitution;
	private final int windowSize;

	/**
	 * Constructs a new Smith Waterman metric. Uses an affine gap of
	 * <code>-5.0 - gapLength</code> a <code>-3.0</code> substitution penalty
	 * for mismatches, <code>5.0</code> for matches.
	 * 
	 */
	public SmithWaterman() {
		this(new AffineGap(-5.0f, -1.0f), new MatchMismatch(5.0f, -3.0f),
				Integer.MAX_VALUE);
	}

	/**
	 * Constructs a new Smith Waterman metric.
	 * 
	 * @param gap
	 *            a gap function to score gaps by
	 * @param substitution
	 *            a substitution function to score substitutions by
	 * @param windowSize
	 *            a non-negative window in which
	 */
	public SmithWaterman(Gap gap, Substitution substitution, int windowSize) {
		checkNotNull(gap);
		checkNotNull(substitution);
		checkArgument(windowSize >= 0);
		this.gap = gap;
		this.substitution = substitution;
		this.windowSize = windowSize;
	}

	@Override
	public float compare(String a, String b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}
		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}
		float maxDistance = min(a.length(), b.length())
				* max(substitution.max(), gap.min());
		return smithWaterman(a, b) / maxDistance;

	}

	private float smithWaterman(String a, String b) {
		final int n = a.length();
		final int m = b.length();

		final float[][] d = new float[n][m];

		// Initialize corner
		float max = d[0][0] = max(0, substitution.compare(a, 0, b, 0));

		// Initialize edge
		for (int i = 0; i < n; i++) {

			// Find most optimal deletion
			float maxGapCost = 0;
			for (int k = max(1, i - windowSize); k < i; k++) {
				maxGapCost = max(maxGapCost, d[i - k][0] + gap.value(i - k, i));
			}

			d[i][0] = max(0, maxGapCost, substitution.compare(a, i, b, 0));

			max = max(max, d[i][0]);

		}

		// Initialize edge
		for (int j = 1; j < m; j++) {

			// Find most optimal insertion
			float maxGapCost = 0;
			for (int k = max(1, j - windowSize); k < j; k++) {
				maxGapCost = max(maxGapCost, d[0][j - k] + gap.value(j - k, j));
			}

			d[0][j] = max(0, maxGapCost, substitution.compare(a, 0, b, j));

			max = max(max, d[0][j]);

		}

		// Build matrix
		for (int i = 1; i < n; i++) {

			for (int j = 1; j < m; j++) {

				float maxGapCost = 0;
				// Find most optimal deletion
				for (int k = max(1, i - windowSize); k < i; k++) {
					maxGapCost = max(maxGapCost,
							d[i - k][j] + gap.value(i - k, i));
				}
				// Find most optimal insertion
				for (int k = max(1, j - windowSize); k < j; k++) {
					maxGapCost = max(maxGapCost,
							d[i][j - k] + gap.value(j - k, j));
				}

				// Find most optimal of insertion, deletion and substitution
				d[i][j] = max(0, maxGapCost,
						d[i - 1][j - 1] + substitution.compare(a, i, b, j));

				max = max(max, d[i][j]);
			}

		}

		return max;
	}

	@Override
	public String toString() {
		return "SmithWaterman [gap=" + gap + ", substitution=" + substitution
				+ ", windowSize=" + windowSize + "]";
	}

}
