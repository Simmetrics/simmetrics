package org.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.simmetrics.wordhandlers.GenericStopTermHandler;
import org.simmetrics.wordhandlers.TermHandler;

import com.google.common.base.Predicate;

import static com.google.common.collect.Collections2.filter;

public class TermFilterTokenizer extends AbstractTokenizer implements TokenizingTokenizer{

	private Tokenizer tokenizer = new WhitespaceTokenizer();

	private TermHandler termHandler = new GenericStopTermHandler();

	public TermHandler getTermHandler() {
		return termHandler;
	}

	public void setTermHandler(TermHandler termHandler) {
		this.termHandler = termHandler;
	}

	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	private final Predicate<String> isWord = new Predicate<String>() {

		public boolean apply(String input) {
			return termHandler.isWord(input);
		}
	};

	public ArrayList<String> tokenizeToList(String input) {
		return new ArrayList<String>(filter(tokenizer.tokenizeToList(input),
				isWord));
	}

	@Override
	public Set<String> tokenizeToSet(String input) {
		return new HashSet<String>(filter(tokenizer.tokenizeToSet(input),
				isWord));
	}

}
