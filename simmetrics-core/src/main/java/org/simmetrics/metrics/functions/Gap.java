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
 * A gap function assigns penalty to the creation of a gap in a string when
 * matching against another string.
 * <p>
 * The penalty returned must be non-positive value to be consistent with the
 * results of the substitution function.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * 
 * @see Substitution
 * @see <a href="https://en.wikipedia.org/wiki/Gap_penalty">Wikipedia - Gap
 *      Penalty</a>
 *
 */
public interface Gap {

	/**
	 * Returns the penalty for creating a gap from <code>fromIndex</code> to
	 * <code>toIndex -1</code>. The value must be non-positive.
	 * 
	 * @param fromIndex
	 *            index at which the gap starts
	 * @param toIndex
	 *            index after the last gap entry
	 * @return a gap penalty
	 */
	public float value(int fromIndex, int toIndex);

	/**
	 * Returns the minimum value a gap can have.
	 * 
	 * @return the minimum value a gap can have
	 */
	public float max();

	/**
	 * Returns the maximum value a gap can have.
	 * 
	 * @return the maximum value a gap can have
	 */
	public float min();
}
