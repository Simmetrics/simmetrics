/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.simmetrics.metrics;

import static com.google.common.base.Preconditions.checkArgument;
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
 * b have a different length the asymmetry is normalized by:
 * <p>
 * <code>normalized_similarity(a,b) = sqrt(similarity(a,b) * similarity(b,a))</code>
 * </p>
 * <p>
 * This class is immutable and thread-safe.
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
		checkArgument(!a.contains(null),"a may not not contain null");
		checkArgument(!b.contains(null),"b may not not contain null");
		
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
