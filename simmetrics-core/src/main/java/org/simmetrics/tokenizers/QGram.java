

package org.simmetrics.tokenizers;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic Q-Gram tokenizer for a variable q. Returns a list with the original
 * input for tokens shorter then q.
 * <p>
 * This class is immutable and thread-safe.
 *
 */
public class QGram extends AbstractTokenizer {

	private final int q;

	/**
	 * Constructs a q-gram tokenizer with the given q.
	 * 
	 * @param q
	 *            size of the tokens
	 */

	public QGram(int q) {
		checkArgument(q > 0, "q must be greater then 0");
		this.q = q;
	}

	/**
	 * Returns the q of this tokenizer.
	 * 
	 * @return the q of this tokenizer
	 */
	public int getQ() {
		return q;
	}

	@Override
	public List<String> tokenizeToList(final String input) {
		if (input.isEmpty()) {
			return emptyList();
		}

		if (input.length() <= q) {
			return singletonList(input);
		}

		final List<String> ret = new ArrayList<>(input.length());

		for (int i = 0; i < input.length() - q + 1; i++) {
			ret.add(input.substring(i, i + q));
		}

		return ret;
	}

	@Override
	public String toString() {
		return "QGramTokenizer [q=" + q + "]";
	}

}
