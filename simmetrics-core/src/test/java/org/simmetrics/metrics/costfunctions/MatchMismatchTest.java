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

package org.simmetrics.metrics.costfunctions;

import org.simmetrics.metrics.functions.MatchMismatch;
import org.simmetrics.metrics.functions.Substitution;

@SuppressWarnings("javadoc")
public class MatchMismatchTest extends SubstitutionTest {
	
	@Override
	protected Substitution getCost() {
		return new MatchMismatch(1.0f, -0.25f);
	}

	@Override
	protected T[] getTests() {
		return new T[]{
				new T(1.000f, "a", 0, "a", 0),
				new T(-0.25f, "a", 0, "b", 0),
				new T(1.000f, "ab", 0, "ba", 1),
				new T(-0.25f, "ab", 1, "ba", 1),
				
		};
	}

}
