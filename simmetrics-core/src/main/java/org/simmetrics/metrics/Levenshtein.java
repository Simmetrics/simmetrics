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
import static org.simmetrics.utils.Math.min3;

import java.util.Objects;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.costfunctions.MatchMismatch;
import org.simmetrics.metrics.costfunctions.Substitution;

/**
 * Levenshtein algorithm providing a similarity measure between two strings.
 * 
 * @see <a href=" http://en.wikipedia.org/wiki/Levenshtein_distance">Wikipedia -
 *      Levenshtein distance</a>
 * 
 * @author mpkorstanje
 */
public class Levenshtein implements StringMetric {

	private final Substitution substitution = new MatchMismatch(0.0f, 1.0f);

	@Override
	public float compare(final String a, final String b) {
		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		return 1.0f - (levenstein(a, b) / max(a.length(), b.length()));
	}

	private float levenstein(final String s, final String t) {

		// degenerate cases
		if (Objects.equals(s, t))
			return 0;
		if (s.length() == 0)
			return t.length();
		if (t.length() == 0)
			return s.length();

		// create two work vectors of integer distances
		final float[] v0 = new float[t.length() + 1];
		final float[] v1 = new float[t.length() + 1];

		// initialize v0 (the previous row of distances)
		// this row is A[0][i]: edit distance for an empty s
		// the distance is just the number of characters to delete from t
		for (int i = 0; i < v0.length; i++) {
			v0[i] = i;
		}

		for (int i = 0; i < s.length(); i++) {
			// calculate v1 (current row distances) from the previous row v0

			// first element of v1 is A[i+1][0]
			// edit distance is delete (i+1) chars from s to match empty t
			v1[0] = i + 1;

			// use formula to fill in the rest of the row
			for (int j = 0; j < t.length(); j++) {
				v1[j + 1] = min3(v1[j] + 1, v0[j + 1] + 1,
						v0[j] + substitution.compare(s, i, t, j));
			}

			// copy v1 (current row) to v0 (previous row) for next iteration
			for (int j = 0; j < v0.length; j++)
				v0[j] = v1[j];
		}

		return v1[t.length()];
	}

	@Override
	public String toString() {
		return "Levenshtein";
	}
}
