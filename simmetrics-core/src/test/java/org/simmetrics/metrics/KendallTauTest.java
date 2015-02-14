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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class KendallTauTest {
	private KendallTau<String> metric = new KendallTau<>();

	@Test
	public void empty() {

		List<String> a = Arrays.asList();
		List<String> b = Arrays.asList();

		assertEquals(0.0f,metric.distance(a, b),  0.001f);

		assertEquals(1.0f,metric.compare(a, b),  0.001f);

	}
	
	
	@Test
	public void inverse() {

		List<String> a = Arrays.asList("A", "B", "C", "D", "E");
		List<String> b = Arrays.asList("E", "D", "C", "B", "A");

		assertEquals(10.0f,metric.distance(a, b), 0.0001f);

		assertEquals(0.0f,metric.compare(a, b),  0.001f);

	}
	
	@Test
	public void test() {

		List<String> a = Arrays.asList("A", "B", "C", "D", "E");
		List<String> b = Arrays.asList("C", "D", "A", "B", "E");

		assertEquals(4.0f,metric.distance(a, b), 0.0001f);

		assertEquals(0.6f,metric.compare(a, b), 0.0001f);

	}
	
	
	
	@Test
	public void testTriangleInEquality() {

		List<String> a = Arrays.asList("A", "B", "C", "D", "E");
		List<String> b = Arrays.asList("C", "D", "A", "B", "E");
		List<String> c = Arrays.asList("E", "D", "C", "B", "A");

		assertEquals(4.0f,metric.distance(a, b), 0.0001f);
		assertEquals(10.0f,metric.distance(a, c), 0.0001f);
		assertEquals(6.0f,metric.distance(b, c), 0.0001f);



	}

}
