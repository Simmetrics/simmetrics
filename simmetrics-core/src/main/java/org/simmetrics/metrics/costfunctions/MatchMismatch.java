package org.simmetrics.metrics.costfunctions;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MatchMismatch implements Substitution {

	private final float matchCost;
	private final float mismatchCost;

	public MatchMismatch(float matchCost, float mismatchCost) {
		super();
		this.matchCost = matchCost;
		this.mismatchCost = mismatchCost;
	}

	@Override
	public float compare(String a, int aIndex, String b, int bIndex) {
		return a.charAt(aIndex) == b.charAt(bIndex) ? matchCost : mismatchCost;
	}

	@Override
	public float getMaxCost() {
		return max(matchCost, mismatchCost);
	}

	@Override
	public float getMinCost() {
		return min(matchCost, mismatchCost);
	}

	@Override
	public String toString() {
		return "MatchMismatch [matchCost=" + matchCost + ", mismatchCost="
				+ mismatchCost + "]";
	}
	
	
}
