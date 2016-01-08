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

package org.simmetrics.metrics;

import org.simmetrics.ListMetric;
import org.simmetrics.ListMetricTest;
import org.simmetrics.StringMetric;

@SuppressWarnings("javadoc")
public final class MongeElkanTest extends ListMetricTest {

	@Override
	protected boolean supportsNullValues() {
		return false;
	}
	
	@Override
	protected ListMetric<String> getMetric() {
		return new MongeElkan(new StringMetric() {

			@Override
			public float compare(String a, String b) {
				return a.equals(b) ? 1.0f : 0.0f;
			}

			@Override
			public String toString() {
				return "Dummy";
			}
		});
	}

	@Override
	protected T[] getTests() {
		return new T[] {
				new T(0.5000f, "test string1", "test string2"),
				new T(0.7071f, "test", "test string2"),
				new T(0.0000f, "", "test string2"),
				new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.7500f, "a b c d", "a b c e"),
				new T(0.3333f, "Sam J Chapman", "Samuel John Chapman"),
				new T(0.5000f, "Sam Chapman", "S Chapman"),
				new T(0.4082f, "John Smith", "Samuel John Chapman"),
				new T(0.0000f, "John Smith", "Sam Chapman"),
				new T(0.0000f, "John Smith", "Sam J Chapman"),
				new T(0.0000f, "John Smith", "S Chapman"),
				new T(
						0.5096f,
						"The field matching problem: Algorithms and applications Alvaro E. Monge and Charles P. Elkan Department of Computer Science and Engineering University of California, San Diego La Jolla, California",
						"Alvaro E. Monge and Charles P. Elkan. Integrating external information sources to guide worldwide web information retrieval. Technical Report CS96-474, Department of Computer Science and Engineering, University of California, San Diego, January 1996")

		};
	}

}
