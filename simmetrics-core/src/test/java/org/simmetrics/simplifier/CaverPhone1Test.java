package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.Caverphone1;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class CaverPhone1Test extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new Caverphone1();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TNS111"),
				new T("James", "YMS111"),
				new T("", "111111"),
				new T("Travis", "TRFS11"),
				new T("Marcus", "MKS111"),
				new T("Ozymandias", "ASMNTS"),
				new T("Jones", "YNS111"),
				new T("Jenkins", "YNKNS1"),
				new T("Trevor", "TRF111"),
				new T("Marinus", "MRNS11"),
		};
	}

}
