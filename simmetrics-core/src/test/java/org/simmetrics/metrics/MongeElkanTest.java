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

import org.simmetrics.ListMetric;
import org.simmetrics.ListMetricTest;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.MongeElkan;

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
	protected T[] getListTests() {
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
