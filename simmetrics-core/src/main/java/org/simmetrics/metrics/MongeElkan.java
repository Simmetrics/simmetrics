/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * #L%
 */

package org.simmetrics.metrics;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

import java.util.List;

import org.simmetrics.ListMetric;
import org.simmetrics.StringMetric;

/**
 * Calculates the normalized Monge-Elkan distance (similarity) over two strings.
 * The normalized Monge-Elkan distance is used because the the unnormalized
 * distance is not symmetric.
 * <p>
 * <code>
 * similarity(a,b) = sqrt(monge-elkan(a,b) * monge-elkan(b,a))
 * monge-elkan(a,b) = average( for s in a | max( for q in b | metric(s,q)) </code>
 * </code>
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * 
 */
public final class MongeElkan implements ListMetric<String> {

	private final StringMetric metric;

	/**
	 * Constructs a Monge-Elkan metric with metric.
	 * 
	 * @param metric
	 *            metric to use
	 */
	public MongeElkan(final StringMetric metric) {
		this.metric = metric;
	}

	@Override
	public float compare(List<String> a, List<String> b) {
		checkArgument(!a.contains(null), "a may not not contain null");
		checkArgument(!b.contains(null), "b may not not contain null");

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		// calculates normalized_similarity(a,b)
		return (float) sqrt(mongeElkan(a, b) * mongeElkan(b, a));
	}

	private float mongeElkan(List<String> a, List<String> b) {
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
