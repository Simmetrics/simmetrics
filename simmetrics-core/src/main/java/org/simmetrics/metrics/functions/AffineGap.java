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

package org.simmetrics.metrics.functions;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A gap function that calculates the gap penalty as <code>A+(B * GapLegth)</code>.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see Substitution
 * @see <a href="https://en.wikipedia.org/wiki/Gap_penalty">Wikipedia - Gap
 *      Penalty</a>
 *
 */
public final class AffineGap implements Gap {

	private final float startValue;
	private final float gapValue;

	/**
	 * Constructs a constant gap function that assigns a penalty of
	 * <code>startValue + gapValue * gapLenght </code> to a gap. .
	 * 
	 * @param startValue
	 *            a non-positive initial penalty for creating a gap
	 * @param gapValue
	 *            a non-positive constant gap value
	 */
	public AffineGap(float startValue, float gapValue) {
		checkArgument(startValue <= 0.0f);
		checkArgument(gapValue <= 0.0f);

		this.startValue = startValue;
		this.gapValue = gapValue;
	}

	@Override
	public final float value(int fromIndex, int toIndex) {
		checkArgument(fromIndex < toIndex, "fromIndex must be before toIndex");
		return startValue + gapValue * (toIndex - fromIndex - 1);
	}

	@Override
	public final float max() {
		return startValue;
	}

	@Override
	public final float min() {
		return Float.NEGATIVE_INFINITY;
	}

	@Override
	public String toString() {
		return "AffineGap [startValue=" + startValue + ", gapValue=" + gapValue
				+ "]";
	}

}
