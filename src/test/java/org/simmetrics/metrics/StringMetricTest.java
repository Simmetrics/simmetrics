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
 */
package org.simmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.StringMetric;

public abstract class StringMetricTest {

	protected class T {
		protected final float similarity;
		protected final String string1;
		protected final String string2;

		public T(float similarity, String string1, String string2) {
			this.string1 = string1;
			this.string2 = string2;
			this.similarity = similarity;
		}

	}

	static final float DEFAULT_DELTA = 0.0001f;
	protected float delta;

	private StringMetric metric;

	public StringMetricTest() {
		super();
	}

	public abstract StringMetric getMetric();

	public abstract T[] getTests();

	@Before
	public void setUp() throws Exception {
		metric = getMetric();
		delta = getDelta();
	}

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	@Test
	public void testGetSimilarity() {
		for (T t : getTests()) {

			float actuall = metric.compare(t.string1, t.string2);
			assertTrue("Similarity must fall within [0.0 - 1.0] range",
					0.0f <= actuall && actuall <= 1.0f);

			String message = String.format("\"%s\" vs \"%s\"", t.string1,
					t.string2);
			assertEquals(message, t.similarity, actuall, delta);
		}
	}

	@Test
	public void generateTest() {
		for (T t : getTests()) {
			float actuall = metric.compare(t.string1, t.string2);
			String message = String.format("new T(%.4ff, \"%s\", \"%s\"),",
					actuall, t.string1, t.string2);
			System.out.println(message);
		}
	}

	@Test
	public void testToString() {
		assertFalse(
				"@ indicates toString() was not implemented "
						+ metric.toString(), metric.toString().contains("@"));

		assertToStringContains(metric, metric.getClass().getSimpleName());
	}

	protected static void assertToStringContains(StringMetric metric,
			String content) {
		String string = metric.toString();
		String message = String.format("%s must contain %s ", string, content);

		assertTrue(message, message.contains(content));
	}

}