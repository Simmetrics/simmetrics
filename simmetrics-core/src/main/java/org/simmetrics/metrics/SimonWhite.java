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

import org.simmetrics.MultisetDistance;
import org.simmetrics.MultisetMetric;

import com.google.common.collect.Multiset;

/**
 * Calculates the Dice similarity coefficient and distance over two multisets.
 * Also known as quantitative version of the Dice similarity coefficient. The
 * similarity is defined as twice the shared information (intersection) divided
 * by the sum of cardinalities.
 * <p>
 * <code>
 * similarity(a,b) = 2 * ∣a ∩ b∣ / (∣a∣  + ∣b∣)
 * <br>
 * distance(a,b) = 1 - similarity(a,b)
 * </code>
 * <p>
 * 
 * Implementation based on the ideas as outlined in <a
 * href="http://www.catalysoft.com/articles/StrikeAMatch.html">How to Strike a
 * Match</a> by <cite>Simon White</cite>. To create the described metric use:
 * <p>
 * <code><pre>{@code
 * import static org.simmetrics.StringMetricBuilder.with;
 *  
 *  ...
 *  
 * with(new SimonWhite<String>())
 *   .tokenize(Tokenizers.qGram(2))
 *   .build();
 * }
 * </pre>
 * </code>
 * <p>
 * The Dice similarity coefficient is identical to Simon White, but unlike Simon
 * White the occurrence (cardinality) of an entry is not taken into account.
 * E.g. {@code [hello, world]} and {@code [hello, world, hello, world]} would be
 * identical when compared with Dice but are dissimilar when Simon White is
 * used.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see Dice
 * @see <a
 *      href="http://en.wikipedia.org/wiki/S%C3%B8rensen%E2%80%93Dice_coefficient"
 *      >Wikipedia - Sørensen–Dice coefficient</a>
 * 
 * @param <T>
 *            type of the token
 * 
 */
public final class SimonWhite<T> implements MultisetMetric<T>, MultisetDistance<T> {

	@Override
	public float compare(Multiset<T> a, Multiset<T> b) {

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
	public float distance(Multiset<T> a, Multiset<T> b) {
		return 1.0f - compare(a, b);
	}
	@Override
	public String toString() {
		return "SimonWhite";
	}

	

}
