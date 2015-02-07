package org.simmetrics.utils;

import static com.google.common.collect.Collections2.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public final class FilteringTokenizer implements Tokenizer {

	private Tokenizer tokenizer;

	private final Predicate<String> filter;

	FilteringTokenizer(Predicate<String> filter) {
		super();
		this.filter = filter;
	}

	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	@Override
	public ArrayList<String> tokenizeToList(String input) {
		return new ArrayList<>(filter(tokenizer.tokenizeToList(input),
				filter));
	}

	@Override
	public Set<String> tokenizeToSet(String input) {
		return new HashSet<>(filter(tokenizer.tokenizeToSet(input), filter));
	}

	@Override
	public String toString() {
		return tokenizer + "->" + filter;
	}

}