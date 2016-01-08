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

import static org.simmetrics.metrics.Math.intersection;
import static java.lang.Math.min;

import java.util.Set;

import org.simmetrics.SetDistance;
import org.simmetrics.SetMetric;

/**
 * The overlap coefficient measures the overlap between two sets. The similarity
 * is defined as the size of the intersection divided by the smaller of the size
 * of the two sets.
 * <p>
 * <code>
 * similarity(q,r) = ∣q ∩ r∣ / min{∣q∣, ∣r∣} <br>
 * distance(q,r) = 1 - similarity(q,r)
 * </code>
 * <p>
 * Unlike the generalized overlap coefficient the occurrence (cardinality) of an
 * entry is not taken into account. E.g. {@code [hello, world]} and
 * {@code [hello, world, hello, world]} would be identical when compared with
 * the overlap coefficient but are dissimilar when the generalized version is
 * used.
 * <p>
 * Similar to the generalized Jaccard similarity which divides the intersection
 * by the union of two multisets.
 * <p>
 * Similar to the dice coefficient which divides the shared information
 * (intersection) by sum of cardinalities.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @param <T>
 *            type of the token
 * 
 * @see GeneralizedOverlapCoefficient
 * @see Jaccard
 * @see Dice
 * @see <a href="http://en.wikipedia.org/wiki/Overlap_coefficient">Wikipedia -
 *      Overlap Coefficient</a>
 */
public final class OverlapCoefficient<T> implements SetMetric<T>, SetDistance<T> {

	@Override
	public float distance(Set<T> a, Set<T> b) {
		return 1 - compare(a, b);
	}
	
	@Override
	public float compare(Set<T> a, Set<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		// ∣q ∩ r∣ / min{∣q∣, ∣r∣}
		return intersection(a, b).size() / (float) min(a.size(), b.size());
	}

	@Override
	public String toString() {
		return "OverlapCoefficient";
	}

}
