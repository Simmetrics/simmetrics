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

package uk.ac.shef.wit.simmetrics.similaritymetrics;

import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser;
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserWhitespace;

import java.util.ArrayList;

/**
 * ChapmanOrderedNameCompoundSimilarity tests similarity upon the
 * most similar in terms of token based names where the later names are valued
 * higher than earlier names. Surnames are less flexible.
 *
 * This is intended to provide a better rating for lists of proper names.
 *
 *
 * @author Sam Chapman, NLP Group, Sheffield Uni, UK
 * 
 */
public final class ChapmanOrderedNameCompoundSimilarity extends
		AbstractStringMetric   {

	private final float ESTIMATEDTIMINGCONST = 0.026571428571428571428571428571429f;

	final InterfaceTokeniser tokenizer;

	private final AbstractStringMetric internalStringMetric1 = new Soundex();

	private final AbstractStringMetric internalStringMetric2 = new SmithWaterman();

	/**
	 * Constructs a ChapmanOrderedNameCompoundSimilarity metric with a {@link TokeniserWhitespace}.
	 */
	public ChapmanOrderedNameCompoundSimilarity() {
		this.tokenizer = new TokeniserWhitespace();
	}

	/**
	 * Constructs a ChapmanOrderedNameCompoundSimilarity metric with the given tokenizer.
	 *
	 * @param tokeniser
	 *            tokenizer to use
	 */
	public ChapmanOrderedNameCompoundSimilarity(
			final InterfaceTokeniser tokenizer) {
		this.tokenizer = tokenizer;
	}
	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Chapman Ordered Name Compound Similarity algorithm whereby terms are matched and tested against the standard soundex algorithm - this is intended to provide a better rating for lists of proper names.";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Tokens = tokenizer.tokenizeToArrayList(string1).size();
		final float str2Tokens = tokenizer.tokenizeToArrayList(string2).size();
		return (tokenizer.tokenizeToArrayList(string1).size() + tokenizer
				.tokenizeToArrayList(string2).size())
				* ((str1Tokens + str2Tokens) * ESTIMATEDTIMINGCONST);
	}

	public final float getSimilarity(final String string1, final String string2) {
		// split the strings into tokens for comparison
		final ArrayList<String> str1Tokens = tokenizer
				.tokenizeToArrayList(string1);
		final ArrayList<String> str2Tokens = tokenizer
				.tokenizeToArrayList(string2);
		int str1TokenNum = str1Tokens.size();
		int str2TokenNum = str2Tokens.size();
		int minTokens = Math.min(str1TokenNum, str2TokenNum);

		float SKEW_AMMOUNT = 1.0f;

		float sumMatches = 0.0f;
		for (int i = 1; i <= minTokens; i++) {
			float strWeightingAdjustment = ((1.0f / minTokens) + (((((minTokens - i) + 0.5f) - (minTokens / 2.0f)) / minTokens)
					* SKEW_AMMOUNT * (1.0f / minTokens)));
			final String sToken = str1Tokens.get(str1TokenNum - i);
			final String tToken = str2Tokens.get(str2TokenNum - i);

			final float found1 = internalStringMetric1.getSimilarity(sToken,
					tToken);
			final float found2 = internalStringMetric2.getSimilarity(sToken,
					tToken);
			sumMatches += ((0.5f * (found1 + found2)) * strWeightingAdjustment);
		}
		return sumMatches;
	}

	public float getUnNormalisedSimilarity(String string1, String string2) {
		// TODO: check this is valid before use mail sam@dcs.shef.ac.uk if
		// problematic
		return getSimilarity(string1, string2);
	}

}
