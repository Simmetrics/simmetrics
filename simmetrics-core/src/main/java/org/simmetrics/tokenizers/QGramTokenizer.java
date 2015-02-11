/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */

package org.simmetrics.tokenizers;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * Basic Q-Gram tokenizer for a variable q. Returns a list with the original
 * input for tokens shorter then q.
 * 
 * @author mpkorstanje
 *
 */
public class QGramTokenizer extends AbstractTokenizer {

	private final int q;

	/**
	 * Constructs a q-gram tokenizer with the given q.
	 * 
	 * @param q
	 *            size of the tokens
	 * 
	 */

	public QGramTokenizer(int q) {
		Preconditions.checkArgument(q > 0, "q must be greater then 0");
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
		final List<String> ret = new ArrayList<>();

		if (input.isEmpty()) {
			return ret;
		}

		if (input.length() <= q) {
			ret.add(input);
			return ret;
		}

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
