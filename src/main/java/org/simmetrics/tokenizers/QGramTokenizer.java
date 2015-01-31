/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. SimMetrics is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * SimMetrics is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */

package org.simmetrics.tokenizers;

import java.util.ArrayList;

/**
 * Basic Q-Gram tokenizer for a variable Q.
 * 
 * @author mpkorstanje
 *
 */
public class QGramTokenizer extends AbstractTokenizer {

	private final int q;

	public QGramTokenizer(int q) {
		this.q = q;
	}
	
	public int getQ() {
		return q;
	}

	public ArrayList<String> tokenizeToList(final String input) {
		final ArrayList<String> ret = new ArrayList<>();

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
