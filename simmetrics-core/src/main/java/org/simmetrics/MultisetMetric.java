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


package org.simmetrics;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

/**
 * Measures the similarity between two arbitrary multisets containing elements
 * of the same type. The measurement results in a value between 0 and 1
 * inclusive. A value of zero indicates that the sets are dissimilar, a value of
 * 1 indicates they are similar.
 * 
 * <p>
 * The elements in the multisets have to implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
 * <p>
 * The elements in the multisets may not be null if metric does not support null
 * values.
 * <p>
 * The similarity measure should be consistent with equals such that
 * {@code a.equals(b) => compare(a,b) == 1.0}.
 * <p>
 * The similarity measure should be reflexive such that
 * {@code compare(a,a) == 1.0}.
 * <p>
 * The similarity measure should be symmetric such that
 * {@code compare(a,b) == compare(b,a)}.
 * <p>
 * Implementations may not modify the contents of the multisets. Multisets should be
 * treated as if wrapped by {@link Multisets#unmodifiableMultiset(Multiset)}.
 * 
 * @param <T>
 *            the type of elements contained in the sets
 *
 */
public interface MultisetMetric<T> extends Metric<Multiset<T>> {
	/**
	 * Measures the similarity between multisets a and b. The measurement
	 * results in a value between 0 and 1 inclusive. A value of zero indicates
	 * that the sets are dissimilar, a value of 1 indicates they are similar.
	 * <p>
	 * Results are undefined if {@code a} and {@code b} are based on different
	 * equivalence relations (as {@code HashMultiset} and {@code TreeMultiset}
	 * are).
	 * 
	 * @param a
	 *            multisets a to compare
	 * @param b
	 *            multisets b to compare
	 * @return a value between 0 and 1 inclusive indicating similarity
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	@Override
	public float compare(Multiset<T> a, Multiset<T> b);

}
