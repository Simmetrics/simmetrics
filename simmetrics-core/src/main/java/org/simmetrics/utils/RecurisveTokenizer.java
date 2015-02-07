package org.simmetrics.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Joiner;

public final class RecurisveTokenizer implements Tokenizer {

	private final Tokenizer inner, outer;

	public RecurisveTokenizer(Tokenizer inner, Tokenizer outer) {
		this.inner = inner;
		this.outer = outer;
	}

	@Override
	public List<String> tokenizeToList(final String input) {
		final List<String> list = new ArrayList<>(input.length());

		for (String word : inner.tokenizeToList(input)) {
			list.addAll(outer.tokenizeToList(word));
		}

		return list;
	}

	@Override
	public Set<String> tokenizeToSet(final String input) {

		// tokenizeToArray is not reused here on purpose. Removing duplicate
		// words early means these don't have to be tokenized multiple
		// times. Increases performance.

		final Set<String> set = new HashSet<>(input.length());

		for (String word : inner.tokenizeToSet(input)) {
			set.addAll(outer.tokenizeToList(word));
		}

		return set;
	}

	@Override
	public String toString() {
		return Joiner.on("->").join(inner, outer);
	}

}