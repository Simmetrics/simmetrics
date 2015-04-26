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

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.simmetrics.Metric;
import org.simmetrics.StringMetric;


public abstract class StringMetricTest extends MetricTest<String> {

	protected static class T {
		protected final float similarity;
		protected final String string1;
		protected final String string2;

		public T(float similarity, String string1, String string2) {
			this.string1 = string1;
			this.string2 = string2;
			this.similarity = similarity;
		}

	}

	protected abstract T[] getTests();
	
	protected void testSimilarity(StringMetric metric, T... tests) {
		testSimilarity((Metric<String>)metric, tests);
	}

	protected void testSimilarity(Metric<String> metric, T... tests) {

		for (T t : tests) {
			testMetric(metric, t.string1, t.string2, t.similarity);
		}
	}

	@Test
	public void testSimilarity() {
		testSimilarity(metric, getTests());
	}

	@Test
	public void testEmpty() {
		testSimilarity(metric, new T(1.0f, "", "")); 
	}
	
	@Test
	public void testEqual5() {
		assertEquals(1.0, metric.compare("candy","candy"), delta);
		assertEquals(1.0, metric.compare("slime","slime"), delta);
	}
	@Test
	public void testEqual4() {
		assertEquals(1.0, metric.compare("fire","fire"), delta);
	}
	
	@Test
	public void testEqual3() {
		assertEquals(1.0, metric.compare("ice","ice"), delta);
	}
	@Test
	public void testEqual2() {
		assertEquals(1.0, metric.compare("fa","fa"), delta);
	}
	
	@Test
	public void testEqual1() {
		assertEquals(1.0, metric.compare("c","c"), delta);
	}

	public void generateTest() {
		for (T t : getTests()) {
			float actuall = metric.compare(t.string1, t.string2);
			String message = String.format(
					"new T(%.4ff, \"%s\", \"%s\"),",
					actuall,
					t.string1,
					t.string2);
			System.out.println(message);
		}
	}

}