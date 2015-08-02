package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.Caverphone2;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class CaverPhone2Test extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new Caverphone2();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TNSA111111"),
				new T("James", "YMS1111111"),
				new T("", "1111111111"),
				new T("Travis", "TRFS111111"),
				new T("Marcus", "MKS1111111"),
				new T("Ozymandias", "ASMNTS1111"),
				new T("Jones", "YNS1111111"),
				new T("Jenkins", "YNKNS11111"),
				new T("Trevor", "TRFA111111"),
				new T("Marinus", "MRNS111111"),
		};
	}

}
