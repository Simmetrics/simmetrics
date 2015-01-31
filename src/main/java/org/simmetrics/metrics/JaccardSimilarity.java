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
import java.util.Set;

import org.simmetrics.TokenSetMetric;

/**
 * Implements the Jaccard Similarity algorithm providing a similarity measure
 * between two strings
 * 
 * Each instance is represented as a Jaccard vector similarity function. The
 * Jaccard between two vectors X and Y is (X*Y) / (|X||Y|-(X*Y))
 * 
 * where (X*Y) is the inner product of X and Y, and |X| = (X*X)^1/2, i.e. the
 * Euclidean norm of X.
 * 
 * This can more easily be described as ( |X & Y| ) / ( | X or Y | )
 * 
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class JaccardSimilarity implements TokenSetMetric {

	@Override
	public float compare(Set<String> str1Tokens, Set<String> str2Tokens) {

		final Set<String> allTokens = new HashSet<>();
		allTokens.addAll(str1Tokens);
		allTokens.addAll(str2Tokens);

		final int commonTerms = (str1Tokens.size() + str2Tokens.size())
				- allTokens.size();

		// return JaccardSimilarity
		return (float) (commonTerms) / (float) (allTokens.size());
	}

	@Override
	public String toString() {
		return "JaccardSimilarity";
	}

}
