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
import org.simmetrics.metrics.costfunctions.Substitution;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.simmetrics.utils.Math.max3;
import static org.simmetrics.utils.Math.max4;

public class SmithWaterman implements StringMetric {

	private static final Substitution MATCH_1_MISMATCH_MINUS_2 = new MatchMismatch(1.0f, -2.0f);

	private final float gapValue;

	private Substitution substitution;

	public SmithWaterman() {
		this(-0.5f, MATCH_1_MISMATCH_MINUS_2);
	}

	public SmithWaterman(float gapValue, Substitution substitution) {
		this.gapValue = gapValue;
		this.substitution = substitution;
	}

	@Override
	public float compare(final String a, final String b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		float maxDistance = (min(a.length(), b.length()) 
								* max(substitution.max(), gapValue));
		return smithWaterman(a, b) / maxDistance;
	}

	private float smithWaterman(final String s, final String t) {
		if (s.isEmpty()) {
			return t.length();
		}
		if (t.isEmpty()) {
			return s.length();
		}

		final float[][] d = new float[s.length()][t.length()];

		// Initialize corner
		float max = d[0][0] = max3(0, gapValue, substitution.compare(s, 0, t, 0));
		
		// Initialize edges
		for (int i = 1; i < s.length(); i++) {
			d[i][0] = max3(	0,
							d[i - 1][0] + gapValue,
							substitution.compare(s, i, t, 0));
			
			max = max(max, d[i][0]);
		}
		
		for (int j = 1; j < t.length(); j++) {
			d[0][j] = max3(	0, 
							d[0][j - 1] + gapValue, 
							substitution.compare(s, 0, t, j));
			
			max = max(max, d[0][j]);
		}

		// Find max
		for (int i = 1; i < s.length(); i++) {
			for (int j = 1; j < t.length(); j++) {
				d[i][j] = max4(	0, 
								d[i - 1][j] + gapValue,
								d[i][j - 1] + gapValue,
								d[i - 1][j - 1] + substitution.compare(s, i, t, j));

				max = max(max, d[i][j]);
			}
		}

		return max;
	}

	@Override
	public String toString() {
		return "SmithWaterman [substitution=" + substitution + ", gapValue="
				+ gapValue + "]";
	}
}
