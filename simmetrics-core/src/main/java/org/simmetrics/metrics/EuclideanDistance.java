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

import static java.util.Collections.frequency;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.ListDistance;
import org.simmetrics.ListMetric;

import static java.lang.Math.sqrt;

/**
 * Euclidean Distance algorithm providing a similarity measure between two lists
 * using the vector space of combined terms as the dimensions.
 *
 * <p>
 * This class is immutable and thread-safe.
 * @param <T>
 *            type of the token
 * 
 */
public class EuclideanDistance<T> implements ListMetric<T>, ListDistance<T> {

	@Override
	public float compare(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		float maxDistance = (float) sqrt((a.size() * a.size()) + (b.size() * b.size()));
		return (maxDistance - distance(a, b)) / maxDistance;
	}

	@Override
	public float distance(final List<T> a, final List<T> b) {
		final Set<T> all = new HashSet<>(a.size() + b.size());
		all.addAll(a);
		all.addAll(b);

		float distance = 0.0f;
		for (final T token : all) {
			int frequencyInA = frequency(a, token);
			int frequencyInB = frequency(b, token);

			distance += ((frequencyInA - frequencyInB) * (frequencyInA - frequencyInB));
		}

		return (float) sqrt(distance);
	}

	@Override
	public String toString() {
		return "EuclideanDistance";
	}

}
