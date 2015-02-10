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
import org.simmetrics.metrics.costfunctions.SubCost1_Minus2;
import org.simmetrics.metrics.costfunctions.SubstitutionCost;

import static org.simmetrics.utils.Math.max3;
import static org.simmetrics.utils.Math.max4;

/**
 * Implements the Smith-Waterman edit distance function.
 * 
 * @see <a
 *      href="http://www.gen.tcd.ie/molevol/nwswat.html for details">Needleman-Wunsch
 *      Algorithm for Sequence Similarity Searches</a>
 *
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class SmithWaterman implements StringMetric {

	private SubstitutionCost costFunction;

	@Override
	public String toString() {
		return "SmithWaterman [costFunction=" + costFunction + ", gapCost="
				+ gapCost + "]";
	}

	private float gapCost;

	/**
	 * constructor - default (empty).
	 */
	public SmithWaterman() {
		// set the gapCost to a default value
		gapCost = 0.5f;
		// set the default cost func
		costFunction = new SubCost1_Minus2();
	}

	/**
	 * constructor.
	 *
	 * @param costG
	 *            - the cost of a gap
	 */
	public SmithWaterman(final float costG) {
		// set the gapCost to a given value
		gapCost = costG;
		// set the cost func to a default function
		costFunction = new SubCost1_Minus2();
	}

	/**
	 * constructor.
	 *
	 * @param costG
	 *            - the cost of a gap
	 * @param costFunc
	 *            - the cost function to use
	 */
	public SmithWaterman(final float costG, final SubstitutionCost costFunc) {
		// set the gapCost to the given value
		gapCost = costG;
		// set the cost func
		costFunction = costFunc;
	}

	/**
	 * constructor.
	 *
	 * @param costFunc
	 *            - the cost function to use
	 */
	public SmithWaterman(final SubstitutionCost costFunc) {
		// set the gapCost to a default value
		gapCost = 0.5f;
		// set the cost func
		costFunction = costFunc;
	}

	@Override
	public float compare(final String string1, final String string2) {
		final float smithWaterman = getUnNormalisedSimilarity(string1, string2);

		// normalise into zero to one region from min max possible
		float maxValue = Math.min(string1.length(), string2.length());
		if (costFunction.getMaxCost() > -gapCost) {
			maxValue *= costFunction.getMaxCost();
		} else {
			maxValue *= -gapCost;
		}

		// check for 0 maxLen
		if (maxValue == 0) {
			return 1.0f; // as both strings identically zero length
		} else {
			// return actual / possible NeedlemanWunch distance to get 0-1 range
			return (smithWaterman / maxValue);
		}
	}


	private float getUnNormalisedSimilarity(final String s, final String t) {
		final float[][] d; // matrix
		final int n; // length of s
		final int m; // length of t
		int i; // iterates through s
		int j; // iterates through t
		float cost; // cost

		// check for zero length input
		n = s.length();
		m = t.length();
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}

		// create matrix (n)x(m)
		d = new float[n][m];

		// process first row and column first as no need to consider previous
		// rows/columns
		float maxSoFar = 0.0f;
		for (i = 0; i < n; i++) {
			// get the substution cost
			cost = costFunction.getCost(s, i, t, 0);

			if (i == 0) {
				d[0][0] = max3(0, -gapCost, cost);
			} else {
				d[i][0] = max3(0, d[i - 1][0] - gapCost, cost);
			}
			// update max possible if available
			if (d[i][0] > maxSoFar) {
				maxSoFar = d[i][0];
			}
		}
		for (j = 0; j < m; j++) {
			// get the substution cost
			cost = costFunction.getCost(s, 0, t, j);

			if (j == 0) {
				d[0][0] = max3(0, -gapCost, cost);
			} else {
				d[0][j] = max3(0, d[0][j - 1] - gapCost, cost);
			}
			// update max possible if available
			if (d[0][j] > maxSoFar) {
				maxSoFar = d[0][j];
			}
		}

		// cycle through rest of table filling values from the lowest cost value
		// of the three part cost function
		for (i = 1; i < n; i++) {
			for (j = 1; j < m; j++) {
				// get the substution cost
				cost = costFunction.getCost(s, i, t, j);

				// find lowest cost at point from three possible
				d[i][j] = max4(
						0,
						d[i - 1][j] - gapCost,
						d[i][j - 1] - gapCost,
						d[i - 1][j - 1] + cost);
				// update max possible if available
				if (d[i][j] > maxSoFar) {
					maxSoFar = d[i][j];
				}
			}
		}

		// return max value within matrix as holds the maximum edit score
		return maxSoFar;
	}
}
