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

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.costfunctions.MatchMismatch;
import org.simmetrics.metrics.costfunctions.SubstitutionCost;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.simmetrics.utils.Math.max3;
import static org.simmetrics.utils.Math.max4;

public class SmithWaterman implements StringMetric {

	private static final SubstitutionCost MATCH_1_MISMATCH_MINUS_2 = new MatchMismatch(
			1.0f, -2.0f);

	private final float gapCost;

	private SubstitutionCost substitutionCost;

	public SmithWaterman() {
		this(0.5f, MATCH_1_MISMATCH_MINUS_2);
	}

	public SmithWaterman(final float gapCost,
			final SubstitutionCost substitutionCost) {
		this.gapCost = gapCost;
		this.substitutionCost = substitutionCost;
	}

	@Override
	public float compare(final String a, final String b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		float maxDistance = (min(a.length(), b.length()) * max(
				substitutionCost.getMaxCost(), -gapCost));
		return smithWatermanDistance(a, b) / maxDistance;
	}

	private float smithWatermanDistance(final String s, final String t) {
		if (s.isEmpty()) {
			return t.length();
		}
		if (t.isEmpty()) {
			return s.length();
		}

		// Use locals for speed
		final int n = s.length();
		final int m = t.length();
		final float gapCost = -this.gapCost;
		final SubstitutionCost sc = this.substitutionCost;
		final float[][] d = new float[n][m];

		// Initialize corner
		d[0][0] = max3(0, gapCost, sc.getCost(s, 0, t, 0));
		float max = d[0][0];

		// Initialize edges
		for (int i = 1; i < n; i++) {
			d[i][0] = max3(0, d[i - 1][0] + gapCost, sc.getCost(s, i, t, 0));
			max = max(max, d[i][0]);
		}
		for (int j = 1; j < m; j++) {
			d[0][j] = max3(0, d[0][j - 1] + gapCost, sc.getCost(s, 0, t, j));
			max = max(max, d[0][j]);
		}

		// Find max
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				d[i][j] = max4(0, d[i - 1][j] + gapCost, d[i][j - 1] + gapCost,
						d[i - 1][j - 1] + sc.getCost(s, i, t, j));

				max = max(max, d[i][j]);
			}
		}

		return max;
	}

	@Override
	public String toString() {
		return "SmithWaterman [costFunction=" + substitutionCost + ", gapCost="
				+ gapCost + "]";
	}
}
