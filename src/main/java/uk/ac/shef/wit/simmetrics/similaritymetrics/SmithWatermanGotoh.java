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

import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractAffineGapCost;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AffineGap5_1;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.SubCost5_3_Minus3;

/**
 * Implements the Gotoh extension of the Smith-Waterman method incorporating
 * affine gaps in the strings
 * 
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class SmithWatermanGotoh extends SmithWatermanGotohWindowedAffine
		  {

	private final float ESTIMATEDTIMINGCONST = 2.2e-5f;

	/**
	 * constructor - default (empty).
	 */
	public SmithWatermanGotoh() {
		// use the supers constructor
		super(new AffineGap5_1(), new SubCost5_3_Minus3(), Integer.MAX_VALUE);
	}

	/**
	 * constructor.
	 *
	 * @param gapCostFunc
	 *            - the gap cost function
	 */
	public SmithWatermanGotoh(final AbstractAffineGapCost gapCostFunc) {
		// use the supers constructor
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
	public SmithWatermanGotoh(final AbstractAffineGapCost gapCostFunc,
			final AbstractSubstitutionCost costFunc) {
		// use the supers constructor
		super(gapCostFunc, costFunc, Integer.MAX_VALUE);
	}

	/**
	 * constructor.
	 *
	 * @param costFunc
	 *            - the cost function to use
	 */
	public SmithWatermanGotoh(final AbstractSubstitutionCost costFunc) {
		// use the supers constructor
		super(new AffineGap5_1(), costFunc, Integer.MAX_VALUE);
	}


	public String getLongDescriptionString() {
		return "Implements the Smith-Waterman-Gotoh algorithm providing a similarity measure between two string";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Length = string1.length();
		final float str2Length = string2.length();
		return ((str1Length * str2Length * str1Length) + (str1Length
				* str2Length * str2Length))
				* ESTIMATEDTIMINGCONST;
	}
}
