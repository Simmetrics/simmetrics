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
 * @deprecated Use {@link Tokenizers#qGram(int)} instead
 */
@Deprecated
public class QGram extends AbstractTokenizer {

	private final int q;
	private final boolean filter;
	
	QGram(int q, boolean filter) {
		checkArgument(q > 0, "q must be greater then 0");
		this.q = q;
		this.filter = filter;
	}

	/**
	 * Constructs a q-gram tokenizer with the given q.
	 * 
	 * @param q
	 *            size of the tokens
	 */

	public QGram(int q) {
		this(q,false);
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

		if (!filter && input.length() <= q) {
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
