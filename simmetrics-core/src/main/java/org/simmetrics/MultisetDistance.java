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
package org.simmetrics;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

/**
 * Measures the unnormalized dissimilarity between two arbitrary multisets
 * containing elements of the same type. The measurement results in a value
 * between 0 and 1 inclusive. A value of zero indicates that the lists are
 * dissimilar, a value of 1 indicates they are similar.
 * <p>
 * The elements in the multisets have to implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
 * <p>
 * The elements in the multisets may not be null if metric does not support null
 * values.
 * <p>
 * The distance measure should be consistent with equals such that
 * {@code a.equals(b) => distance(a,b) == 0.0}.
 * <p>
 * The distance measure should be reflexive such that
 * {@code distance(a,a) == 0.0}.
 * <p>
 * The distance measure should be symmetric such that
 * {@code distance(a,b) == distance(b,a)}.
 * <p>
 * Implementations may not modify the contents of the multisets. Multisets
 * should be treated as if wrapped by
 * {@link Multisets#unmodifiableMultiset(Multiset)}.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Metric_(mathematics)">Wikipedia -
 *      Metric</a>
 * 
 * @param <E>
 *            the type of elements contained in the lists
 */
public interface MultisetDistance<E> extends Distance<Multiset<E>> {

	/**
	 * Measures the distance between multisets a and b. The measurement results
	 * in a non-negative value. A value of {@code 0.0} indicates that {@code a}
	 * and {@code b} are similar.
	 * <p>
	 * Results are undefined if {@code a} and {@code b} are based on different
	 * equivalence relations (as {@code HashMultiset} and {@code TreeMultiset}
	 * are).
	 * 
	 * @param a
	 *            list a to compare
	 * @param b
	 *            list b to compare
	 * @return a non-negative value
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	@Override
	public float distance(Multiset<E> a, Multiset<E> b);

}
