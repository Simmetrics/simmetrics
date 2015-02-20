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

import org.junit.Test;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.JaroWinkler;

public class JaroWinklerTest extends StringMetricTest {

	@Override
	protected StringMetric getMetric() {
		return new JaroWinkler();
	}

	/**
	 * Tests references from <a
	 * href="http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance"
	 * >Wikipedia - Jaro Winkler Distance</a>
	 */
	@Test
	public void testWikipediaExamples() {
		testSimilarity(getMetric(), new T(0.9611f, "MARTHA", "MARHTA"));
		testSimilarity(getMetric(), new T(0.8400f, "DWAYNE", "DUANE"));
		testSimilarity(getMetric(), new T(0.8133f, "DIXON", "DICKSONX"));
	}

	@Override
	protected T[] getTests() {
		return new T[] {
				new T(0.9778f, "test string1", "test string2"),
				new T(0.9467f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.9619f, "a b c d", "a b c e"),
				new T(0.8889f, "Healed", "Sealed"),
				new T(0.8476f, "Healed", "Healthy"),
				new T(0.8756f, "Healed", "Heard"),
				new T(0.7556f, "Healed", "Herded"),
				new T(0.8000f, "Healed", "Help"),
				new T(0.6111f, "Healed", "Sold"),
				new T(0.8000f, "Healed", "Help"),
				new T(0.8545f, "Sam J Chapman", "Samuel John Chapman"),
				new T(0.8288f, "Sam Chapman", "S Chapman"),
				new T(0.5945f, "John Smith", "Samuel John Chapman"),
				new T(0.4131f, "John Smith", "Sam Chapman"),
				new T(0.4949f, "John Smith", "Sam J Chapman"),
				new T(0.4333f, "John Smith", "S Chapman"),
				new T(0.9460f, "Web Database Applications",
						"Web Database Applications with PHP & MySQL"),
				new T(0.6901f, "Web Database Applications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(0.6353f, "Web Database Applications",
						"Building Database Applications on the Web Using PHP3"),
				new T(0.6582f, "Web Database Applications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(0.7786f, "Web Database Applications",
						"Web Application Development With PHP"),
				new T(
						0.7404f,
						"Web Database Applications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.4751f, "Web Database Applications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.4882f, "Web Database Applications",
						"How to Find a Scholarship Online"),
				new T(0.7981f, "Web Aplications",
						"Web Database Applications with PHP & MySQL"),
				new T(0.5980f, "Web Aplications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(0.5675f, "Web Aplications",
						"Building Database Applications on the Web Using PHP3"),
				new T(0.5909f, "Web Aplications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(0.9096f, "Web Aplications",
						"Web Application Development With PHP"),
				new T(
						0.7447f,
						"Web Aplications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.4751f, "Web Aplications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.4931f, "Web Aplications",
						"How to Find a Scholarship Online"), };
	}
}
