
package org.simmetrics.tokenizers;

import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Whitespace;

@SuppressWarnings("javadoc")
public class WhitespaceTokenizerTest extends TokenizerTest {

	@Override
	protected Tokenizer getTokenizer() {
		return new Whitespace();
	}

	@Override
	public T[] getTests() {

		return new T[] { 
				new T(""),
				new T(" "),
				new T("A B C", "A", "B", "C"),
				new T("A   B  C", "A", "B", "C"),
				new T("A\nB", "A", "B"),
				new T("A\tB", "A", "B"), 
				new T("A\t\nB", "A", "B"),
		};
	}
}
