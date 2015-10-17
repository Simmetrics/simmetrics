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

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

/**
 * Dice similarity algorithm providing a similarity measure between two sets
 * using the vector space of presented tokens.
 * <p>
 * <code>dices coefficient (a,b) = (2 * |a & b |) / (|a|  + |b|)</code>
 * <p>
 * This metric is identical to Simon White which operates on lists.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see SimonWhite
 * @see <a
 *      href="http://en.wikipedia.org/wiki/S%C3%B8rensen%E2%80%93Dice_coefficient">Wikipedia
 *      - Sørensen–Dice coefficient</a>
 * 
 * 
 * @param <T>
 *            type of the token
 */
public class DiceSimilarity<T> implements SetMetric<T> {

	@Override
	public float compare(Set<T> a, Set<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		final int total = a.size() + b.size();
		final Set<T> union = new HashSet<>(total);
		union.addAll(a);
		union.addAll(b);

		final int intersection = total - union.size();

		// (2 * |a & b |) / (|a|  + |b|)
		return (2.0f * intersection) / total;
	}

	@Override
	public String toString() {
		return "DiceSimilarity";
	}

}
