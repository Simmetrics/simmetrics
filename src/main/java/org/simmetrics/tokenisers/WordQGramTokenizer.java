/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.simmetrics.tokenisers;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import org.simmetrics.wordhandlers.TermHandler;

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

	public ArrayList<String> tokenizeToList(final String input) {
		final ArrayList<String> returnArrayList = new ArrayList<String>(
				input.length());
		final ArrayList<String> words = wordTokenizer.tokenizeToList(input);

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

		final Set<String> returnSet = new HashSet<String>(input.length());
		final Set<String> words = wordTokenizer.tokenizeToSet(input);

		// for each word
		for (String word : words) {
			// find all qgrams
			returnSet.addAll(qGramTokenizer.tokenizeToList(word));
		}

		return returnSet;
	}

	/**
	 * Gets the stop word handler used by the word tokenizer.
	 * 
	 * @return the stop word handler used by the word tokenizer
	 */
	public TermHandler getStopWordHandler() {
		return wordTokenizer.getStopWordHandler();
	}

	/**
	 * Sets the stop word handler on the word tokenizer.
	 * 
	 * @param stopWordHandler
	 *            to set on the word tokenizer
	 */
	public void setStopWordHandler(TermHandler stopWordHandler) {
		wordTokenizer.setStopWordHandler(stopWordHandler);
	}

	public final String toString() {

		return getClass().getSimpleName() + " [" + wordTokenizer + ", "
				+ qGramTokenizer + "]";
	}

}
