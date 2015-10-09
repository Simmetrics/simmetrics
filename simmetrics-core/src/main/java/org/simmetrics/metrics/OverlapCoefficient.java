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

import static java.lang.Math.min;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

/**
 * Overlap Coefficient algorithm providing a similarity measure between two sets
 * where it is determined to what degree a set is a subset of another.
 * <p>
 * <code>overlap_coefficient(q,r) = (|q & r|) / min{|q|, |r|}</code>
 * <p>
 * Related to the Jaccard index.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @param <T>
 *            type of the token
 * 
 * @see JaccardSimilarity
 * @see <a href="http://en.wikipedia.org/wiki/Overlap_coefficient">Wikipedia -
 *      Overlap Coefficient</a>
 */
public final class OverlapCoefficient<T> implements SetMetric<T> {

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
		// (|q & r|) / min{|q|, |r|}
		return intersection / (float) min(a.size(), b.size());
	}

	@Override
	public String toString() {
		return "OverlapCoefficient";
	}

}
