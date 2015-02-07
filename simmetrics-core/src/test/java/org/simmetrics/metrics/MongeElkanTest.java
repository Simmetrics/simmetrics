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
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.metrics.MongeElkan;
import org.simmetrics.tokenizers.WhitespaceTokenizer;

public class MongeElkanTest extends StringMetricTest {

	@Override
	protected StringMetric getMetric() {
		return new StringMetricBuilder().with(
				new MongeElkan(new SmithWatermanGotoh()))
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	@Override
	protected T[] getTests() {
		return new T[] {
				new T(0.9286f, "test string1", "test string2"),
				new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.7500f, "a b c d", "a b c e"),
				new T(0.8333f, "Healed", "Sealed"),
				new T(0.6667f, "Healed", "Healthy"),
				new T(0.7200f, "Healed", "Heard"),
				new T(0.4667f, "Healed", "Herded"),
				new T(0.5000f, "Healed", "Help"),
				new T(0.4000f, "Healed", "Sold"),
				new T(0.5000f, "Healed", "Help"),
				new T(1.0000f, "Sam J Chapman", "Samuel John Chapman"),
				new T(1.0000f, "Sam Chapman", "S Chapman"),
				new T(0.6600f, "John Smith", "Samuel John Chapman"),
				new T(0.2917f, "John Smith", "Sam Chapman"),
				new T(0.6667f, "John Smith", "Sam J Chapman"),
				new T(0.6250f, "John Smith", "S Chapman"),
				new T(1.0000f,
						"Web Database Applications",
						"Web Database Applications with PHP & MySQL"),
				new T(1.0000f,
						"Web Database Applications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(1.0000f,
						"Web Database Applications",
						"Building Database Applications on the Web Using PHP3"),
				new T(1.0000f,
						"Web Database Applications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(0.8444f,
						"Web Database Applications",
						"Web Application Development With PHP"),
				new T(1.0000f,
						"Web Database Applications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.4000f,
						"Web Database Applications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.8667f,
						"Web Database Applications",
						"How to Find a Scholarship Online"),
				new T(0.9545f,
						"Web Aplications",
						"Web Database Applications with PHP & MySQL"),
				new T(0.9545f,
						"Web Aplications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(1.0000f,
						"Web Aplications",
						"Building Database Applications on the Web Using PHP3"),
				new T(0.9545f,
						"Web Aplications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(0.9091f,
						"Web Aplications",
						"Web Application Development With PHP"),
				new T(1.0000f,
						"Web Aplications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.4333f,
						"Web Aplications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.8000f,
						"Web Aplications",
						"How to Find a Scholarship Online"), };
	}
}
