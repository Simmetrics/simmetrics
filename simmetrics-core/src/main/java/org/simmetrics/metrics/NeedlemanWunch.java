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
import org.simmetrics.metrics.costfunctions.SubCost01;
import org.simmetrics.metrics.costfunctions.SubstitutionCost;

import static org.simmetrics.utils.Math.min3;

/**
 * Implements the Needleman-Wunch algorithm providing an edit distance based
 * similarity measure between two strings.
 * 
 * @See <a href="http://www.gen.tcd.ie/molevol/nwswat.html"> Needleman-Wunsch
 *      Algorithm for Sequence Similarity Searches</a>
 * @author Sam Chapman
 * @version 1.1
 */
public class NeedlemanWunch implements StringMetric {

	private final SubstitutionCost costFunction;

	private final float gapCost;

	/**
	 * Constructs a NeedlemanWunch metric with a gap cost of 2.0 and a
	 * substitution cost of 1.0.
	 *
	 */
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
	public NeedlemanWunch(final float costG, final SubstitutionCost costFunc) {
		// set the gapCost to the given value
		this.gapCost = costG;
		// set the cost func
		this.costFunction = costFunc;
	}

	/**
	 * constructor.
	 *
	 * @param costFunc
	 *            - the cost function to use
	 */
	public NeedlemanWunch(final SubstitutionCost costFunc) {
		this(2.0f, costFunc);
	}

	@Override
	public float compare(String string1, String string2) {

		float needlemanWunch = getUnNormalisedSimilarity(string1, string2);

		// normalise into zero to one region from min max possible
		float maxValue = Math.max(string1.length(), string2.length());
		float minValue = maxValue;
		if (costFunction.getMaxCost() > gapCost) {
			maxValue *= costFunction.getMaxCost();
		} else {
			maxValue *= gapCost;
		}
		if (costFunction.getMinCost() < gapCost) {
			minValue *= costFunction.getMinCost();
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

	private float getUnNormalisedSimilarity(final String s, final String t) {

		if (s.isEmpty()) {
			return t.length();
		}
		if (t.isEmpty()) {
			return s.length();
		}

		// create matrix (n+1)x(m+1)
		final float[][] d = new float[s.length() + 1][t.length() + 1];

		// put row and column numbers in place
		for (int i = 0; i < d.length; i++) {
			d[i][0] = i;
		}
		for (int j = 0; j < d[0].length; j++) {
			d[0][j] = j;
		}

		// cycle through rest of table filling values from the lowest cost value
		// of the three part cost function
		for (int i = 1; i < d.length; i++) {
			for (int j = 1; j < d[0].length; j++) {
				// get the substution cost
				float cost = costFunction.getCost(s, i - 1, t, j - 1);

				// find lowest cost at point from three possible
				d[i][j] = min3(d[i - 1][j] + gapCost, d[i][j - 1] + gapCost,
						d[i - 1][j - 1] + cost);
			}
		}

		// return bottom right of matrix as holds the maximum edit score
		return d[d.length - 1][d[0].length - 1];
	}

	@Override
	public String toString() {
		return "NeedlemanWunch [costFunction=" + costFunction + ", gapCost="
				+ gapCost + "]";
	}

}
