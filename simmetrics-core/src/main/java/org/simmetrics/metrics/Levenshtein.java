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
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.costfunctions.SubCost01;
import org.simmetrics.metrics.costfunctions.SubstitutionCost;
import org.simmetrics.utils.Math;

/**
 * Implements the basic Levenshtein algorithm providing a similarity measure
 * between two strings.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class Levenshtein implements StringMetric {

	private final SubstitutionCost costFunction = new SubCost01();

	@Override
	public float compare(final String string1, final String string2) {
		final float levensteinDistance = getUnNormalisedSimilarity(string1,
				string2);
		// convert into zero to one return

		// get the max possible levenstein distance score for string
		float maxLen = string1.length();
		if (maxLen < string2.length()) {
			maxLen = string2.length();
		}

		// check for 0 maxLen
		if (maxLen == 0) {
			return 1.0f; // as both strings identically zero length
		} else {
			// return actual / possible levenstein distance to get 0-1 range
			return 1.0f - (levensteinDistance / maxLen);
		}

	}

	private float getUnNormalisedSimilarity(final String s, final String t) {

		/*
		 * The levenstein distance function:
		 * 
		 * * Copy character from string1 over to string2 (cost 0)
		 * 
		 * * Delete a character in string1 (cost 1)
		 * 
		 * * Insert a character in string2 (cost 1)
		 * 
		 * * Substitute one character for another (cost 1)
		 * 
		 * D(i-1,j-1) + d(si,tj) //subst/copy
		 * 
		 * D(i,j) = min D(i-1,j)+1 //insert
		 * 
		 * D(i,j-1)+1 //delete
		 * 
		 * d(i,j) is a function whereby d(c,d)=0 if c=d, 1 else.
		 */
		final float[][] d; // matrix
		final int n; // length of s
		final int m; // length of t
		int i; // iterates through s
		int j; // iterates through t
		float cost; // cost

		// Step 1
		n = s.length();
		m = t.length();
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new float[n + 1][m + 1];

		// Step 2
		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}
		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}

		// Step 3
		for (i = 1; i <= n; i++) {
			// Step 4
			for (j = 1; j <= m; j++) {
				// Step 5
				cost = costFunction.getCost(s, i - 1, t, j - 1);

				// Step 6
				d[i][j] = Math.min3(d[i - 1][j] + 1, d[i][j - 1] + 1,
						d[i - 1][j - 1] + cost);
			}
		}

		// Step 7
		return d[n][m];
	}

	@Override
	public String toString() {
		return "Levenshtein [costFunction=" + costFunction + "]";
	}
}
