/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


package org.simmetrics.metrics.functions;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A gap function that takes into account the length the gap. Assigns a penalty
 * that is linear in the length of the gap.
 * 
 * <p>
 * This class is immutable and thread-safe.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Gap_penalty">Wikipedia - Gap
 *      Penalty</a>
 */
public final class LinearGap implements Gap {

	private final float gapValue;

	/**
	 * Constructs a linear gap function that scales the length of a gap with
	 * <code>gapValue</code>.
	 * 
	 * @param gapValue
	 *            a constant gap value
	 */
	public LinearGap(float gapValue) {
		checkArgument(gapValue <= 0.0f);

		this.gapValue = gapValue;
	}

	@Override
	public final float value(int fromIndex, int toIndex) {
		checkArgument(fromIndex < toIndex, "fromIndex must be before toIndex");
		return gapValue * (toIndex - fromIndex - 1);
	}

	@Override
	public final float max() {
		return 0.0f;
	}

	@Override
	public final float min() {
		return Float.NEGATIVE_INFINITY;
	}

	@Override
	public String toString() {
		return "LinearGap [gapValue=" + gapValue + "]";
	}

}
