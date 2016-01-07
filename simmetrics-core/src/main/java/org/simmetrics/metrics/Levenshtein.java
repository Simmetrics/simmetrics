/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
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

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static org.simmetrics.metrics.Math.min;

import org.simmetrics.StringDistance;
import org.simmetrics.StringMetric;

/**
 * Calculates the Levenshtein distance and similarity over two strings.
 * <p>
 * Insert/delete and substitute operations can be weighted. When the cost for
 * substitution is zero Levenshtein does not satisfy the coincidence property.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href=" http://en.wikipedia.org/wiki/Levenshtein_distance">Wikipedia -
 *      Levenshtein distance</a>
 * @see DamerauLevenshtein
 * 
 */
public final class Levenshtein implements StringMetric, StringDistance {

	private final float maxCost;
	private final float insertDelete;
	private final float substitute;

	/**
	 * Constructs a new weighted Levenshtein metric. When the cost for
	 * substitution is zero Levenshtein does not satisfy the coincidence
	 * property.
	 * 
	 * @param insertDelete
	 *            positive non-zero cost of an insert or deletion operation
	 * @param substitute
	 *            positive cost of a substitute operation
	 */
	public Levenshtein(float insertDelete, float substitute) {
		checkArgument(insertDelete > 0);
		checkArgument(substitute >= 0);
		this.maxCost = max(insertDelete, substitute);
		this.insertDelete = insertDelete;
		this.substitute = substitute;
	}

	/**
	 * Constructs a new Levenshtein metric.
	 */
	public Levenshtein() {
		this(1.0f, 1.0f);
	}

	@Override
	public float compare(final String a, final String b) {
		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		return 1.0f - (distance(a, b) / (maxCost * max(a.length(), b.length())));
	}

	@Override
	public float distance(final String s, final String t) {

		if (s.isEmpty())
			return t.length();
		if (t.isEmpty())
			return s.length();
		if (s.equals(t))
			return 0;

		final int tLength = t.length();
		final int sLength = s.length();

		float[] swap;
		float[] v0 = new float[tLength + 1];
		float[] v1 = new float[tLength + 1];

		// initialize v0 (the previous row of distances)
		// this row is A[0][i]: edit distance for an empty s
		// the distance is just the number of characters to delete from t
		for (int i = 0; i < v0.length; i++) {
			v0[i] = i * insertDelete;
		}

		for (int i = 0; i < sLength; i++) {

			// first element of v1 is A[i+1][0]
			// edit distance is delete (i+1) chars from s to match empty t
			v1[0] = (i + 1) * insertDelete;

			for (int j = 0; j < tLength; j++) {
				v1[j + 1] = min(v1[j] + insertDelete,
						v0[j + 1] + insertDelete,
						v0[j]
								+ (s.charAt(i) == t.charAt(j) ? 0.0f
										: substitute));
			}

			swap = v0;
			v0 = v1;
			v1 = swap;
		}

		// latest results was in v1 which was swapped with v0
		return v0[tLength];
	}

	@Override
	public String toString() {
		return "Levenshtein [insertDelete=" + insertDelete + ", substitute="
				+ substitute + "]";
	}

}
