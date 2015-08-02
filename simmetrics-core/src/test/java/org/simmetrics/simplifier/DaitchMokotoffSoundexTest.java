package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.DaitchMokotoffSoundex;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class DaitchMokotoffSoundexTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new DaitchMokotoffSoundex();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "365490"),
				new T("James", "164000"),
				new T("", "000000"),
				new T("Travis", "397400"),
				new T("Marcus", "694400"),
				new T("Ozymandias", "046634"),
				new T("Jones", "164000"),
				new T("Jenkins", "165640"),
				new T("Trevor", "397900"),
				new T("Marinus", "696400"),
		};
	}

}
