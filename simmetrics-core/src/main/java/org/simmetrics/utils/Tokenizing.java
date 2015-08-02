

package org.simmetrics.utils;

import org.simmetrics.tokenizers.Tokenizer;

/**
 * Interface for classes that delegate to a {@link Tokenizer}.
 * 
 * 
 *
 */
public interface Tokenizing {
	/**
	 * Gets the tokenizer. When null no simplifier was set.
	 * 
	 * @return the tokenizer or null when not set
	 */

	public Tokenizer getTokenizer();

	/**
	 * Sets the tokenizer. May not be null.
	 * 
	 * @param tokenizer
	 *            a tokenizer to set
	 */
	public void setTokenizer(Tokenizer tokenizer);

}
