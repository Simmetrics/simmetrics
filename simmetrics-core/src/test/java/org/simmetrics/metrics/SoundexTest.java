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

import static org.simmetrics.StringMetricBuilder.with;

import org.simmetrics.StringMetric;
import org.simmetrics.simplifiers.SoundexSimplifier;

public class SoundexTest extends StringMetricTest {

	@Override
	protected StringMetric getMetric() {
		return with(new JaroWinkler())
				.simplify(new SoundexSimplifier()).build();
	}

	@Override
	protected T[] getTests() {
		return new T[] {
				new T(1.0000f, "test string1", "test string2"),
				new T(0.9067f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.9067f, "a b c d", "a b c e"),
				new T(0.8667f, "Healed", "Sealed"),
				new T(1.0000f, "Healed", "Healthy"),
				new T(0.8800f, "Healed", "Heard"),
				new T(0.7600f, "Healed", "Herded"),
				new T(0.8933f, "Healed", "Help"),
				new T(0.8667f, "Healed", "Sold"),
				new T(0.8933f, "Healed", "Help"),
				new T(0.8933f, "Sam J Chapman", "Samuel John Chapman"),
				new T(0.7600f, "Sam Chapman", "S Chapman"),
				new T(0.7333f, "John Smith", "Samuel John Chapman"),
				new T(0.7333f, "John Smith", "Sam Chapman"),
				new T(0.7333f, "John Smith", "Sam J Chapman"),
				new T(0.6000f, "John Smith", "S Chapman"),
				new T(1.0000f, "Web Database Applications",
						"Web Database Applications with PHP & MySQL"),
				new T(0.4667f, "Web Database Applications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(0.4667f, "Web Database Applications",
						"Building Database Applications on the Web Using PHP3"),
				new T(0.4667f, "Web Database Applications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(0.6800f, "Web Database Applications",
						"Web Application Development With PHP"),
				new T(
						0.8933f,
						"Web Database Applications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.6000f, "Web Database Applications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.6222f, "Web Database Applications",
						"How to Find a Scholarship Online"),
				new T(0.6800f, "Web Aplications",
						"Web Database Applications with PHP & MySQL"),
				new T(0.4667f, "Web Aplications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(0.4667f, "Web Aplications",
						"Building Database Applications on the Web Using PHP3"),
				new T(0.4667f, "Web Aplications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(1.0000f, "Web Aplications",
						"Web Application Development With PHP"),
				new T(
						0.6800f,
						"Web Aplications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.4667f, "Web Aplications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.4667f, "Web Aplications",
						"How to Find a Scholarship Online"), };

	}
}
