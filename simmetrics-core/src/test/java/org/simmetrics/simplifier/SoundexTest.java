package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Soundex;

@SuppressWarnings("javadoc")
public class SoundexTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new Soundex();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "T526"),
				new T("James", "J520"),
				new T("", ""),
				new T("Travis", "T612"),
				new T("Marcus", "M622"),
				
				new T("Ozymandias", "O255"),
				new T("Jones", "J520"),
				new T("Jenkins", "J525"),
				new T("Trevor", "T616"),
				new T("Marinus", "M652"),
		};
	}

}
