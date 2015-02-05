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

import java.util.*;

import org.simmetrics.ListMetric;

/**
 * Implements the Q Grams Distance algorithm providing a similarity measure
 * between two strings using the qGram approach check matching qGrams/possible
 * matching qGrams
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public  class QGramsDistance implements ListMetric<Object> {


	@Override
	public float compare(List<Object> str1Tokens, List<Object> str2Tokens) {


		final int maxQGramsMatching = str1Tokens.size() + str2Tokens.size();

		// return
		if (maxQGramsMatching == 0) {
			return 0.0f;
		} else {
			return (maxQGramsMatching - getInnerUnNormalizedSimilarity(
					str1Tokens, str2Tokens)) / maxQGramsMatching;
		}
	}

	

	private static float getInnerUnNormalizedSimilarity(
			final List<Object> str1Tokens,
			final List<Object> str2Tokens) {
		final Set<Object> allTokens = new HashSet<>();
		allTokens.addAll(str1Tokens);
		allTokens.addAll(str2Tokens);

		final Iterator<Object> allTokensIt = allTokens.iterator();
		int difference = 0;
		while (allTokensIt.hasNext()) {
			final Object token = allTokensIt.next();
			int matchingQGrams1 = 0;
			for (Object str1Token : str1Tokens) {
				if (str1Token.equals(token)) {
					matchingQGrams1++;
				}
			}
			int matchingQGrams2 = 0;
			for (Object str2Token : str2Tokens) {
				if (str2Token.equals(token)) {
					matchingQGrams2++;
				}
			}
			if (matchingQGrams1 > matchingQGrams2) {
				difference += (matchingQGrams1 - matchingQGrams2);
			} else {
				difference += (matchingQGrams2 - matchingQGrams1);
			}
		}

		return difference;
	}



	@Override
	public String toString() {
		return "QGramsDistance";
	}



	
}
