package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.MatchRatingApproach;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class MatchRatingApproachTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new MatchRatingApproach();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TNHSR"),
				new T("James", "JMS"),
				new T("", ""),
				new T("Travis", "TRVS"),
				new T("Marcus", "MRCS"),
				new T("Ozymandias", "OZYNDS"),
				new T("Jones", "JNS"),
				new T("Jenkins", "JNKNS"),
				new T("Trevor", "TRVR"),
				new T("Marinus", "MRNS"),
		};
	}

}
