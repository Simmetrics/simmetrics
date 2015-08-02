/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics;

import org.simmetrics.Metric;
import org.simmetrics.StringDistance;
import org.simmetrics.StringDistanceTest;
import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricTest;
import org.simmetrics.metrics.Levenshtein;

@SuppressWarnings("javadoc")
public final class LevenshteinTest {

	public static final class DistanceString {

		public static final class UnitCost extends StringDistanceTest {

			@Override
			protected StringDistance getMetric() {
				return new Levenshtein();
			}

			@Override
			protected T[] getStringTests() {
				return new T[] {
						new T(1.0000f, "test string1", "test string2"),
						new T(8.0000f, "test", "test string2"),
						new T(12.0000f, "", "test string2"),
						new T(3.0000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
						new T(3.0000f, "aaa bbb", "aaa aaa"),
						new T(4.0000f, "aaa", "aaa aaa"),
						new T(1.0000f, "a b c d", "a b c e"),
						new T(3.0000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
						new T(1.0000f, "Healed", "Sealed"),
						new T(3.0000f, "Healed", "Healthy"),
						new T(2.0000f, "Healed", "Heard"),
						new T(2.0000f, "Healed", "Herded"),
						new T(3.0000f, "Healed", "Help"),
						new T(4.0000f, "Healed", "Sold"),
						new T(3.0000f, "Healed", "Help"),
						new T(6.0000f, "Sam J Chapman", "Samuel John Chapman"),
						new T(2.0000f, "Sam Chapman", "S Chapman"),
						new T(14.0000f, "John Smith", "Samuel John Chapman"),
						new T(11.0000f, "John Smith", "Sam Chapman"),
						new T(12.0000f, "John Smith", "Sam J Chapman"),
						new T(9.0000f, "John Smith", "S Chapman"),
						new T(17.0000f, "Web Database Applications",
								"Web Database Applications with PHP & MySQL"),
						new T(28.0000f, "Web Database Applications",
								"Creating Database Web Applications with PHP and ASP"),
						new T(30.0000f, "Web Database Applications",
								"Building Database Applications on the Web Using PHP3"),
						new T(30.0000f, "Web Database Applications",
								"Building Web Database Applications with Visual Studio 6"),
						new T(27.0000f, "Web Database Applications",
								"Web Application Development With PHP"),
						new T(
								62.0000f,
								"Web Database Applications",
								"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
						new T(53.0000f, "Web Database Applications",
								"Structural Assessment: The Role of Large and Full-Scale Testing"),
						new T(27.0000f, "Web Database Applications",
								"How to Find a Scholarship Online"),
						new T(27.0000f, "Web Aplications",
								"Web Database Applications with PHP & MySQL"),
						new T(36.0000f, "Web Aplications",
								"Creating Database Web Applications with PHP and ASP"),
						new T(39.0000f, "Web Aplications",
								"Building Database Applications on the Web Using PHP3"),
						new T(40.0000f, "Web Aplications",
								"Building Web Database Applications with Visual Studio 6"),
						new T(22.0000f, "Web Aplications",
								"Web Application Development With PHP"),
						new T(
								72.0000f,
								"Web Aplications",
								"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
						new T(56.0000f, "Web Aplications",
								"Structural Assessment: The Role of Large and Full-Scale Testing"),
						new T(26.0000f, "Web Aplications",
								"How to Find a Scholarship Online"), };
			}
		}

	}

	public static final class MetricString {

		public static final class UnitCost extends StringMetricTest {

			@Override
			protected StringMetric getMetric() {
				return new Levenshtein();
			}

			@Override
			protected T[] getStringTests() {
				return new T[] {

						new T(0.9166f, "test string1", "test string2"),
						new T(0.3333f, "test", "test string2"),
						new T(0.0000f, "", "test string2"),

						new T(0.8000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
						new T(0.5714f, "aaa bbb", "aaa aaa"),
						new T(0.4285f, "aaa", "aaa aaa"),

						new T(0.8571f, "a b c d", "a b c e"),

						new T(0.8000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
						new T(0.8333f, "Healed", "Sealed"),
						new T(0.5714f, "Healed", "Healthy"),
						new T(0.6667f, "Healed", "Heard"),
						new T(0.6667f, "Healed", "Herded"),
						new T(0.5000f, "Healed", "Help"),
						new T(0.3333f, "Healed", "Sold"),
						new T(0.5000f, "Healed", "Help"),
						new T(0.6842f, "Sam J Chapman", "Samuel John Chapman"),
						new T(0.8182f, "Sam Chapman", "S Chapman"),
						new T(0.2632f, "John Smith", "Samuel John Chapman"),
						new T(0.0000f, "John Smith", "Sam Chapman"),
						new T(0.0769f, "John Smith", "Sam J Chapman"),
						new T(0.1000f, "John Smith", "S Chapman"),
						new T(0.5952f, "Web Database Applications",
								"Web Database Applications with PHP & MySQL"),
						new T(0.4510f, "Web Database Applications",
								"Creating Database Web Applications with PHP and ASP"),
						new T(0.4231f, "Web Database Applications",
								"Building Database Applications on the Web Using PHP3"),
						new T(0.4545f, "Web Database Applications",
								"Building Web Database Applications with Visual Studio 6"),
						new T(0.2500f, "Web Database Applications",
								"Web Application Development With PHP"),
						new T(
								0.2874f,
								"Web Database Applications",
								"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
						new T(0.1587f, "Web Database Applications",
								"Structural Assessment: The Role of Large and Full-Scale Testing"),
						new T(0.1563f, "Web Database Applications",
								"How to Find a Scholarship Online"),
						new T(0.3571f, "Web Aplications",
								"Web Database Applications with PHP & MySQL"),
						new T(0.2941f, "Web Aplications",
								"Creating Database Web Applications with PHP and ASP"),
						new T(0.2500f, "Web Aplications",
								"Building Database Applications on the Web Using PHP3"),
						new T(0.2727f, "Web Aplications",
								"Building Web Database Applications with Visual Studio 6"),
						new T(0.3889f, "Web Aplications",
								"Web Application Development With PHP"),
						new T(
								0.1724f,
								"Web Aplications",
								"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
						new T(0.1111f, "Web Aplications",
								"Structural Assessment: The Role of Large and Full-Scale Testing"),
						new T(0.1875f, "Web Aplications",
								"How to Find a Scholarship Online"), };
			}
		}

		public static final class InsertDeleteCost extends StringMetricTest {

			@Override
			protected Metric<String> getMetric() {
				return new Levenshtein(0.1f, 1.0f);
			}

			@Override
			protected boolean satisfiesSubadditivity() {
				return false;
			}

			@Override
			protected T[] getStringTests() {
				return new T[] { new T(0.9500f, "InsertDelete", "Insert"),
						new T(0.9500f, "InsertDelete", "Delete"),
						new T(0.9500f, "DeleteInsert", "Insert"),
						new T(0.9500f, "DeleteInsert", "Delete"),
						new T(0.9833f, "test string1", "test string2"),
						new T(0.9333f, "test", "test string2"),
						new T(0.0000f, "", "test string2"),
						new T(0.9600f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
						new T(0.9143f, "aaa bbb", "aaa aaa"),
						new T(0.9429f, "aaa", "aaa aaa"),
						new T(0.9714f, "a b c d", "a b c e"), };
			}
		}

		public static final class NoSubstituteCost extends StringMetricTest {

			@Override
			protected Metric<String> getMetric() {
				return new Levenshtein(1.0f, 0.0f);
			}

			@Override
			protected boolean satisfiesCoincidence() {
				return false;
			}

			@Override
			protected T[] getStringTests() {
				return new T[] { new T(1.0000f, "Subsitute", "Subsytute"),
						new T(1.0000f, "Subsitute", "Sybsytyte"),
						new T(0.5000f, "abc", "abcdef"),
						new T(0.5000f, "def", "abcdef"),
						new T(1.0000f, "Healed", "Sealed"),
						new T(0.8571f, "Healed", "Healthy"),
						new T(0.8333f, "Healed", "Heard"),
						new T(1.0000f, "Healed", "Herded"),
						new T(0.6667f, "Healed", "Help"),
						new T(0.6667f, "Healed", "Sold"),
						new T(0.6667f, "Healed", "Help"),
						new T(1.0000f, "test string1", "test string2"),
						new T(0.3333f, "test", "test string2"),
						new T(0.0000f, "", "test string2"),
						new T(1.0000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
						new T(1.0000f, "aaa bbb", "aaa aaa"),
						new T(0.4286f, "aaa", "aaa aaa"),
						new T(1.0000f, "a b c d", "a b c e"),

				};
			}
		}

		public static final class LowSubstituteCost extends StringMetricTest {

			@Override
			protected Metric<String> getMetric() {
				return new Levenshtein(1.0f, 0.1f);
			}

			@Override
			protected T[] getStringTests() {
				return new T[] { new T(0.9889f, "Subsitute", "Subsytute"),
						new T(0.9667f, "Subsitute", "Sybsytyte"),
						new T(0.5000f, "abc", "abcdef"),
						new T(0.5000f, "def", "abcdef"),
						new T(0.9833f, "Healed", "Sealed"),
						new T(0.8286f, "Healed", "Healthy"),
						new T(0.8167f, "Healed", "Heard"),
						new T(0.9667f, "Healed", "Herded"),
						new T(0.6500f, "Healed", "Help"),
						new T(0.6333f, "Healed", "Sold"),
						new T(0.6500f, "Healed", "Help"),
						new T(0.9917f, "test string1", "test string2"),
						new T(0.3333f, "test", "test string2"),
						new T(0.0000f, "", "test string2"),
						new T(0.9800f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
						new T(0.9571f, "aaa bbb", "aaa aaa"),
						new T(0.4286f, "aaa", "aaa aaa"),
						new T(0.9857f, "a b c d", "a b c e"), };
			}
		}
	}
}
