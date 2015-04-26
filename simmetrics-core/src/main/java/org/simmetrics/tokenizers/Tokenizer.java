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

import java.util.List;
import java.util.Set;

/**
 * A tokenizer divides an input string into tokens. A tokenizer may not provide
 * empty strings as tokens.
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
