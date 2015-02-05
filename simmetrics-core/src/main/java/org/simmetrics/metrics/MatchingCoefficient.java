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

import java.util.List;

import org.simmetrics.ListMetric;

/**
 * Implements the Matching Coefficient algorithm providing a similarity measure
 * between two strings
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class MatchingCoefficient<T> implements ListMetric<T> {
	@Override
	public float compare(List<T> str1Tokens, List<T> str2Tokens) {

		final int totalPossible = Math
				.max(str1Tokens.size(), str2Tokens.size());
		return getInnerUnNormalisedSimilarity(str1Tokens, str2Tokens)
				/ totalPossible;
	}

	private static <T> float getInnerUnNormalisedSimilarity(
			final List<T> str1Tokens, final List<T> str2Tokens) {
		int totalFound = 0;
		for (Object str1Token : str1Tokens) {
			boolean found = false;
			for (Object str2Token : str2Tokens) {
				if (str1Token.equals(str2Token)) {
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
