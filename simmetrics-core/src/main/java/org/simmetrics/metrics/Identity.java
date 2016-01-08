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
package org.simmetrics.metrics;

import static com.google.common.base.Preconditions.checkNotNull;

import org.simmetrics.Distance;
import org.simmetrics.Metric;

/**
 * Identity similarity and distance metric that returns a distance of 0.0 and
 * similarity 1.0 when the inputs are equals, and vice-versa when they're not.
 * <p>
 * <code>
 * similarity(a,b) = equals(a,b) ? 1.0f : 0.0f
 * <br>
 * distance(a,b) = equals(a,b) ? 0.0f : 1.0f
 * </code>
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @param <T>
 *            type of token
 */
public final class Identity<T> implements Metric<T>, Distance<T> {

	@Override
	public float compare(T a, T b) {
		checkNotNull(a);
		checkNotNull(b);
		return a.equals(b) ? 1.0f : 0.0f;
	}
	
	@Override
	public float distance(T a, T b) {
		checkNotNull(a);
		checkNotNull(b);
		return a.equals(b) ? 0.0f : 1.0f;
	}
	
	@Override
	public String toString() {
		return "Identity";
	}
}
