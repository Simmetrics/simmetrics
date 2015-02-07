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
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */

package org.simmetrics.utils;

import org.simmetrics.tokenizers.Tokenizer;


/**
 * Interface for metrics that use a {@link Tokenizer} to tokenize strings. These
 * methods can be called by batching-processing systems to inject a
 * {@link CachingTokenizer}s.
 * 
 * 
 * For the benefit of these caching systems, implementations should take care to
 * call the tokenizer exactly once for each argument.
 * 
 * <pre>
 *  <code>
 * 	public ArrayList{@code<String>} tokenizeToList(String input) {
 * 		return tokenizer.tokenizeToList(input);
 * 	}
 * 
 * 	public Set{@code<String>} tokenizeToSet(String input) {
 * 		return tokenizer.tokenizeToSet(input);
 * 
 * 	}
 * </code>
 * </pre>
 * 
 * @author mpkorstanje
 *
 */
public interface Tokenizing {
	/**
	 * Gets the tokenizer. The tokenizer may not be null.
	 * 
	 * @return the non-null tokenizer used
	 */

	public Tokenizer getTokenizer();

	/**
	 * Sets the tokenizer. The tokenizer may not be null.
	 * 
	 * @param tokenizer
	 *            a non-null tokenizer
	 */
	public void setTokenizer(Tokenizer tokenizer);



}
