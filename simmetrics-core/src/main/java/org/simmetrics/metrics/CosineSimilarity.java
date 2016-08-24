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
import static org.simmetrics.metrics.Math.union;
import static java.lang.Math.sqrt;

import org.simmetrics.MultisetDistance;
import org.simmetrics.MultisetMetric;

import com.google.common.collect.Multiset;

/**
 * Calculates the cosine similarity over two multisets. The similarity is
 * defined as the cosine of the angle between the multisets expressed as sparse
 * vectors.
 * <p>
 * <code>
 * similarity(a,b) = a·b / (||a|| * ||b||)
 * <br>
 * distance(a,b) = 1 - similarity(a,b)
 * </code>
 * 
 * <p>
 * The cosine similarity is identical to the Tanimoto coefficient, but unlike
 * Tanimoto the occurrence (cardinality) of an entry is taken into account. E.g.
 * {@code [hello, world]} and {@code [hello, world, hello, world]} would be
 * identical when compared with Tanimoto but are dissimilar when the cosine
 * similarity is used.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see TanimotoCoefficient
 * @see <a href="http://en.wikipedia.org/wiki/Cosine_similarity">Wikipedia
 *      Cosine similarity</a>
 * 
 * @param <T>
 *            type of the token
 */
public final class CosineSimilarity<T> implements MultisetMetric<T>, MultisetDistance<T> {

	@Override
	public float compare(Multiset<T> a, Multiset<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		float dotProduct = 0;
		float magnitudeA = 0;
		float magnitudeB = 0;

		for (T entry : union(a, b).elementSet()) {
			float aCount = a.count(entry);
			float bCount = b.count(entry);

			dotProduct += aCount * bCount;
			magnitudeA += aCount * aCount;
			magnitudeB += bCount * bCount;
		}

		//  a·b / (||a|| * ||b||)
		return (float) (dotProduct / (sqrt(magnitudeA) * sqrt(magnitudeB)));
	}
	@Override
	public float distance(Multiset<T> a, Multiset<T> b) {
		return 1.0f - compare(a, b);
	}
	
	@Override
	public String toString() {
		return "CosineSimilarity";
	}



}
