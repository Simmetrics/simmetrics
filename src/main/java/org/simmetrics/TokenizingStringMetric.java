package org.simmetrics;

import java.util.ArrayList;
import java.util.Set;

import org.simmetrics.simplifier.PassThroughSimplifier;
import org.simmetrics.simplifier.Simplifier;
import org.simmetrics.tokenisers.Tokenizer;

/**
 * Abstract metric that handles simplification and tokenization of strings
 * before comparing them. By default strings are not simplified.
 * 
 * Implementing classes can access the tokenizer through
 * {@link #tokenizeToList(String)} and {@link #tokenizeToSet(String)}.
 * 
 * @author mpkorstanje
 *
 */
public abstract class TokenizingStringMetric extends SimplyfingStringMetric
		implements Tokenizing {

	private Tokenizer tokenizer;

	/**
	 * Constructs a metric with the given simplifier and tokenizer.
	 * 
	 * @param simplifier
	 *            simplifier to use
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public TokenizingStringMetric(Simplifier simplifier, Tokenizer tokenizer) {
		super(simplifier);
		this.tokenizer = tokenizer;

	}

	/**
	 * Constructs a metric with the given tokenizer.
	 * 
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public TokenizingStringMetric(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	/**
	 * Returns the a tokenized version of the input string. The resulting list
	 * contains a list of tokens as found in the string. May contain duplicates
	 * when duplicate tokens occur in the input string.
	 * 
	 * @param input
	 *            string to tokenize
	 * @return a list of tokens
	 */
	protected ArrayList<String> tokenizeToList(String input) {
		return tokenizer.tokenizeToList(input);
	}

	/**
	 * Returns the unique tokens in the input string.
	 * 
	 * @param input
	 *            string to tokenize
	 * @return a set of tokens
	 */
	protected Set<String> tokenizeToSet(String input) {
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
