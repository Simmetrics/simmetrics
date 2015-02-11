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

import org.simmetrics.metrics.costfunctions.AffineGap5_1;
import org.simmetrics.metrics.costfunctions.AffineGapCost;
import org.simmetrics.metrics.costfunctions.SubCost5_3_Minus3;
import org.simmetrics.metrics.costfunctions.SubstitutionCost;

/**
 * Implements the Gotoh extension of the Smith-Waterman method incorporating
 * affine gaps in the strings.
 * 
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class SmithWatermanGotoh extends SmithWatermanGotohWindowedAffine {

	/**
	 * constructor - default (empty).
	 */
	public SmithWatermanGotoh() {
		super(new AffineGap5_1(), new SubCost5_3_Minus3(), Integer.MAX_VALUE);
	}

	/**
	 * constructor.
	 *
	 * @param gapCostFunc
	 *            - the gap cost function
	 */
	public SmithWatermanGotoh(AffineGapCost gapCostFunc) {
		super(gapCostFunc, new SubCost5_3_Minus3(), Integer.MAX_VALUE);
	}

	/**
	 * constructor.
	 *
	 * @param gapCostFunc
	 *            - the cost of a gap
	 * @param costFunc
	 *            - the cost function to use
	 */
	public SmithWatermanGotoh(AffineGapCost gapCostFunc,
			SubstitutionCost costFunc) {
		super(gapCostFunc, costFunc, Integer.MAX_VALUE);
	}

	/**
	 * constructor.
	 *
	 * @param costFunc
	 *            - the cost function to use
	 */
	public SmithWatermanGotoh(SubstitutionCost costFunc) {
		super(new AffineGap5_1(), costFunc, Integer.MAX_VALUE);
	}

	@Override
	public String toString() {
		return "SmithWatermanGotoh [getWindowSize()=" + getWindowSize()
				+ ", getCostfunction()=" + getCostfunction()
				+ ", getGapFunction()=" + getGapFunction() + "]";
	}

}
