package org.simmetrics;

import java.util.ArrayList;
import java.util.Set;

import org.simmetrics.tokenisers.Tokenizer;

import uk.ac.shef.wit.simmetrics.simplifier.PassThroughSimplifier;

public abstract class TokenizingStringMetric extends SimplyfingStringMetric
		implements Tokenizing {

	private Tokenizer tokenizer;

	public TokenizingStringMetric(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public ArrayList<String> tokenizeToList(String input) {
		return tokenizer.tokenizeToList(input);
	}

	public Set<String> tokenizeToSet(String input) {
		return tokenizer.tokenizeToSet(input);

	}

	@Override
	public String toString() {
		if (getSimplifier() instanceof PassThroughSimplifier) {
			return getClass().getSimpleName();
		}

		return getClass().getSimpleName() + " [" + getSimplifier() + ", "
				+ tokenizer + "]";
	}
}
