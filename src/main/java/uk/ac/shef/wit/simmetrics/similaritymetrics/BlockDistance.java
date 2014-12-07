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
import java.util.ArrayList;
import static java.lang.Math.abs;
/**
 * Implements the Block distance algorithm whereby vector space block distance
 * between tokens is used to determine a similarity.
 * 
 * Uses the {@link TokeniserWhitespace} by default.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class BlockDistance extends AbstractStringMetric 
		 {

	private final float ESTIMATEDTIMINGCONST = 6.4457142857142857142857142857146e-5f;

	private final InterfaceTokeniser tokeniser;

	/**
	 * Constructs a BlockDistance metric with a {@link TokeniserWhitespace}.
	 */
	public BlockDistance() {
		this.tokeniser = new TokeniserWhitespace();
	}

	/**
	 * Constructs a BlockDistance metric with the given tokenizer.
	 *
	 * @param tokeniser
	 *            tokenizer to use
	 */
	public BlockDistance(final InterfaceTokeniser tokeniser) {
		this.tokeniser = tokeniser;
	}

	@Override
	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Block distance algorithm whereby vector space block distance is used to determine a similarity";
	}

	@Override
	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Tokens = tokeniser.tokenizeToArrayList(string1).size();
		final float str2Tokens = tokeniser.tokenizeToArrayList(string2).size();
		return (((str1Tokens + str2Tokens) * str1Tokens) + ((str1Tokens + str2Tokens) * str2Tokens))
				* ESTIMATEDTIMINGCONST;
	}

	public float getSimilarity(final String string1, final String string2) {
		final ArrayList<String> str1Tokens = tokeniser
				.tokenizeToArrayList(string1);
		final ArrayList<String> str2Tokens = tokeniser
				.tokenizeToArrayList(string2);

		final float totalPossible = (float) (str1Tokens.size() + str2Tokens
				.size());

		final float totalDistance = getInnerUnNormalizedSimilarity(str1Tokens, str2Tokens);
		return (totalPossible - totalDistance) / totalPossible;
	}

	public float getUnNormalisedSimilarity(final String string1,
			final String string2) {
		final ArrayList<String> str1Tokens = tokeniser
				.tokenizeToArrayList(string1);
		final ArrayList<String> str2Tokens = tokeniser
				.tokenizeToArrayList(string2);

		return getInnerUnNormalizedSimilarity(str1Tokens, str2Tokens);
	}

	private float getInnerUnNormalizedSimilarity(
			final ArrayList<String> str1Tokens,
			final ArrayList<String> str2Tokens) {
		final Set<String> allTokens = new HashSet<String>();
		allTokens.addAll(str1Tokens);
		allTokens.addAll(str2Tokens);

		int totalDistance = 0;
		for (String token : allTokens) {
			int countInString1 = 0;
			int countInString2 = 0;
			for (String sToken : str1Tokens) {
				if (sToken.equals(token)) {
					countInString1++;
				}
			}
			for (String sToken : str2Tokens) {
				if (sToken.equals(token)) {
					countInString2++;
				}
			}
			
			totalDistance += abs(countInString1 - countInString2);

		}
		return totalDistance;
	}
}
