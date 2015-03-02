package org.simmetrics.utils;

import static com.google.common.collect.Collections2.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.simmetrics.StringMetricBuilder;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * Tokenizer that filters tokens that don't match a predicate.
 * <p>
 * This class is immutable and thread-safe if its components are.
 * 
 * @see StringMetricBuilder
 */
public final class FilteringTokenizer implements Tokenizer {

	private final Tokenizer tokenizer;

	private final Predicate<String> filter;

	/**
	 * Constructs a new filtering tokenizer.
	 * 
	 * Tokenization is delegated to the tokenizer. From the result all tokens
	 * that don't match a predicate are removed.
	 * 
	 * 
	 * @param tokenizer
	 *            delegate tokenizer
	 * @param predicate
	 *            for tokens to keep
	 */
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