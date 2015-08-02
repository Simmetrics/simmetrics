

package org.simmetrics.tokenizers;

import java.util.HashSet;
import java.util.Set;

/**
 * Convenience tokenizer. Provides default implementation for to set.
 *
 */
public abstract class AbstractTokenizer implements Tokenizer {

	@Override
	public Set<String> tokenizeToSet(final String input) {
		return new HashSet<>(tokenizeToList(input));
	}

}