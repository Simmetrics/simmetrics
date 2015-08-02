package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.NonDiacritics;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class NonDiacriticsTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new NonDiacritics();
	}

	@Override
	protected T[] getTests() {
		return new T[] {
				new T("Chilpéric II son of Childeric II",
						"Chilperic II son of Childeric II"),
				new T("The 11th Hour", "The 11th Hour"), 
				new T("", ""), };
	}

}
