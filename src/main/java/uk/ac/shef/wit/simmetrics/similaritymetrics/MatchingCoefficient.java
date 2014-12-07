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
 * Implements the Matching Coefficient algorithm providing a similarity measure
 * between two strings
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class MatchingCoefficient extends AbstractStringMetric {

	private final float ESTIMATEDTIMINGCONST = 2.0e-4f;

	private final InterfaceTokeniser tokenizer;

	/**
	 * Constructs a MatchingCoefficient metric with a
	 * {@link TokeniserWhitespace}.
	 */
	public MatchingCoefficient() {
		this.tokenizer = new TokeniserWhitespace();
	}

	/**
	 * Constructs a MatchingCoefficient metric with the given tokenizer.
	 *
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public MatchingCoefficient(final InterfaceTokeniser tokenizer) {
		this.tokenizer = tokenizer;
	}
	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Matching Coefficient algorithm providing a similarity measure between two strings";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Tokens = tokenizer.tokenizeToArrayList(string1).size();
		final float str2Tokens = tokenizer.tokenizeToArrayList(string2).size();
		return (str2Tokens * str1Tokens) * ESTIMATEDTIMINGCONST;
	}

	public float getSimilarity(final String string1, final String string2) {
		final ArrayList<String> str1Tokens = tokenizer
				.tokenizeToArrayList(string1);
		final ArrayList<String> str2Tokens = tokenizer
				.tokenizeToArrayList(string2);

		final int totalPossible = Math
				.max(str1Tokens.size(), str2Tokens.size());
		return getInnerUnNormalisedSimilarity(str1Tokens, str2Tokens)
				/ (float) totalPossible;
	}

	public float getUnNormalisedSimilarity(String string1, String string2) {
		final ArrayList<String> str1Tokens = tokenizer
				.tokenizeToArrayList(string1);
		final ArrayList<String> str2Tokens = tokenizer
				.tokenizeToArrayList(string2);

		return getInnerUnNormalisedSimilarity(str1Tokens, str2Tokens);
	}

	private float getInnerUnNormalisedSimilarity(
			final ArrayList<String> str1Tokens,
			final ArrayList<String> str2Tokens) {
		int totalFound = 0;
		for (Object str1Token : str1Tokens) {
			final String sToken = (String) str1Token;
			boolean found = false;
			for (Object str2Token : str2Tokens) {
				final String tToken = (String) str2Token;
				if (sToken.equals(tToken)) {
					found = true;
				}
			}
			if (found) {
				totalFound++;
			}
		}
		return (float) totalFound;
	}
}
