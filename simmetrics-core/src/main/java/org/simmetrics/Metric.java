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

/**
 * Measures the similarity between two arbitrary objects of the same type . The
 * measurement results in a value between 0 and 1 inclusive. A value of zero
 * indicates that the sets are dissimilar, a value of 1 indicates they are
 * similar.
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
 * The similarity measure may be transitive such that
 * {@code 1.0 - compare(a, c) ≤ 1.0 - compare(a, b) + 1.0 - compare(b, c)}
 * <p>
 * The similarity measure may satisfy the coincidence axiom such that
 * {@code compare(a, b) = 0 if and only if a.equals(b)}
 * <p>
 * Implementations may not modify the arguments. Arguments should be treated as
 * if they were unmodifiable.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Metric_(mathematics)">Wikipedia -
 *      Metric</a>
 * 
 * @param <T>
 *            type of the elements compared
 * 
 */

public interface Metric<T> {
	/**
	 * Measures the similarity between a and b. The measurement results in a
	 * value between 0 and 1 inclusive. A value of {@code 0.0} indicates that
	 * {@code a} and {@code b} are dissimilar, a value of {@code 1.0} indicates
	 * they are similar.
	 * 
	 * @param a
	 *            object a to compare
	 * @param b
	 *            object b to compare
	 * @return a value between 0 and 1 inclusive indicating similarity
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	public float compare(T a, T b);

}
