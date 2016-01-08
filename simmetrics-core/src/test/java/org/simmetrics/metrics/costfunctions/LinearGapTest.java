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

import org.simmetrics.metrics.functions.Gap;
import org.simmetrics.metrics.functions.LinearGap;

@SuppressWarnings("javadoc")
public final class LinearGapTest extends GapCostTest {
	@Override
	protected Gap getCost() {
		return new LinearGap(-0.42f);
	}

	@Override
	protected T[] getTests() {
		final String testString = "hello world AAAAAAA BBB ABCDEF this is a test";
		return new T[] { new T(-2.1000f, testString, 0, 6),
				new T(-0.0000f, testString, 3, 4),
				new T(-1.2600f, testString, 13, 17),
				new T(-0.8400f, testString, 19, 22),
				new T(-2.1000f, testString, 23, 29),
				new T(-0.0000f, testString, 5, 6), };
	}

}
