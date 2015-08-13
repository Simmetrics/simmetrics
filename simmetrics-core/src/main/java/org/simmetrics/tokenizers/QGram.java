/*
 * #%L Simmetrics Core %% Copyright (C) 2014 - 2015 Simmetrics Authors %% This
 * program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/gpl-3.0.html>. #L%
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
