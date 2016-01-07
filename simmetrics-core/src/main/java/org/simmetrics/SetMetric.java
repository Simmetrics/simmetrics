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
 * Measures the similarity between two arbitrary sets containing elements of the
 * same type. The measurement results in a value between 0 and 1 inclusive. A
 * value of zero indicates that the sets are dissimilar, a value of 1 indicates
 * they are similar.
 * 
 * <p>
 * The elements in the set have to implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
 * <p>
 * The elements in the sets may not be null if metric does not support null
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
 * Implementations may not modify the contents of the set. Sets should be
 * treated as if wrapped by {@link Collections#unmodifiableSet(Set)}.
 * 
 * @param <T>
 *            the type of elements contained in the sets
 *
 */
public interface SetMetric<T> extends Metric<Set<T>> {
	/**
	 * Measures the similarity between sets a and b. The measurement results in
	 * a value between 0 and 1 inclusive. A value of zero indicates that the
	 * sets are dissimilar, a value of 1 indicates they are similar.
	 * <p>
	 * Results are undefined if {@code set1} and {@code set2} are sets based on
	 * different equivalence relations (as {@code HashSet}, {@code TreeSet}, and
	 * the keySet of an {@code IdentityHashMap} all are).
	 * 
	 * @param a
	 *            set a to compare
	 * @param b
	 *            set b to compare
	 * @return a value between 0 and 1 inclusive indicating similarity
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	@Override
	public float compare(Set<T> a, Set<T> b);

}
