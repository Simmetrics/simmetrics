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

import static java.lang.Math.sqrt;

/**
 * Implements the Euclidean Distance algorithm providing a similarity measure
 * between two strings using the vector space of combined terms as the
 * dimensions"
 *
 * @author Sam Chapman
 * @version 1.2
 */
public class EuclideanDistance<T> implements ListMetric<T> {

	@Override
	public float compare(List<T> str1Tokens, List<T> str2Tokens) {

		float totalPossible = (float) Math.sqrt((str1Tokens.size() * str1Tokens
				.size()) + (str2Tokens.size() * str2Tokens.size()));
		final float totalDistance = getEuclidianDistance(str1Tokens, str2Tokens);
		return (totalPossible - totalDistance) / totalPossible;
	}

	private static <T> float getEuclidianDistance(final List<T> str1Tokens,
			final List<T> str2Tokens) {
		final Set<Object> allTokens = new HashSet<>();
		allTokens.addAll(str1Tokens);
		allTokens.addAll(str2Tokens);

		float totalDistance = 0.0f;
		for (final Object token : allTokens) {
			int countInString1 = 0;
			int countInString2 = 0;
			for (final Object sToken : str1Tokens) {
				if (sToken.equals(token)) {
					countInString1++;
				}
			}
			for (final Object sToken : str2Tokens) {
				if (sToken.equals(token)) {
					countInString2++;
				}
			}

			totalDistance += ((countInString1 - countInString2) * (countInString1 - countInString2));
		}

		totalDistance = (float) sqrt(totalDistance);
		return totalDistance;
	}

	@Override
	public String toString() {
		return "EuclideanDistance";
	}

}
