package org.simmetrics.utils;

import static com.google.common.collect.Collections2.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

public final class FilteringTokenizer implements Tokenizer {

	private final Tokenizer tokenizer;

	private final Predicate<String> filter;

	public FilteringTokenizer(Tokenizer tokenizer, Predicate<String> predicate) {
		Preconditions.checkNotNull(predicate);
		this.filter = predicate;
		this.tokenizer = tokenizer;
	}

	@Override
	public ArrayList<String> tokenizeToList(String input) {
		return new ArrayList<>(filter(tokenizer.tokenizeToList(input), filter));
	}

	@Override
	public Set<String> tokenizeToSet(String input) {
		return new HashSet<>(filter(tokenizer.tokenizeToSet(input), filter));
	}

	@Override
	public String toString() {
		return Joiner.on(" -> ").join(tokenizer, filter);
	}

}