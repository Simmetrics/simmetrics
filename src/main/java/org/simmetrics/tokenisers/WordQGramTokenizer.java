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

package org.simmetrics.tokenisers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/**
 * A QGram Tokeniser for words. A string is broken up into words by a
 * {@link Tokenizer}. For each word q-grams are made by a {@link QGramTokenizer}
 * .
 * 
 * @author mpkorstanje
 * 
 *
 */
public final class WordQGramTokenizer implements Tokenizer {

	private Tokenizer wordTokenizer;
	private QGramTokenizer qGramTokenizer;

	/**
	 * Constructs a TokeniserWordQGram with a {@link WhitespaceTokenizer} as a
	 * word tokenizer and {@link QGram2Tokenizer}. A string is broken up into
	 * words by the word tokenizer. For each word q-grams are made.
	 */
	public WordQGramTokenizer() {
		this(new WhitespaceTokenizer(), new QGram2Tokenizer());
	}

	/**
	 * Constructs a TokeniserWordQGram with a {@link WhitespaceTokenizer} as a
	 * word tokenizer the given q-gram. A string is broken up into words by the
	 * word tokenizer. For each word q-grams are made.
	 */
	public WordQGramTokenizer(QGramTokenizer qGramTokenizer) {
		this(new WhitespaceTokenizer(), qGramTokenizer);
	}

	/**
	 * Constructs a TokeniserWordQGram with the given word and q-gram
	 * tokenizers. A string is broken up into words by the word tokenizer. For
	 * each word q-grams are made.
	 * 
	 * @param wordTokenizer
	 *            word tokenizer to use to split input into words
	 * @param qGramTokenizer
	 *            q-gram tokenizer to use to split words into q grams
	 */
	public WordQGramTokenizer(Tokenizer wordTokenizer,
			QGramTokenizer qGramTokenizer) {
		super();
		this.wordTokenizer = wordTokenizer;
		this.qGramTokenizer = qGramTokenizer;
	}

	public void setWordTokenizer(Tokenizer wordTokenizer) {
		this.wordTokenizer = wordTokenizer;
	}

	public void setqGramTokenizer(QGramTokenizer qGramTokenizer) {
		this.qGramTokenizer = qGramTokenizer;
	}

	public ArrayList<String> tokenizeToList(final String input) {
		final ArrayList<String> returnArrayList = new ArrayList<>(
				input.length());
		final List<String> words = wordTokenizer.tokenizeToList(input);

		// for each word
		for (String word : words) {
			// find all qgrams
			returnArrayList.addAll(qGramTokenizer.tokenizeToList(word));

		}

		return returnArrayList;
	}

	public Set<String> tokenizeToSet(final String input) {

		// tokenizeToArray is not reused here on purpose. Removing duplicate
		// words early means these don't have to be tokenized multiple times.
		// Increases performance.

		final Set<String> returnSet = new HashSet<>(input.length());
		final Set<String> words = wordTokenizer.tokenizeToSet(input);

		// for each word
		for (String word : words) {
			// find all qgrams
			returnSet.addAll(qGramTokenizer.tokenizeToList(word));
		}

		return returnSet;
	}

	public String toString() {

		return getClass().getName() + " [" + wordTokenizer + ", "
				+ qGramTokenizer + "]";
	}

}
