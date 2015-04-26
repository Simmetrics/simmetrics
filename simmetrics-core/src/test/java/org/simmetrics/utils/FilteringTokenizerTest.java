package org.simmetrics.utils;

import static java.util.Arrays.asList;

import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizerTest;
import org.simmetrics.tokenizers.Whitespace;

import static com.google.common.base.Predicates.*;

public class FilteringTokenizerTest extends TokenizerTest {

	@Override
	protected Tokenizer getTokenizer() {
		return new FilteringTokenizer(new Whitespace(),
				not(in(asList(
				"the", "and", "or"))));
	}

	@Override
	public T[] getTests() {
		return new T[] { new T("the mouse and cat or dog", "mouse", "cat",
				"dog") };
	}
}
