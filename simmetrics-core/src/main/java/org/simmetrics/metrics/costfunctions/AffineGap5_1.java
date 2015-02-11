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

package org.simmetrics.metrics.costfunctions;

/**
 * AffineGap5_1 implements a affine gap cost function.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class AffineGap5_1 implements AffineGapCost {

	@Override
	public String toString() {
		return "AffineGap5_1";
	}

	@Override
	public final float getCost(final String stringToGap,
			final int stringIndexStartGap, final int stringIndexEndGap) {
		if (stringIndexStartGap >= stringIndexEndGap) {
			return 0.0f;
		} else {
			return 5.0f + ((stringIndexEndGap - 1) - stringIndexStartGap);
		}
	}

	@Override
	public final float getMaxCost() {
		return Float.MAX_VALUE;
	}

	@Override
	public final float getMinCost() {
		return 0.0f;
	}
}
