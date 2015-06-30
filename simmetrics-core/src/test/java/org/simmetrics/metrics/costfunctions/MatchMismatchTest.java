package org.simmetrics.metrics.costfunctions;

import org.simmetrics.metrics.functions.MatchMismatch;
import org.simmetrics.metrics.functions.Substitution;

@SuppressWarnings("javadoc")
public class MatchMismatchTest extends SubstitutionTest {
	
	@Override
	public Substitution getCost() {
		return new MatchMismatch(1.0f, -0.25f);
	}

	@Override
	public T[] getTests() {
		return new T[]{
				new T(1.000f, "a", 0, "a", 0),
				new T(-0.25f, "a", 0, "b", 0),
				new T(1.000f, "ab", 0, "ba", 1),
				new T(-0.25f, "ab", 1, "ba", 1),
				
		};
	}

}
