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

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.ListMetric;


public abstract class ListMetricTest extends StringMetricTest {

	private ListMetric<String> listMetric;

	public abstract ListMetric<String> getListMetric();

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.listMetric = getListMetric();
	}

	@Test
	public void testUnmodifiable() {
		listMetric.compare(
				Collections.unmodifiableList(Arrays.asList("a", "b", "c", "d")),
				Collections.unmodifiableList(Arrays.asList("c", "d", "e", "f")));
	}

}
