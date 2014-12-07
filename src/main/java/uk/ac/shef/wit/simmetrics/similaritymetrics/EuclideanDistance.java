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

import static java.lang.Math.sqrt;

/**
 * Implements the Euclidean Distance algorithm providing a similarity measure
 * between two strings using the vector space of combined terms as the
 * dimensions"
 *
 * @author Sam Chapman
 * @version 1.2
 */
public final class EuclideanDistance extends AbstractStringMetric 
		 {

	private final float ESTIMATEDTIMINGCONST = 7.4457142857142857142857142857146e-5f;

	private final InterfaceTokeniser tokenizer;

	/**
	 * Constructs a EuclideanDistance metric with a {@link TokeniserWhitespace}.
	 */
	public EuclideanDistance() {
		this.tokenizer = new TokeniserWhitespace();
	}

	/**
	 * Constructs a EuclideanDistance metric with the given tokenizer.
	 *
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public EuclideanDistance(final InterfaceTokeniser tokenizer) {
		this.tokenizer = tokenizer;
	}
	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Euclidean Distancey algorithm providing a similarity measure between two stringsusing the vector space of combined terms as the dimensions";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Tokens = tokenizer.tokenizeToArrayList(string1).size();
		final float str2Tokens = tokenizer.tokenizeToArrayList(string2).size();
		return (((str1Tokens + str2Tokens) * str1Tokens) + ((str1Tokens + str2Tokens) * str2Tokens))
				* ESTIMATEDTIMINGCONST;
	}

	public float getSimilarity(final String string1, final String string2) {
		final ArrayList<String> str1Tokens = tokenizer
				.tokenizeToArrayList(string1);
		final ArrayList<String> str2Tokens = tokenizer
				.tokenizeToArrayList(string2);
		float totalPossible = (float) Math.sqrt((str1Tokens.size() * str1Tokens
				.size()) + (str2Tokens.size() * str2Tokens.size()));
		final float totalDistance = getEuclidianDistance(str1Tokens, str2Tokens);
		return (totalPossible - totalDistance) / totalPossible;
	}

	public float getUnNormalisedSimilarity(String string1, String string2) {
		final ArrayList<String> str1Tokens = tokenizer
				.tokenizeToArrayList(string1);
		final ArrayList<String> str2Tokens = tokenizer
				.tokenizeToArrayList(string2);

		return getEuclidianDistance(str1Tokens, str2Tokens);
	}

	private float getEuclidianDistance(final ArrayList<String> str1Tokens,
			final ArrayList<String> str2Tokens) {
		final Set<String> allTokens = new HashSet<String>();
		allTokens.addAll(str1Tokens);
		allTokens.addAll(str2Tokens);

		float totalDistance = 0.0f;
		for (final String token : allTokens) {
			int countInString1 = 0;
			int countInString2 = 0;
			for (final String sToken : str1Tokens) {
				if (sToken.equals(token)) {
					countInString1++;
				}
			}
			for (final String sToken : str2Tokens) {
				if (sToken.equals(token)) {
					countInString2++;
				}
			}

			totalDistance += ((countInString1 - countInString2) * (countInString1 - countInString2));
		}

		totalDistance = (float) sqrt(totalDistance);
		return totalDistance;
	}
}
