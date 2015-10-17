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
package org.simmetrics.metrics;

import static com.google.common.base.Preconditions.checkNotNull;

import org.simmetrics.Metric;

/**
 * Identity metric that returns 1.0 when the inputs are equals, and 0.0 when
 * they're not.
 * 
 * @param <T>
 *            type of token
 */
public final class Identity<T> implements Metric<T> {

	@Override
	public float compare(T a, T b) {
		checkNotNull(a);
		checkNotNull(b);
		return a.equals(b) ? 1.0f : 0.0f;
	}

	@Override
	public String toString() {
		return "Identity";
	}

}
