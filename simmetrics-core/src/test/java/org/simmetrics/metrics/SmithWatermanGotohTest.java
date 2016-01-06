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

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricTest;

@SuppressWarnings("javadoc")
public final class SmithWatermanGotohTest extends StringMetricTest {
	
	@Override
	protected boolean satisfiesSubadditivity() {
		return false;
	}
	
	@Override
	protected boolean satisfiesCoincidence() {
		return false;
	}

	@Override
	protected StringMetric getMetric() {
		return new SmithWatermanGotoh();
	}

	
	@Override
	protected T[] getTests()  {
		return new T[] {
				new T(0.9167f, "test string1", "test string2"),
				new T(1.0000f, "test", "test string2"),
				new T(0.0000f, "", "test string2"),
				new T(0.8000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.8571f, "a b c d", "a b c e"),
				new T(0.8333f, "Healed", "Sealed"),
				new T(0.6667f, "Healed", "Healthy"),
				new T(0.6000f, "Healed", "Heard"),
				new T(0.3333f, "Healed", "Herded"),
				new T(0.6250f, "Healed", "Help"),
				new T(0.3750f, "Healed", "Sold"),
				new T(0.6250f, "Healed", "Help"),
				new T(0.7692f, "Sam J Chapman", "Samuel John Chapman"),
				new T(0.8889f, "Sam Chapman", "S Chapman"),
				new T(0.5000f, "John Smith", "Samuel John Chapman"),
				new T(0.1500f, "John Smith", "Sam Chapman"),
				new T(0.1500f, "John Smith", "Sam J Chapman"),
				new T(0.1111f, "John Smith", "S Chapman"),
				new T(1.0000f, "Web Database Applications",
						"Web Database Applications with PHP & MySQL"),
				new T(0.8000f, "Web Database Applications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(0.8800f, "Web Database Applications",
						"Building Database Applications on the Web Using PHP3"),
				new T(1.0000f, "Web Database Applications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(0.5000f, "Web Database Applications",
						"Web Application Development With PHP"),
				new T(
						0.8800f,
						"Web Database Applications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.1000f, "Web Database Applications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.0800f, "Web Database Applications",
						"How to Find a Scholarship Online"),
				new T(0.8000f, "Web Aplications",
						"Web Database Applications with PHP & MySQL"),
				new T(0.9667f, "Web Aplications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(0.8000f, "Web Aplications",
						"Building Database Applications on the Web Using PHP3"),
				new T(0.8000f, "Web Aplications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(0.9000f, "Web Aplications",
						"Web Application Development With PHP"),
				new T(
						0.8000f,
						"Web Aplications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.1667f, "Web Aplications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.1333f, "Web Aplications",
						"How to Find a Scholarship Online"),

		};
	}
}
