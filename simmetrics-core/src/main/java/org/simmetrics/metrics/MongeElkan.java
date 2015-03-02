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
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;

import java.util.List;

import org.simmetrics.StringMetric;
import org.simmetrics.ListMetric;

/**
 * Monge Elkan algorithm providing an matching style similarity measure between
 * two strings.
 * <p>
 * <code>similarity(a,b) = average( for s in a | max( for q in b | metric(s,q)) </code>
 * </p>
 * Implementation note: Because the matches of a in b are not symmetric with the
 * matches of b in a and because the whole operation is not symmetric when a and
 * b have a different length the asymmetry is normalized by *
 * <p>
 * This class is immutable and thread-safe.
 * <p>
 * <code>normalized_similarity(a,b) = sqrt(similarity(a,b) * similarity(b,a))</code>
 * </p>
 * 
 * 
 */
public class MongeElkan implements ListMetric<String> {

	private final StringMetric metric;

	/**
	 * Constructs a MongeElkan metric with metric.
	 * 
	 * @param metric
	 *            metric to use
	 */
	public MongeElkan(final StringMetric metric) {
		this.metric = metric;
	}

	@Override
	public float compare(List<String> a, List<String> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		// calculates normalized_similarity(a,b)
		return (float) sqrt(similarity(a, b) * similarity(b, a));
	}

	private float similarity(List<String> a, List<String> b) {
		// calculates average( for s in a | max( for q in b | metric(s,q))

		float sum = 0.0f;

		for (String s : a) {
			float max = 0.0f;
			for (String q : b) {
				max = max(max, metric.compare(s, q));
			}
			sum += max;
		}
		return sum / a.size();
	}

	@Override
	public String toString() {
		return "MongeElkan [metric=" + metric + "]";
	}

}
