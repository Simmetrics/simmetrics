/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */

package org.simmetrics.metrics;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import org.simmetrics.simplifier.PassThroughSimplifier;
import org.simmetrics.simplifier.Simplifier;
import org.simmetrics.tokenisers.Tokenizer;
import org.simmetrics.tokenisers.TokenizingTokenizer;


/**
 * Abstract metric that handles simplification and tokenization of strings
 * before comparing them. By default strings are not simplified.
 * 
 * Implementing classes can access the tokenizer through
 * {@link #tokenizeToList(String)} and {@link #tokenizeToSet(String)}.
 * 
 * @author mpkorstanje
 *
 */
public abstract class TokenizingStringMetric extends SimplyfingStringMetric
		implements Tokenizing {

	private Tokenizer tokenizer;

	/**
	 * Constructs a metric with the given simplifier and tokenizer.
	 * 
	 * @param simplifier
	 *            simplifier to use
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public TokenizingStringMetric(Simplifier simplifier, Tokenizer tokenizer) {
		super(simplifier);
		this.tokenizer = tokenizer;

	}

	/**
	 * Constructs a metric with the given tokenizer.
	 * 
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public TokenizingStringMetric(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public void addTokenizer(TokenizingTokenizer tokenizer) {
		tokenizer.setTokenizer(this.tokenizer);
		this.tokenizer = tokenizer;
	}

	/**
	 * Returns the a tokenized version of the input string. The resulting list
	 * contains a list of tokens as found in the string. May contain duplicates
	 * when duplicate tokens occur in the input string.
	 * 
	 * @param input
	 *            string to tokenize
	 * @return a list of tokens
	 */
	protected ArrayList<String> tokenizeToList(String input) {
		return tokenizer.tokenizeToList(input);
	}

	/**
	 * Returns the unique tokens in the input string.
	 * 
	 * @param input
	 *            string to tokenize
	 * @return a set of tokens
	 */
	protected Set<String> tokenizeToSet(String input) {
		return tokenizer.tokenizeToSet(input);

	}

	@Override
	public String toString() {
		if (getSimplifier() instanceof PassThroughSimplifier) {
			return getClass().getName() + " [" + Objects.toString(tokenizer) + "]";
		}

		return getClass().getName()+ " [" + getSimplifier() + ", "
				+ Objects.toString(tokenizer) + "]";
	}

}
