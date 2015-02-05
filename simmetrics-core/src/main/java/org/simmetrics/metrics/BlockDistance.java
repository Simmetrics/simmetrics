/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.ListMetric;
import org.simmetrics.tokenizers.WhitespaceTokenizer;


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
public class BlockDistance<T> implements ListMetric<T> {

	@Override
	public float compare(List<T> str1Tokens, List<T> str2Tokens) {

		final float totalPossible = str1Tokens.size() + str2Tokens.size();

		final float totalDistance = getInnerUnNormalizedSimilarity(
				str1Tokens,
				str2Tokens);
		return (totalPossible - totalDistance) / totalPossible;
	}

	private static <T> float getInnerUnNormalizedSimilarity(
			final List<T> str1Tokens, final List<T> str2Tokens) {
		final Set<Object> allTokens = new HashSet<>();
		allTokens.addAll(str1Tokens);
		allTokens.addAll(str2Tokens);

		int totalDistance = 0;
		for (Object token : allTokens) {
			int countInString1 = 0;
			int countInString2 = 0;
			for (Object sToken : str1Tokens) {
				if (sToken.equals(token)) {
					countInString1++;
				}
			}
			for (Object sToken : str2Tokens) {
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
