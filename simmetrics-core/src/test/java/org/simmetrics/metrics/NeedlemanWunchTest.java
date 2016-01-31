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

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricTest;
import org.simmetrics.metrics.functions.MatchMismatch;

@SuppressWarnings("javadoc")
@RunWith(Enclosed.class)
public class NeedlemanWunchTest {

	public static final class Default extends StringMetricTest {

		@Override
		protected StringMetric getMetric() {
			return new NeedlemanWunch();
		}

		
		@Override
		protected T[] getTests()  {
			return new T[] {

					new T(0.9583f, "test string1", "test string2"),
					new T(0.5000f, "test", "test string2"),
					new T(0.0000f, "", "test string2"),
					new T(0.9000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.9286f, "a b c d", "a b c e"),
					new T(0.9167f, "Healed", "Sealed"),
					new T(0.7143f, "Healed", "Healthy"),
					new T(0.7500f, "Healed", "Heard"),
					new T(0.8333f, "Healed", "Herded"),
					new T(0.5833f, "Healed", "Help"),
					new T(0.5833f, "Healed", "Sold"),
					new T(0.5833f, "Healed", "Help"),
					new T(0.7105f, "Sam J Chapman", "Samuel John Chapman"),
					new T(0.8636f, "Sam Chapman", "S Chapman"),
					new T(0.5789f, "John Smith", "Samuel John Chapman"),
					new T(0.5000f, "John Smith", "Sam Chapman"),
					new T(0.5000f, "John Smith", "Sam J Chapman"),
					new T(0.5000f, "John Smith", "S Chapman"),
					new T(0.5952f, "Web Database Applications",
							"Web Database Applications with PHP & MySQL"),
					new T(0.5098f, "Web Database Applications",
							"Creating Database Web Applications with PHP and ASP"),
					new T(0.5192f, "Web Database Applications",
							"Building Database Applications on the Web Using PHP3"),
					new T(0.5364f, "Web Database Applications",
							"Building Web Database Applications with Visual Studio 6"),
					new T(0.5139f, "Web Database Applications",
							"Web Application Development With PHP"),
					new T(
							0.5172f,
							"Web Database Applications",
							"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
					new T(0.5159f, "Web Database Applications",
							"Structural Assessment: The Role of Large and Full-Scale Testing"),
					new T(0.5313f, "Web Database Applications",
							"How to Find a Scholarship Online"),
					new T(0.5119f, "Web Aplications",
							"Web Database Applications with PHP & MySQL"),
					new T(0.5098f, "Web Aplications",
							"Creating Database Web Applications with PHP and ASP"),
					new T(0.5288f, "Web Aplications",
							"Building Database Applications on the Web Using PHP3"),
					new T(0.5182f, "Web Aplications",
							"Building Web Database Applications with Visual Studio 6"),
					new T(0.5000f, "Web Aplications",
							"Web Application Development With PHP"),
					new T(
							0.5345f,
							"Web Aplications",
							"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
					new T(0.5079f, "Web Aplications",
							"Structural Assessment: The Role of Large and Full-Scale Testing"),
					new T(0.5156f, "Web Aplications",
							"How to Find a Scholarship Online"), };
		}
	}

	public static final class NoGapValue extends StringMetricTest {

		@Override
		protected StringMetric getMetric() {
			return new NeedlemanWunch(0.0f, new MatchMismatch(0.0f, -1.0f));
		}

		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

		
		@Override
		protected T[] getTests()  {
			return new T[] { new T(1.0000f, "test string1", "test string2"),
					new T(1.0000f, "test", "test string2"),
					new T(1.0000f, "", "test string2"),
					new T(1.0000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(1.0000f, "a b c d", "a b c e"),
					new T(0.8333f, "Healed", "Sealed"),
					new T(1.0000f, "Healed", "Healthy"),
					new T(1.0000f, "Healed", "Heard"),
					new T(1.0000f, "Healed", "Herded"),
					new T(1.0000f, "Healed", "Help"),
					new T(0.8333f, "Healed", "Sold"),
					new T(1.0000f, "Healed", "Help"),
					new T(1.0000f, "Sam J Chapman", "Samuel John Chapman"),
					new T(1.0000f, "Sam Chapman", "S Chapman"),
					new T(0.9474f, "John Smith", "Samuel John Chapman"),
					new T(0.9091f, "John Smith", "Sam Chapman"),
					new T(0.9231f, "John Smith", "Sam J Chapman"),
					new T(0.9000f, "John Smith", "S Chapman"), };
		}
	}

	public static final class LowGapValue extends StringMetricTest {

		@Override
		protected StringMetric getMetric() {
			return new NeedlemanWunch(-0.1f, new MatchMismatch(0.0f, -1.0f));
		}

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

		
		@Override
		protected T[] getTests()  {
			return new T[] {
					new T(0.9833f, "test string1", "test string2"),
					new T(0.9333f, "test", "test string2"),
					new T(0.9000f, "", "test string2"),
					new T(0.9600f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.9714f, "a b c d", "a b c e"),
					new T(0.8333f, "Healed", "Sealed"),
					new T(0.9286f, "Healed", "Healthy"),
					new T(0.9500f, "Healed", "Heard"),
					new T(0.9333f, "Healed", "Herded"),
					new T(0.9333f, "Healed", "Help"),
					new T(0.7667f, "Healed", "Sold"),
					new T(0.9333f, "Healed", "Help"),
					new T(0.9684f, "Sam J Chapman", "Samuel John Chapman"),
					new T(0.9818f, "Sam Chapman", "S Chapman"),
					new T(0.8579f, "John Smith", "Samuel John Chapman"),
					new T(0.7727f, "John Smith", "Sam Chapman"),
					new T(0.7923f, "John Smith", "Sam J Chapman"),
					new T(0.7700f, "John Smith", "S Chapman"), };
		}
	}
}
