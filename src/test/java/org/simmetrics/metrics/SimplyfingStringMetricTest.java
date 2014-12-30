/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */package org.simmetrics.metrics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.metrics.SimplyfingStringMetric;
import org.simmetrics.simplifier.PassThroughSimplifier;

public abstract class SimplyfingStringMetricTest extends StringMetricTest {

	private SimplyfingStringMetric metric;

	public abstract SimplyfingStringMetric getMetric();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		metric = getMetric();
	}

	@Test
	public void testToString() {
		super.testToString();

		if (metric.getSimplifier() instanceof PassThroughSimplifier) {
			assertFalse("Must not contain name of : "
					+ PassThroughSimplifier.class.getSimpleName(), metric
					.toString().contains(metric.getSimplifier().toString()));
			return;
		}

		assertToStringContains(metric, metric.getSimplifier().toString());
	}

	@Test
	public void testSetSimplifier() {
		PassThroughSimplifier simplifier = new PassThroughSimplifier();
		metric.setSimplifier(simplifier);
		assertSame(simplifier, metric.getSimplifier());
	}

	@Test
	public void testGetSimplifier() {
		assertNotNull(metric.getSimplifier());
	}
}