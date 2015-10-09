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

import static java.util.Arrays.asList;

import java.util.List;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.Distance;
import org.simmetrics.ListDistanceTest;
import org.simmetrics.ListMetric;
import org.simmetrics.ListMetricTest;
import org.simmetrics.metrics.EuclideanDistance;

@SuppressWarnings("javadoc")
@RunWith(Enclosed.class)
public final class EuclideanDistanceTest {
	public final static class DistanceList extends ListDistanceTest {

		@Override
		protected Distance<List<String>> getMetric() {
			return new EuclideanDistance<>();
		}

		@Override
		protected T[] getListTests() {
			return new T[] {

					new T(0.0000f, asList("test", "string1"), asList("test",
							"string1")),
					new T(1.4142f, asList("test", "string1"), asList("test",
							"string2")),
					new T(1.0000f, asList("test"), asList("test", "string2")),
					new T(1.4142f, getEmpty(), asList("test", "string2")),
					new T(1.4142f, asList("test", null), asList("test",
							"string2")),
					new T(1.4142f, asList("aaa", "bbb", "ccc", "ddd"), asList(
							"aaa", "bbb", "ccc", "eee")),
					new T(1.4142f, asList("aaa", "bbb"), asList("aaa", "aaa")),
					new T(1.0000f, asList("aaa"), asList("aaa", "aaa")),
					new T(1.4142f, asList("a", "b", "c", "d"), asList("a", "b",
							"c", "e")),
					new T(2.0000f, asList("a", "b", "c", "d"), asList("a", "b",
							"e", "f")) };
		}
	}

	public static final class MetricListTest extends ListMetricTest {

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

		@Override
		protected ListMetric<String> getMetric() {
			return new EuclideanDistance<>();
		}

		@Override
		protected T[] getListTests() {
			return new T[] {
					new T(0.5000f, asList("test", null), asList("test",
							"string2")),
					new T(0.5000f, "test string1", "test string2"),
					new T(0.5527f, "test", "test string2"),
					new T(0.2928f, "", "test string2"),
					new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.7500f, "a b c d", "a b c e"),
					new T(0.0000f, "Healed", "Sealed"),
					new T(0.0000f, "Healed", "Healthy"),
					new T(0.0000f, "Healed", "Heard"),
					new T(0.0000f, "Healed", "Herded"),
					new T(0.0000f, "Healed", "Help"),
					new T(0.0000f, "Healed", "Sold"),
					new T(0.0000f, "Healed", "Help"),
					new T(0.5286f, "Sam J Chapman", "Samuel John Chapman"),
					new T(0.5000f, "Sam Chapman", "S Chapman"),
					new T(0.5196f, "John Smith", "Samuel John Chapman"),
					new T(0.2929f, "John Smith", "Sam Chapman"),
					new T(0.3798f, "John Smith", "Sam J Chapman"),
					new T(0.2929f, "John Smith", "S Chapman"),
					new T(0.7374f, "Web Database Applications",
							"Web Database Applications with PHP & MySQL"),
					new T(0.7383f, "Web Database Applications",
							"Creating Database Web Applications with PHP and ASP"),
					new T(0.7383f, "Web Database Applications",
							"Building Database Applications on the Web Using PHP3"),
					new T(0.7383f, "Web Database Applications",
							"Building Web Database Applications with Visual Studio 6"),
					new T(0.5799f, "Web Database Applications",
							"Web Application Development With PHP"),
					new T(
							0.7630f,
							"Web Database Applications",
							"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
					new T(0.6349f, "Web Database Applications",
							"Structural Assessment: The Role of Large and Full-Scale Testing"),
					new T(0.5528f, "Web Database Applications",
							"How to Find a Scholarship Online"),
					new T(0.6366f, "Web Aplications",
							"Web Database Applications with PHP & MySQL"),
					new T(0.6570f, "Web Aplications",
							"Creating Database Web Applications with PHP and ASP"),
					new T(0.6570f, "Web Aplications",
							"Building Database Applications on the Web Using PHP3"),
					new T(0.6570f, "Web Aplications",
							"Building Web Database Applications with Visual Studio 6"),
					new T(0.5848f, "Web Aplications",
							"Web Application Development With PHP"),
					new T(
							0.7259f,
							"Web Aplications",
							"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
					new T(0.6403f, "Web Aplications",
							"Structural Assessment: The Role of Large and Full-Scale Testing"),
					new T(0.5528f, "Web Aplications",
							"How to Find a Scholarship Online") };
		}
	}

}
