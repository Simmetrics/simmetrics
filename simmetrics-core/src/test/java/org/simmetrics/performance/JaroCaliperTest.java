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
package org.simmetrics.performance;

import org.junit.Test;
import org.simmetrics.performance.JaroCaliper.Method;
import org.simmetrics.performance.JaroCaliper.Value;

@SuppressWarnings("javadoc")
public class JaroCaliperTest {

	@Test
	public void smokeTest() {
		for (Value a : Value.values()) {
			for (Value b : Value.values()) {
				for (Method m : Method.values()) {
					JaroCaliper test = new JaroCaliper();
					test.a = a;
					test.b = b;
					test.method = m;
					test.compare(1);
				}
			}
		}
	}
}
