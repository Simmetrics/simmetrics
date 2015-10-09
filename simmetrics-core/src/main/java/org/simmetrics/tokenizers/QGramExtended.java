/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.simmetrics.tokenizers;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.repeat;
import static java.util.Collections.emptyList;

import java.util.List;

/**
 * Basic Q-Gram tokenizer for a variable Q.The Q-Gram is extended beyond the
 * length of the string with padding.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @deprecated Use {@link Tokenizers#qGramWithPadding(int, String)}
 */
@Deprecated
public class QGramExtended extends AbstractTokenizer {

	private final static String DEFAULT_START_PADDING = "#";
	private final static String DEFAULT_END_PADDING = "#";

	private final String endPadding;
	private final String startPadding;

	private final QGram tokenizer;

	/**
	 * Constructs a q-gram tokenizer with the given q and padding.
	 * 
	 * @param q
	 *            size of the tokens
	 * @param startPadding
	 *            padding to apply at the start of short tokens
	 * @param endPadding
	 *            padding to apply at the end of short tokens
	 */
	public QGramExtended(int q, String startPadding, String endPadding) {
		checkArgument(!startPadding.isEmpty(), "startPadding may not be empty");
		checkArgument(!endPadding.isEmpty(), "endPadding may not be empty");

		this.tokenizer = new QGram(q);
		this.startPadding = repeat(startPadding, q - 1);
		this.endPadding = repeat(endPadding, q - 1);
	}

	/**
	 * Constructs a q-gram tokenizer with the given q and default padding.
	 * 
	 * @param q
	 *            size of the tokens
	 */
	public QGramExtended(int q) {
		this(q, DEFAULT_START_PADDING, DEFAULT_END_PADDING);
	}

	/**
	 * Returns the start padding.
	 * 
	 * @return the start padding
	 */
	public String getStartPadding() {
		return startPadding;
	}

	/**
	 * Returns the end padding.
	 * 
	 * @return the end padding
	 */
	public String getEndPadding() {
		return endPadding;
	}

	@Override
	public List<String> tokenizeToList(String input) {
		if (input.isEmpty()) {
			return emptyList();
		}

		return tokenizer.tokenizeToList(startPadding + input + endPadding);
	}

	@Override
	public String toString() {
		return "QGramExtendedTokenizer [startPadding=" + startPadding
				+ ", endPadding=" + endPadding + ", q=" + tokenizer.getQ()
				+ "]";
	}

}
