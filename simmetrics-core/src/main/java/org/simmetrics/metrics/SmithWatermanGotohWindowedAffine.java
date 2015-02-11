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
import org.simmetrics.metrics.costfunctions.AffineGap5_1;
import org.simmetrics.metrics.costfunctions.AffineGapCost;
import org.simmetrics.metrics.costfunctions.SubCost5_3_Minus3;
import org.simmetrics.metrics.costfunctions.SubstitutionCost;

import static java.lang.Math.max;
import static org.simmetrics.utils.Math.max3;
import static org.simmetrics.utils.Math.max4;

/**
 * Implements the Smith-Waterman-Gotoh algorithm with a windowed affine gap
 * providing a similarity measure between two strings.
 * 

 * @author Sam Chapman
 * @version 1.1
 */
public class SmithWatermanGotohWindowedAffine implements StringMetric {

	@Override
	public String toString() {
		return "SmithWatermanGotohWindowedAffine [windowSize=" + windowSize
				+ ", costfunction=" + costfunction + ", gapFunction="
				+ gapFunction + "]";
	}

	public int getWindowSize() {
		return windowSize;
	}

	public SubstitutionCost getCostfunction() {
		return costfunction;
	}

	public AffineGapCost getGapFunction() {
		return gapFunction;
	}

	private final int windowSize;

	private final SubstitutionCost costfunction;

	private final AffineGapCost gapFunction;

	/**
	 * constructor - default (empty).
	 */
	public SmithWatermanGotohWindowedAffine() {
		// set the default gap cost func
		this.gapFunction = new AffineGap5_1();
		// set the default cost func
		this.costfunction = new SubCost5_3_Minus3();
		// set the default windowSize
		this.windowSize = 100;
	}

	/**
	 * constructor.
	 *
	 * @param gapCostFunc
	 *            - the gap cost function
	 */
	public SmithWatermanGotohWindowedAffine(final AffineGapCost gapCostFunc) {
		// set the gap cost func
		this.gapFunction = gapCostFunc;
		// set the cost func to a default function
		this.costfunction = new SubCost5_3_Minus3();
		// set the default window size
		this.windowSize = 100;
	}

	/**
	 * constructor.
	 *
	 * @param gapCostFunc
	 *            - the cost of a gap
	 * @param costfunction
	 *            - the cost function to use
	 */
	public SmithWatermanGotohWindowedAffine(final AffineGapCost gapCostFunc,
			final SubstitutionCost costfunction) {
		// set the gap cost func
		this.gapFunction = gapCostFunc;
		// set the cost func
		this.costfunction = costfunction;
		// set the default window size
		this.windowSize = 100;
	}

	/**
	 * constructor.
	 *
	 * @param costfunction
	 *            - the cost function to use
	 */
	public SmithWatermanGotohWindowedAffine(final SubstitutionCost costfunction) {
		// set the gapCost to a default value
		this.gapFunction = new AffineGap5_1();
		// set the cost func
		this.costfunction = costfunction;
		// set the default window size
		this.windowSize = 100;
	}

	/**
	 * constructor.
	 *
	 * @param affineGapWindowSize
	 *            the size of the affine gap window to use
	 */
	public SmithWatermanGotohWindowedAffine(final int affineGapWindowSize) {
		// set the default gap cost func
		this.gapFunction = new AffineGap5_1();
		// set the default cost func
		this.costfunction = new SubCost5_3_Minus3();
		// set the default windowSize
		this.windowSize = affineGapWindowSize;
	}

	/**
	 * constructor.
	 *
	 * @param gapCostFunc
	 *            - the gap cost function
	 * @param affineGapWindowSize
	 *            the size of the affine gap window to use
	 */
	public SmithWatermanGotohWindowedAffine(final AffineGapCost gapCostFunc,
			final int affineGapWindowSize) {
		// set the gap cost func
		this.gapFunction = gapCostFunc;
		// set the cost func to a default function
		this.costfunction = new SubCost5_3_Minus3();
		// set the default window size
		this.windowSize = affineGapWindowSize;
	}

	/**
	 * constructor.
	 *
	 * @param gapCostFunc
	 *            - the cost of a gap
	 * @param costfunction
	 *            - the cost function to use
	 * @param affineGapWindowSize
	 *            the size of the affine gap window to use
	 */
	public SmithWatermanGotohWindowedAffine(final AffineGapCost gapCostFunc,
			final SubstitutionCost costfunction, final int affineGapWindowSize) {
		// set the gap cost func
		this.gapFunction = gapCostFunc;
		// set the cost func
		this.costfunction = costfunction;
		// set the default window size
		this.windowSize = affineGapWindowSize;
	}

