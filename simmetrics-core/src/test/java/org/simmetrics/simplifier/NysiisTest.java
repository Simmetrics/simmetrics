package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.Nysiis;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class NysiisTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new Nysiis();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TANASA"),
				new T("James", "JAN"),
				new T("", ""),
				new T("Travis", "TRAV"),
				new T("Marcus", "MARC"),
				new T("Ozymandias", "OSYNAN"),
				new T("Jones", "JAN"),
				new T("Jenkins", "JANCAN"),
				new T("Trevor", "TRAFAR"),
				new T("Marinus", "MARAN"),
		};
	}

}
