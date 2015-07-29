package org.simmetrics.utils;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.List;
import java.util.Set;

import org.simmetrics.StringMetricBuilder;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

/**
 * Tokenizer that transforms tokens.
 * <p>
 * This class is immutable and thread-safe if its components are.
 * 
 * @see StringMetricBuilder
 */
public final class TransformingTokenizer implements Tokenizer {

	private final Tokenizer tokenizer;

	private final Function<String, String> function;

	/**
	 * Constructs a new transforming tokenizer.
	 * 
	 * Tokenization is delegated to the tokenizer. The resulting tokens are
	 * transformed by a the function.
	 * 
	 * 
	 * @param tokenizer
	 *            delegate tokenizer
	 * @param function
	 *            to transform tokens
	 */
	public TransformingTokenizer(Tokenizer tokenizer,
			Function<String, String> function) {
		checkNotNull(tokenizer);
		checkNotNull(function);
		this.function = function;
		this.tokenizer = tokenizer;
	}

	@Override
	public List<String> tokenizeToList(String input) {
		return newArrayList(transform(tokenizer.tokenizeToList(input), function));
	}

	@Override
	public Set<String> tokenizeToSet(String input) {
		return newHashSet(transform(tokenizer.tokenizeToSet(input),
				function));
	}

	@Override
	public String toString() {
		return Joiner.on(" -> ").join(tokenizer, function);
	}

}