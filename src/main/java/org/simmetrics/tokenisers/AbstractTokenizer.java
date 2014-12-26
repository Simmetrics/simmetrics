package org.simmetrics.tokenisers;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.wordhandlers.TermHandler;

public abstract class AbstractTokenizer implements Tokenizer {

	public Set<String> tokenizeToSet(final String input) {
		return new HashSet<String>(tokenizeToList(input));
	}

	public final String toString() {
		return getClass().getSimpleName();
	}

}