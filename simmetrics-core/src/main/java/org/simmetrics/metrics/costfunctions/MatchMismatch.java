package org.simmetrics.metrics.costfunctions;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.google.common.base.Preconditions;

public class MatchMismatch implements Substitution {

	private final float matchValue;
	private final float mistMatchValue;

	public MatchMismatch(float matchValue, float mistMatchValue) {
		super();
		checkArgument(matchValue > mistMatchValue);
		
		this.matchValue = matchValue;
		this.mistMatchValue = mistMatchValue;
	}

	@Override
	public float compare(String a, int aIndex, String b, int bIndex) {
		return a.charAt(aIndex) == b.charAt(bIndex) ? matchValue : mistMatchValue;
	}

	@Override
	public float max() {
		return matchValue;
	}

	@Override
	public float min() {
		return mistMatchValue;
	}

	@Override
	public String toString() {
		return "MatchMismatch [matchCost=" + matchValue + ", mismatchCost="
				+ mistMatchValue + "]";
	}
	
	
}
