package org.simmetrics.metrics.costfunctions;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A substitution function that assigns one value to equal characters, another
 * value to unequal characters.
 * 
 * @author mpkorstanje
 *
 */
public class MatchMismatch implements Substitution {

	private final float matchValue;
	private final float mismatchValue;

	/**
	 * Constructs a new match-mismatch substitution function. When two
	 * characters are equal<code>matchValue</code> is returned. In case of a
	 * mismatch <code>mismatchValue</code>. The <code>matchValue</code> must be
	 * strictly greater then <code>mismatchValue</code>
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
