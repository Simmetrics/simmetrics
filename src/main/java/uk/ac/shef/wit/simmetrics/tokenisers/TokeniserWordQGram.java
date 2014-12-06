/**
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

package uk.ac.shef.wit.simmetrics.tokenisers;

import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;
import uk.ac.shef.wit.simmetrics.wordhandlers.DummyStopTermHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Package: uk.ac.shef.wit.simmetrics.tokenisers Description: TokeniserWordQGram
 * implements a QGram Tokeniser for words
 * 
 * @author mpkorstanje
 * 
 * @version 1.0
 * 
 *
 */
public final class TokeniserWordQGram implements InterfaceTokeniser,
		Serializable {

	private InterfaceTokeniser tokenizerWord = new TokeniserWhitespace();
	private InterfaceTokeniser tokenizerQGram = new TokeniserQGram2();

	/**
	 * stopWordHandler used by the tokenisation.
	 */
	private InterfaceTermHandler stopWordHandler = new DummyStopTermHandler();

	/**
	 * displays the tokenisation method.
	 *
	 * @return the tokenisation method
	 */
	public final String getShortDescriptionString() {
		return "TokeniserWordQGram";
	}

	/**
	 * gets the stop word handler used.
	 * 
	 * @return the stop word handler used
	 */
	public InterfaceTermHandler getStopWordHandler() {
		return stopWordHandler;
	}

	/**
	 * sets the stop word handler used with the handler given.
	 * 
	 * @param stopWordHandler
	 *            the given stop word hanlder
	 */
	public void setStopWordHandler(final InterfaceTermHandler stopWordHandler) {
		this.stopWordHandler = stopWordHandler;
	}

	public void setTokenizerQGram(InterfaceTokeniser tokenizerQGram) {
		this.tokenizerQGram = tokenizerQGram;
	}

	public InterfaceTokeniser getTokenizerQGram() {
		return tokenizerQGram;
	}

	public void setTokenizerWord(InterfaceTokeniser tokenizerWord) {
		this.tokenizerWord = tokenizerWord;
	}

	public InterfaceTokeniser getTokenizerWord() {
		return tokenizerWord;
	}

	/**
	 * displays the delimiters used
	 *
	 * @return the delimiters used
	 */
	public final String getDelimiters() {
		return tokenizerWord.getDelimiters() + tokenizerQGram.getDelimiters();
	}

	/**
	 * Return tokenized version of a string.
	 *
	 * @param input
	 * @return tokenized version of a string
	 */
	public final ArrayList<String> tokenizeToArrayList(final String input) {
		final ArrayList<String> returnArrayList = new ArrayList<String>(
				input.length());
		final ArrayList<String> words = tokenizerWord
				.tokenizeToArrayList(input);

		// for each word
		for (String word : words) {
			if (!stopWordHandler.isWord(word)) {
				// find all qgrams
				returnArrayList
						.addAll(tokenizerQGram.tokenizeToArrayList(word));
			}
		}

		return returnArrayList;
	}

	/**
	 * Return tokenized set of a string.
	 *
	 * @param input
	 * @return tokenized version of a string as a set
	 */
	public Set<String> tokenizeToSet(final String input) {

		// tokenizeToArray is not reused here on purpose. Removing duplicate
		// words early means these don't have to be tokenized multiple times.
		// Increases performance.

		final Set<String> returnSet = new HashSet<String>();
		final Set<String> words = tokenizerWord.tokenizeToSet(input);

		// for each word
		for (String word : words) {
			if (!stopWordHandler.isWord(word)) {
				// find all qgrams
				returnSet.addAll(tokenizerQGram.tokenizeToArrayList(word));
			}
		}

		return returnSet;
	}
}
