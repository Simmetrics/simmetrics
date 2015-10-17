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

import static java.lang.Math.abs;

/**
 * Block distance algorithm whereby vector space block distance between tokens
 * is used to determine a similarity. Also known as L1 Distance or City block
 * distance.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Taxicab_geometry">Wikipedia -
 *      Taxicab geometry</a>
 * @param <T>
 *            type of token
 */
public class BlockDistance<T> implements ListMetric<T>, ListDistance<T> {

	@Override
	public float compare(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		return 1.0f - distance(a, b) / (a.size() + b.size());
	}

	@Override
	public float distance(final List<T> a, final List<T> b) {
		final Set<T> all = new HashSet<>(a.size() + b.size());
		all.addAll(a);
		all.addAll(b);

		int totalDistance = 0;
		for (T token : all) {
			int frequencyInA = frequency(a, token);
			int frequencyInB = frequency(b, token);

			totalDistance += abs(frequencyInA - frequencyInB);
		}
		return totalDistance;
	}

	@Override
	public String toString() {
		return "BlockDistance";
	}

}
