/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
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

package org.simmetrics.utils;

import org.simmetrics.simplifiers.Simplifier;

/**
 * Interface for classes that delegate to a {@link Simplifier}.
 * 
 * 
 *
 */
@Deprecated
public interface Simplifying {

	/**
	 * Sets the simplifier. May not be null.
	 * 
	 * @param simplifier
	 *            a simplifier to set
	 */
	public void setSimplifier(Simplifier simplifier);

	/**
	 * Gets the simplifier. When null no simplifier was set.
	 * 
	 * @return the simplifier or null when not set
	 */
	public Simplifier getSimplifier();

}
