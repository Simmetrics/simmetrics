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


package org.simmetrics.metrics.functions;

/**
 * A substitution function assigns value to the substitution of one character
 * for another matching against another string.
 * <p>
 * A positive value indicates the characters are similar, a negative value
 * indicates they are dissimilar.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see Gap
 * @see <a
 *      href="https://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm">Wikipedia
 *      - Smith–Waterman algorithm</a>
 *
 */
public interface Substitution {
	/**
	 * Rates the substitution of character <code>a.getChar(aIndex)</code>
	 * against <code>b.getChar(bIndex)</code>.
	 * 
	 * @param a
	 *            a string to check in
	 * @param aIndex
	 *            index of character at string <code>a</code> to compare
	 * @param b
	 *            another string to check in
	 * @param bIndex
	 *            index of character at string <code>b</code> to compare
	 * @return a score indicating the characters (dis) similarity
	 */
	public float compare(String a, int aIndex, String b, int bIndex);

	/**
	 * Returns the maximum value a gap can have
	 * 
	 * @return the maximum value a gap can have
	 */
	public float max();

	/**
	 * Returns the minimum value a gap can have
	 * 
	 * @return the minimum value a gap can have
	 */
	public float min();
}
