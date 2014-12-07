/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package uk.ac.shef.wit.simmetrics.similaritymetrics;

import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.InterfaceSubstitutionCost;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.SubCost01;
import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import static uk.ac.shef.wit.simmetrics.utils.Math.min3;

/**
 * Implements the Needleman-Wunch algorithm providing an edit distance based
 * similarity measure between two strings
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class NeedlemanWunch extends AbstractStringMetric {

	private final float ESTIMATEDTIMINGCONST = 1.842e-4f;

	private final InterfaceSubstitutionCost dCostFunc;

	private final float gapCost;

	public NeedlemanWunch() {
		this(2.0f, new SubCost01());
	}

	/**
	 * constructor.
	 *
	 * @param costG
	 *            - the cost of a gap
	 */
	public NeedlemanWunch(final float costG) {
		this(costG, new SubCost01());
	}

	/**
	 * constructor.
	 *
	 * @param costG
	 *            - the cost of a gap
	 * @param costFunc
	 *            - the cost function to use
	 */
	public NeedlemanWunch(final float costG,
			final InterfaceSubstitutionCost costFunc) {
		// set the gapCost to the given value
		this.gapCost = costG;
		// set the cost func
		this.dCostFunc = costFunc;
	}

	/**
	 * constructor.
	 *
	 * @param costFunc
	 *            - the cost function to use
	 */
	public NeedlemanWunch(final InterfaceSubstitutionCost costFunc) {
		this(2.0f, costFunc);
	}

	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Needleman-Wunch algorithm providing an edit distance based similarity measure between two strings";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Length = string1.length();
		final float str2Length = string2.length();
		return (str1Length * str2Length) * ESTIMATEDTIMINGCONST;
	}

	public float getSimilarity(final String string1, final String string2) {
		float needlemanWunch = getUnNormalisedSimilarity(string1, string2);

		// normalise into zero to one region from min max possible
		float maxValue = Math.max(string1.length(), string2.length());
		float minValue = maxValue;
		if (dCostFunc.getMaxCost() > gapCost) {
			maxValue *= dCostFunc.getMaxCost();
		} else {
			maxValue *= gapCost;
		}
		if (dCostFunc.getMinCost() < gapCost) {
			minValue *= dCostFunc.getMinCost();
		} else {
			minValue *= gapCost;
		}
		if (minValue < 0.0f) {
			maxValue -= minValue;
			needlemanWunch -= minValue;
		}

		// check for 0 maxLen
		if (maxValue == 0) {
			return 1.0f; // as both strings identically zero length
		} else {
			// return actual / possible NeedlemanWunch distance to get 0-1 range
			return 1.0f - (needlemanWunch / maxValue);
		}

	}

	public float getUnNormalisedSimilarity(final String s, final String t) {
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

		// create matrix (n+1)x(m+1)
		d = new float[n + 1][m + 1];

		// put row and column numbers in place
		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}
		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}

		// cycle through rest of table filling values from the lowest cost value
		// of the three part cost function
		for (i = 1; i <= n; i++) {
			for (j = 1; j <= m; j++) {
				// get the substution cost
				cost = dCostFunc.getCost(s, i - 1, t, j - 1);

				// find lowest cost at point from three possible
				d[i][j] = min3(d[i - 1][j] + gapCost, d[i][j - 1] + gapCost,
						d[i - 1][j - 1] + cost);
			}
		}

		// return bottom right of matrix as holds the maximum edit score
		return d[n][m];
	}
}
