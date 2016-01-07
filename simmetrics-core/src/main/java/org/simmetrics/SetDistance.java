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

import java.util.Collections;
import java.util.Set;

/**
 * Measures the unnormalized dissimilarity between two arbitrary sets containing
 * elements of the same type. The measurement results in a non-negative value. A
 * value of zero indicates that the objects are similar.
 * <p>
 * The elements in the sets have to implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
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
 * Implementations may not modify the contents of the sets. List should be
 * treated as if wrapped by {@link Collections#unmodifiableSet(Set)}.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Metric_(mathematics)">Wikipedia -
 *      Metric</a>
 * 
 * @param <E>
 *            the type of elements contained in the lists
 */
public interface SetDistance<E> extends Distance<Set<E>> {

	/**
	 * Measures the distance between sets a and b. The measurement results in a
	 * non-negative value. A value of {@code 0.0} indicates that {@code a} and
	 * {@code b} are similar.
	 * <p>
	 * Results are undefined if {@code a} and {@code b} are sets based on
	 * different equivalence relations (as {@code HashSet}, {@code TreeSet}, and
	 * the keySet of an {@code IdentityHashMap} all are).
	 * 
	 * @param a
	 *            set a to compare
	 * @param b
	 *            set b to compare
	 * @return a non-negative value
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	@Override
	public float distance(Set<E> a, Set<E> b);

}
