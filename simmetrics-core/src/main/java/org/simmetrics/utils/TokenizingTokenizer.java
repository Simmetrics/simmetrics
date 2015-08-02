

package org.simmetrics.utils;

import org.simmetrics.StringMetricBuilder;
import org.simmetrics.tokenizers.Tokenizer;

/**
 * A tokenizer that delegates the work to another tokenizer.
 * 
 * @see StringMetricBuilder
 * 
 */
public interface TokenizingTokenizer extends Tokenizing, Tokenizer {
	// Empty: because composite interface
}
