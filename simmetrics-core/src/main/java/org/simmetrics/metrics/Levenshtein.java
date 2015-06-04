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

/**
 * Levenshtein algorithm providing a similarity measure between two strings.
 * 
 * 
 *
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href=" http://en.wikipedia.org/wiki/Levenshtein_distance">Wikipedia -
 *      Levenshtein distance</a>
 * 
 * 
 */
public class Levenshtein implements StringMetric {

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

	private static float levenstein(final String s, final String t) {

		if (Objects.equals(s, t))
			return 0;
		if (s.isEmpty())
			return t.length();
		if (t.isEmpty())
			return s.length();

		final int tLength = t.length();
		final int sLength = s.length();
		
		final float[] v0 = new float[tLength + 1];
		final float[] v1 = new float[tLength + 1];

		// initialize v0 (the previous row of distances)
		// this row is A[0][i]: edit distance for an empty s
		// the distance is just the number of characters to delete from t
		for (int i = 0; i < v0.length; i++) {
			v0[i] = i;
		}

		for (int i = 0; i < sLength ; i++) {

			// first element of v1 is A[i+1][0]
			// edit distance is delete (i+1) chars from s to match empty t
			v1[0] = i + 1;

			for (int j = 0; j < tLength; j++) {
				v1[j + 1] = min3(v1[j] + 1, v0[j + 1] + 1, v0[j]
						+ (s.charAt(i) == t.charAt(j) ? 0.0f : 1.0f));
			}

			for (int j = 0; j < v0.length; j++) {
				v0[j] = v1[j];
			}
		}

		return v1[tLength];
	}

	@Override
	public String toString() {
		return "Levenshtein";
	}
}