	/**
	 * constructor.
	 *
	 * @param costfunction
	 *            - the cost function to use
	 * @param affineGapWindowSize
	 *            the size of the affine gap window to use
	 */
	public SmithWatermanGotohWindowedAffine(
			final SubstitutionCost costfunction, final int affineGapWindowSize) {
		// set the gapCost to a default value
		this.gapFunction = new AffineGap5_1();
		// set the cost func
		this.costfunction = costfunction;
		// set the default window size
		this.windowSize = affineGapWindowSize;
	}

	@Override
	public float compare(String string1, String string2) {

		final float smithWatermanGotoh = distance(string1, string2);

		// normalise into zero to one region from min max possible
		float maxValue = Math.min(string1.length(), string2.length());
		if (costfunction.getMaxCost() > -gapFunction.getMaxCost()) {
			maxValue *= costfunction.getMaxCost();
		} else {
			maxValue *= -gapFunction.getMaxCost();
		}

		// check for 0 maxLen
		if (maxValue == 0) {
			return 1.0f; // as both strings identically zero length
		} else {
			// return actual / possible NeedlemanWunch distance to get 0-1 range
			return (smithWatermanGotoh / maxValue);
		}
	}

	/**
	 * Implements the Smith-Waterman-Gotoh distance function
	 * 
	 *
	 * @param s
	 * @param t
	 *
	 * @return the Smith-Waterman-Gotoh distance for the two strings given
	 */
	private float distance(final String s, final String t) {
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
			cost = costfunction.getCost(s, i, t, 0);

			if (i == 0) {
				d[0][0] = max(0, cost);
			} else {
				float maxGapCost = 0.0f;
				int windowStart = i - windowSize;
				if (windowStart < 1) {
					windowStart = 1;
				}
				for (int k = windowStart; k < i; k++) {
					maxGapCost = max(maxGapCost,
							d[i - k][0] - gapFunction.getCost(s, i - k, i));
				}
				d[i][0] = max3(0, maxGapCost, cost);
			}
			// update max possible if available
			if (d[i][0] > maxSoFar) {
				maxSoFar = d[i][0];
			}
		}
		for (j = 0; j < m; j++) {
			// get the substution cost
			cost = costfunction.getCost(s, 0, t, j);

			if (j == 0) {
				d[0][0] = max(0, cost);
			} else {
				float maxGapCost = 0.0f;
				int windowStart = j - windowSize;
				if (windowStart < 1) {
					windowStart = 1;
				}
				for (int k = windowStart; k < j; k++) {
					maxGapCost = max(maxGapCost,
							d[0][j - k] - gapFunction.getCost(t, j - k, j));
				}
				d[0][j] = max3(0, maxGapCost, cost);
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
				cost = costfunction.getCost(s, i, t, j);

				// find lowest cost at point from three possible
				float maxGapCost1 = 0.0f;
				float maxGapCost2 = 0.0f;
				int windowStart = i - windowSize;
				if (windowStart < 1) {
					windowStart = 1;
				}
				for (int k = windowStart; k < i; k++) {
					maxGapCost1 = max(maxGapCost1,
							d[i - k][j] - gapFunction.getCost(s, i - k, i));
				}
				windowStart = j - windowSize;
				if (windowStart < 1) {
					windowStart = 1;
				}
				for (int k = windowStart; k < j; k++) {
					maxGapCost2 = max(maxGapCost2,
							d[i][j - k] - gapFunction.getCost(t, j - k, j));
				}
				d[i][j] = max4(0, maxGapCost1, maxGapCost2, d[i - 1][j - 1]
						+ cost);
				// update max possible if available
				if (d[i][j] > maxSoFar) {
					maxSoFar = d[i][j];
				}
			}
		}

		// debug output
		/*
		 * System.out.print(" \t"); for (j = 0; j < m; j++) {
		 * System.out.print(t.charAt(j) + "     "); } System.out.print("\n");
		 * for (i = 0; i < n; i++) { //print characteer of string s
		 * System.out.print(s.charAt(i) + "\t"); for (j = 0; j < m; j++) {
		 * System.out.print(d[i][j] + "  "); } System.out.print("\n"); }
		 */// end debug output

		// return max value within matrix as holds the maximum edit score
		return maxSoFar;
	}

}
