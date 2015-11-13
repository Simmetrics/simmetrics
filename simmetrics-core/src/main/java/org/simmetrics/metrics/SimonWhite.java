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

import static com.google.common.collect.Multisets.intersection;

import org.simmetrics.MultisetMetric;

import com.google.common.collect.Multiset;

/**
 * Simon White algorithm providing a similarity measure between two multisets.
 * Implementation based on the ideas as outlined in <a
 * href="http://www.catalysoft.com/articles/StrikeAMatch.html">How to Strike a
 * Match</a> by Simon White.
 * 
 * <p>
 * <code>
 * similarity(a,b) = 2 * |(a A b)|  / (|a| + |b|)
 * </code>
 * 
 * <p>
 * This metric is similar to Dice's coefficient however Simon White used
 * the multiset intersection rather then the set intersection to prevent
 * duplicates from scoring a perfect match against a list with single elements.
 * E.g. 'hello hello hello' should not be identical to 'hello'.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see DiceSimilarity
 * 
 * @param <T>
 *            type of the token
 * 
 */
public class SimonWhite<T> implements MultisetMetric<T> {

	@Override
	public float compare(Multiset<T> a, Multiset<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}
		
		// (2 * |a & b |) / (|a|  + |b|)
		return (2.0f * intersection(a, b).size()) / (a.size() + b.size());

	}

	@Override
	public String toString() {
		return "SimonWhite";
	}

}
