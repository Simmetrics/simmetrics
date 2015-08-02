package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.RefinedSoundex;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class RefinedSoundexTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new RefinedSoundex();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "T6080309"),
				new T("James", "J40803"),
				new T("", ""),
				new T("Travis", "T690203"),
				new T("Marcus", "M809303"),
				
				new T("Ozymandias", "O050808603"),
				new T("Jones", "J40803"),
				new T("Jenkins", "J4083083"),
				new T("Trevor", "T690209"),
				new T("Marinus", "M8090803"),
		};
	}

}
