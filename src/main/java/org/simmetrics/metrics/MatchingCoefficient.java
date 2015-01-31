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

import java.util.List;

import org.simmetrics.TokenListMetric;

/**
 * Implements the Matching Coefficient algorithm providing a similarity measure
 * between two strings
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class MatchingCoefficient implements TokenListMetric {
	@Override
	public float compare(List<String> str1Tokens, List<String> str2Tokens) {

		final int totalPossible = Math
				.max(str1Tokens.size(), str2Tokens.size());
		return getInnerUnNormalisedSimilarity(str1Tokens, str2Tokens)
				/ totalPossible;
	}

	private static float getInnerUnNormalisedSimilarity(
			final List<String> str1Tokens, final List<String> str2Tokens) {
		int totalFound = 0;
		for (Object str1Token : str1Tokens) {
			final String sToken = (String) str1Token;
			boolean found = false;
			for (String str2Token : str2Tokens) {
				final String tToken = str2Token;
				if (sToken.equals(tToken)) {
					found = true;
				}
			}
			if (found) {
				totalFound++;
			}
		}
		return totalFound;
	}

	@Override
	public String toString() {
		return "MatchingCoefficient";
	}
	
	

}
