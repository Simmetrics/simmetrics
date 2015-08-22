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
 * A gap function that assigns a constant penalty to a gap regardless of size.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Gap_penalty">Wikipedia - Gap
 *      Penalty</a>
 */
public final class ConstantGap implements Gap {

	private final float gapValue;

	/**
	 * Constructs a constant gap function that assigns a penalty of
	 * <code>gapValue</code> to a gap. .
	 * 
	 * @param gapValue
	 *            a non-positive constant gap value
	 */
	public ConstantGap(float gapValue) {
		checkArgument(gapValue <= 0.0f);

		this.gapValue = gapValue;
	}

	@Override
	public final float value(int fromIndex, int toIndex) {
		checkArgument(fromIndex < toIndex, "fromIndex must be before toIndex");
		return gapValue;
	}

	@Override
	public final float max() {
		return gapValue;
	}

	@Override
	public final float min() {
		return gapValue;
	}

	@Override
	public String toString() {
		return "ConstantGap [gapValue=" + gapValue + "]";
	}

}
