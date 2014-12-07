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

import java.util.HashSet;
import java.util.Set;

/**
 * Implements the DiceSimilarity algorithm providing a similarity measure
 * between two strings using the vector space of presented tokens.
 * 
 * Dices coefficient = (2*Common tokens) / (Number of tokens in String1 + Number
 * of tokens in String2).
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class DiceSimilarity extends AbstractStringMetric 
		 {

	private final float ESTIMATEDTIMINGCONST = 0.00000034457142857142857142857142857146f;

	private final InterfaceTokeniser tokeniser;

	/**
	 * Constructs a DiceSimilarity metric with a {@link TokeniserWhitespace}.
	 */
	public DiceSimilarity() {
		tokeniser = new TokeniserWhitespace();
	}

	/**
	 * Constructs a DiceSimilarity metric with the given tokenizer.
	 *
	 * @param tokeniser
	 *            tokenizer to use
	 */
	public DiceSimilarity(final InterfaceTokeniser tokeniserToUse) {
		tokeniser = tokeniserToUse;
	}
	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the DiceSimilarity algorithm providing a similarity measure between two strings using the vector space of present terms";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Length = string1.length();
		final float str2Length = string2.length();
		return (str1Length + str2Length)
				* ((str1Length + str2Length) * ESTIMATEDTIMINGCONST);
	}

	public float getSimilarity(final String string1, final String string2) {
		final Set<String> str1Tokens = tokeniser.tokenizeToSet(string1);
		final Set<String> str2Tokens = tokeniser.tokenizeToSet(string2);

		final Set<String> allTokens = new HashSet<String>();
		allTokens.addAll(str1Tokens);
		allTokens.addAll(str2Tokens);

		final int commonTerms = (str1Tokens.size() + str2Tokens.size())
				- allTokens.size();

		// return Dices coefficient = (2*Common Terms) / (Number of distinct
		// terms in String1 + Number of distinct terms in String2)
		return (2.0f * commonTerms) / (str1Tokens.size() + str2Tokens.size());
	}

}
