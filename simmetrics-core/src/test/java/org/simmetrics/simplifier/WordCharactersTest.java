package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.WordCharacters;

@SuppressWarnings("javadoc")
public class WordCharactersTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new WordCharacters();
	}

	@Override
	protected T[] getTests() {
		return new T[] { new T("##", "  "),
				new T("The ##th Hour", "The   th Hour"),
				new T("", "") };
	}

}
