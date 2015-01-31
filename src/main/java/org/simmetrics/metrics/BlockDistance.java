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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.TokenListMetric;
import org.simmetrics.tokenisers.WhitespaceTokenizer;

import static java.lang.Math.abs;

/**
 * Implements the Block distance algorithm whereby vector space block distance
 * between tokens is used to determine a similarity.
 * 
 * Uses the {@link WhitespaceTokenizer} by default.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class BlockDistance implements TokenListMetric {

	@Override
	public float compare(List<String> str1Tokens, List<String> str2Tokens) {

		final float totalPossible = str1Tokens.size() + str2Tokens.size();

		final float totalDistance = getInnerUnNormalizedSimilarity(str1Tokens,
				str2Tokens);
		return (totalPossible - totalDistance) / totalPossible;
	}

	private static float getInnerUnNormalizedSimilarity(
			final List<String> str1Tokens, final List<String> str2Tokens) {
		final Set<String> allTokens = new HashSet<>();
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

	@Override
	public String toString() {
		return "BlockDistance";
	}

}
