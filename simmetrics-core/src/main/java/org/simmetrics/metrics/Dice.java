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

import java.util.Set;

import org.simmetrics.SetDistance;
import org.simmetrics.SetMetric;

/**
 * Calculates the Dice similarity coefficient and distance over two sets. The
 * similarity is defined as twice the shared information (intersection) divided
 * by sum of cardinalities.
 * <p>
 * <code>
 * similarity(a,b) = 2 * ∣a ∩ b∣ / (∣a∣  + ∣b∣)
 * <br>
 * distance(a,b) = 1 - similarity(a,b)
 * </code>
 * <p>
 * The Dice similarity coefficient is identical to SimonWhite, but unlike Simon
 * White the occurrence (cardinality) of an entry is not taken into account.
 * E.g. {@code [hello, world]} and {@code [hello, world, hello, world]} would be
 * identical when compared with Dice but are dissimilar when Simon White is
 * used.
 * <p>
 * Similar to the overlap coefficient which divides the intersection by the size
 * of the smaller of the two sets.
 * <p>
 * Similar to the generalized Jaccard similarity which divides the intersection
 * by the union of two multisets.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see SimonWhite
 * @see OverlapCoefficient
 * @see Jaccard
 * @see <a
 *      href="http://en.wikipedia.org/wiki/S%C3%B8rensen%E2%80%93Dice_coefficient">Wikipedia
 *      - Sørensen–Dice coefficient</a>
 * 
 * 
 * @param <T>
 *            type of the token
 */
public final class Dice<T> implements SetMetric<T>, SetDistance<T> {

	@Override
	public float compare(Set<T> a, Set<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		// 2 * ∣a ∩ b∣ / (∣a∣ + ∣b∣)
		return (2.0f * intersection(a, b).size()) / (a.size() + b.size());
	}

	@Override
	public float distance(Set<T> a, Set<T> b) {
		return 1.0f - compare(a, b);
	}

	@Override
	public String toString() {
		return "Dice";
	}

}
