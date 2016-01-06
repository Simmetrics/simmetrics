/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
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
