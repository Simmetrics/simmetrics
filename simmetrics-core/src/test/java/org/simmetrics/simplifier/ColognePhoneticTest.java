package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.ColognePhonetic;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class ColognePhoneticTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new ColognePhonetic();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "2687"),
				new T("James", "068"),
				new T("", ""),
				new T("Travis", "2738"),
				new T("Marcus", "6748"),
				new T("Ozymandias", "086628"),
				new T("Jones", "068"),
				new T("Jenkins", "06468"),
				new T("Trevor", "2737"),
				new T("Marinus", "6768"),
		};
	}

}
