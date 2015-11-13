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

import static com.google.common.collect.Sets.intersection;

import java.util.Set;

import org.simmetrics.SetMetric;

/**
 * Jaccard similarity algorithm providing a similarity measure between two sets
 * using the vector space of presented tokens.
 * 
 * <p>
 * <code>jaccard index(a,b) = ( |a & b| ) / ( | a or b | )</code>
 * <p>
 * 
 * This metric is identical to the matching coefficient which operates on lists.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Jaccard_index">Wikipedia - Jaccard
 *      index</a>
 * 
 * 
 * @param <T>
 *            type of the token
 * 
 */
public final class JaccardSimilarity<T> implements SetMetric<T> {

	@Override
	public float compare(Set<T> a, Set<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}
		
		final int intersection = intersection(a, b).size();
				
		// ( |a & b| ) / ( | a or b | )
		// Implementation note: The size of the union of two sets is equal to
		// the size of both lists minus the duplicate elements.
		return intersection / (float)(a.size() + b.size() - intersection);
	}

	@Override
	public String toString() {
		return "JaccardSimilarity";
	}

}
