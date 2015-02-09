package org.simmetrics.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Joiner;

public final class CompositeTokenizer implements Tokenizer {

	private final Tokenizer first, second;

	public CompositeTokenizer(Tokenizer first, Tokenizer second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public List<String> tokenizeToList(final String input) {
		final List<String> list = new ArrayList<>(input.length());

		for (String word : first.tokenizeToList(input)) {
			list.addAll(second.tokenizeToList(word));
		}

		return list;
	}

	@Override
	public Set<String> tokenizeToSet(final String input) {

		// tokenizeToArray is not reused here on purpose. Removing duplicate
		// words early means these don't have to be tokenized multiple
		// times. Increases performance.

		final Set<String> set = new HashSet<>(input.length());

		for (String word : first.tokenizeToSet(input)) {
			set.addAll(second.tokenizeToList(word));
		}

		return set;
	}

	@Override
	public String toString() {
		return Joiner.on(" -> ").join(first, second);
	}

}