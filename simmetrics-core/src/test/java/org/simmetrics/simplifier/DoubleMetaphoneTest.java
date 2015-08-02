package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.DoubleMetaphone;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class DoubleMetaphoneTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new DoubleMetaphone();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TNSR"),
				new T("James", "JMS"),
				new T("", ""),
				new T("Travis", "TRFS"),
				new T("Marcus", "MRKS"),
				new T("Ozymandias", "ASMN"),
				new T("Jones", "JNS"),
				new T("Jenkins", "JNKN"),
				new T("Trevor", "TRFR"),
				new T("Marinus", "MRNS"),
		};
	}

}
