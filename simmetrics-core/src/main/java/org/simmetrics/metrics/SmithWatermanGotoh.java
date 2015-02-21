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
import static org.simmetrics.utils.Math.max3;
import static org.simmetrics.utils.Math.max4;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.costfunctions.AffineGap5_1;
import org.simmetrics.metrics.costfunctions.Gap;
import org.simmetrics.metrics.costfunctions.SubCost5_3_Minus3;
import org.simmetrics.metrics.costfunctions.Substitution;

public class SmithWatermanGotoh implements StringMetric {

	private final Gap gap;
	private final Substitution substitution;
	private final int windowSize;
	
	public SmithWatermanGotoh() {
		this(new AffineGap5_1(), new SubCost5_3_Minus3(), Integer.MAX_VALUE);
	}
	
	
	public SmithWatermanGotoh(Gap gap, Substitution substitution, int windowSize) {
		super();
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
		float maxDistance = (min(a.length(), b.length()) * max(
				substitution.max(), gap.min()));
		return smithWatermanGotoh(a, b) / maxDistance;

	}

	private float smithWatermanGotoh(String a, String b) {
		int n = a.length();
		int m = b.length();

		float[][] d = new float[n][m];

		float max = d[0][0] = max(0, substitution.compare(a, 0, b, 0));

		for (int i = 0; i < n; i++) {

			float maxGapCost = 0;

			for (int k = max(1, i - windowSize); k < i; k++) {
				maxGapCost = max(maxGapCost, d[i - k][0] + gap.value(i - k, i));
			}

			d[i][0] = max3(0, maxGapCost, substitution.compare(a, i, b, 0));

			max = max(max, d[i][0]);

		}

		for (int j = 1; j < m; j++) {
			float maxGapCost = 0;
			for (int k = max(1, j - windowSize); k < j; k++) {
				maxGapCost = max(maxGapCost, d[0][j - k] + gap.value(j - k, j));
			}

			d[0][j] = max3(0, maxGapCost, substitution.compare(a, 0, b, j));

			max = max(max, d[0][j]);

		}

		for (int i = 1; i < n; i++) {

			for (int j = 1; j < m; j++) {

				float maxGapCostI = 0;
				float maxGapCostJ = 0;
				for (int k = max(1, i - windowSize); k < i; k++) {
					maxGapCostI = max(maxGapCostI,
							d[i - k][j] + gap.value(i - k, i));
				}
				for (int k = max(1, j - windowSize); k < j; k++) {
					maxGapCostJ = max(maxGapCostJ,
							d[i][j - k] + gap.value(j - k, j));
				}

				d[i][j] = max4(0, maxGapCostI, maxGapCostJ, d[i - 1][j - 1]
						+ substitution.compare(a, i, b, j));

				max = max(max, d[i][j]);
			}

		}

		return max;
	}


	@Override
	public String toString() {
		return "SmithWatermanGotoh [gap=" + gap + ", substitution="
				+ substitution + ", windowSize=" + windowSize + "]";
	}
	
	
}
