

package org.simmetrics.tokenizers;

import java.util.List;
import java.util.Set;

/**
 * A tokenizer divides an input string into tokens. A tokenizer may not provide
 * {@code null} as a token.
 */
public interface Tokenizer {

	/**
	 * Return tokenized version of a string as a list of tokens.
	 *
	 * @param input
	 *            input string to tokenize
	 *
	 * @return List tokenized version of a string
	 */
	public List<String> tokenizeToList(String input);

	/**
	 * Return tokenized version of a string as a set of tokens.
	 *
	 * @param input
	 *            input string to tokenize
	 * @return tokenized version of a string as a set
	 */
	public Set<String> tokenizeToSet(String input);

}
