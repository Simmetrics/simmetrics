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
 * A substitution function that assigns one value to equal characters, another
 * value to unequal characters.
 * 
 * <p>
 * This class is immutable and thread-safe.
 *
 */
public class MatchMismatch implements Substitution {

	private final float matchValue;
	private final float mismatchValue;

	/**
	 * Constructs a new match-mismatch substitution function. When two
	 * characters are equal a score of <code>matchValue</code> is assigned. In
	 * case of a mismatch a score of <code>mismatchValue</code>. The
	 * <code>matchValue</code> must be strictly greater then
	 * <code>mismatchValue</code>
	 * 
	 * @param matchValue
	 *            value when characters are equal
	 * @param mismatchValue
	 *            value when characters are not equal
	 */
	public MatchMismatch(float matchValue, float mismatchValue) {
		super();
		checkArgument(matchValue > mismatchValue);

		this.matchValue = matchValue;
		this.mismatchValue = mismatchValue;
	}

	@Override
	public float compare(String a, int aIndex, String b, int bIndex) {
		return a.charAt(aIndex) == b.charAt(bIndex) ? matchValue
				: mismatchValue;
	}

	@Override
	public float max() {
		return matchValue;
	}

	@Override
	public float min() {
		return mismatchValue;
	}

	@Override
	public String toString() {
		return "MatchMismatch [matchCost=" + matchValue + ", mismatchCost="
				+ mismatchValue + "]";
	}

}
