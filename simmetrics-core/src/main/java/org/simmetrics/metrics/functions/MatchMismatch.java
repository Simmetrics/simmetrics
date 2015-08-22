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
