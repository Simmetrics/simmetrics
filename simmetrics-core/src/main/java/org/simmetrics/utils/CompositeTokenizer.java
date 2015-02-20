package org.simmetrics.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Joiner;

public final class CompositeTokenizer implements Tokenizer {

	private final List<Tokenizer> tokenizers;

	public CompositeTokenizer(List<Tokenizer> tokenizers) {
		this.tokenizers = tokenizers;
	}

	@Override
	public List<String> tokenizeToList(final String input) {
		List<String> tokens = new ArrayList<>(1);
		tokens.add(input);
		
		List<String> newTokens = new ArrayList<>(input.length());
		for(Tokenizer t : tokenizers){
			for(String token : tokens){
				newTokens.addAll(t.tokenizeToList(token));
			}
			tokens = newTokens;
			newTokens = new ArrayList<>(input.length());
		}
		
		return tokens;
	}

	@Override
	public Set<String> tokenizeToSet(final String input) {

		// tokenizeToArray is not reused here on purpose. Removing duplicate
		// words early means these don't have to be tokenized multiple
		// times. Increases performance.

		Set<String> tokens = new HashSet<>(1);
		tokens.add(input);
		
		Set<String> newTokens = new HashSet<>(input.length());
		for(Tokenizer t : tokenizers){
			for(String token : tokens){
				newTokens.addAll(t.tokenizeToList(token));
			}
			tokens = newTokens;
			newTokens = new HashSet<>(input.length());
		}
		
		return tokens;
	}

	@Override
	public String toString() {
		return Joiner.on(" -> ").join(tokenizers);
	}

}