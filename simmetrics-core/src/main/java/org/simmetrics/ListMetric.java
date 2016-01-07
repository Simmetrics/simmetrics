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
import java.util.List;

/**
 * Measures the similarity between two arbitrary lists containing elements of
 * the same type. The measurement results in a value between 0 and 1 inclusive.
 * A value of zero indicates that the lists are dissimilar, a value of 1
 * indicates they are similar.
 * <p>
 * The elements in the lists have to implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
 * <p>
 * The elements in the lists may not be null if metric does not support null
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
 * Implementations my not modify the contents of the list. List should be
 * treated as if wrapped by {@link Collections#unmodifiableList(List)}.
 * 
 * 
 * @param <T>
 *            the type of elements contained in the lists
 *
 */
public interface ListMetric<T> extends Metric<List<T>> {
	/**
	 * Measures the similarity between lists a and b. The measurement results in
	 * a value between 0 and 1 inclusive. A value of zero indicates that the
	 * lists are dissimilar, a value of 1 indicates they are similar.
	 * 
	 * @param a
	 *            list a to compare
	 * @param b
	 *            list b to compare
	 * @return a value between 0 and 1 inclusive indicating similarity
	 * @throws NullPointerException
	 *             when either a or b is null
	 * @throws IllegalArgumentException
	 *             when either a or b contains null elements and this metric
	 *             does not permit lists containing null.
	 */
	@Override
	public float compare(List<T> a, List<T> b);

}
