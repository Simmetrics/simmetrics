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

import org.simmetrics.StringMetricBuilder;
import org.simmetrics.StringMetric;
import org.simmetrics.ListMetric;
import org.simmetrics.simplifiers.SoundexSimplifier;

/**
 * ChapmanOrderedNameCompoundSimilarity tests similarity upon the most similar
 * in terms of token based names where the later names are valued higher than
 * earlier names. Surnames are less flexible.
 *
 * This is intended to provide a better rating for lists of proper names.
 *
 * @author Sam Chapman
 * 
 */
public class ChapmanOrderedNameCompoundSimilarity implements ListMetric<String> {

	private final StringMetric metric1 = new StringMetricBuilder().with(
			new JaroWinkler())
			.simplify(new SoundexSimplifier())
			.build();

	private final StringMetric metric2 = new SmithWaterman();

	@Override
	public float compare(List<String> a, List<String> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		} else if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		int minTokens = Math.min(a.size(), b.size());

		float SKEW_AMMOUNT = 1.0f;

		float sumMatches = 0.0f;
		for (int i = 1; i <= minTokens; i++) {
			float strWeightingAdjustment = ((1.0f / minTokens) + (((((minTokens - i) + 0.5f) - (minTokens / 2.0f)) / minTokens)
					* SKEW_AMMOUNT * (1.0f / minTokens)));
			final String sToken = a.get(a.size() - i);
			final String tToken = b.get(b.size() - i);

			final float found1 = metric1.compare(sToken, tToken);
			final float found2 = metric2.compare(sToken, tToken);
			sumMatches += ((0.5f * (found1 + found2)) * strWeightingAdjustment);
		}
		return sumMatches;
	}

	@Override
	public String toString() {
		return "ChapmanOrderedNameCompoundSimilarity [metric1=" + metric1
				+ ", metric2=" + metric2 + "]";
	}

}
