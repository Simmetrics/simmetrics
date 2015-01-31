/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. SimMetrics is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * SimMetrics is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.TokenSetMetric;

/**
 * Implements the Overlap Coefficient algorithm providing a similarity measure
 * between two string where it is determined to what degree a string is a subset
 * of another.
 * 
 * overlap_coefficient(q,r) = (|q & r|) / min{|q|, |r|}.
 * 
 * @author Sam Chapman * @version 1.1
 */
public final class OverlapCoefficient implements TokenSetMetric {

	@Override
	public float compare(Set<String> str1Tokens, Set<String> str2Tokens) {

		final Set<String> allTokens = new HashSet<>();
		allTokens.addAll(str1Tokens);
		allTokens.addAll(str2Tokens);

		// overlap_coefficient(q,r) = ( | q & r | ) / min{ | q | , | r | }.
		final int commonTerms = (str1Tokens.size() + str2Tokens.size())
				- allTokens.size();
		return (float) (commonTerms)
				/ (float) Math.min(str1Tokens.size(), str2Tokens.size());
	}

	@Override
	public String toString() {
		return "OverlapCoefficient";
	}
	
	

}
