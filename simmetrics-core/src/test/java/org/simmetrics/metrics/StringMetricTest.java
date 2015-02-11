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
	protected StringMetric metric;

	@Before
	public void setUp() throws Exception {
		delta = getDelta();
		metric = getMetric();
	}

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	protected abstract StringMetric getMetric();

	protected abstract T[] getTests();

	protected void testSimilarity(StringMetric metric, T... tests) {

		for (T t : tests) {

			float actuall = metric.compare(t.string1, t.string2);

			assertTrue("Similarity " + actuall
					+ " must fall within [0.0 - 1.0] range", 0.0f <= actuall
					&& actuall <= 1.0f);

			String message = String.format(
					"\"%s\" vs \"%s\"",
					t.string1,
					t.string2);
			assertEquals(message, t.similarity, actuall, delta);
		}
	}

	@Test
	public void testSimilarity() {
		testSimilarity(metric, getTests());
	}

	@Test
	public void testEmptyStrings() {
		testSimilarity(metric, new T(1.0f, "", "")); 
	}



	@Test
	public void testToString() {

		assertFalse(
				"@ indicates toString() was not implemented "
						+ metric.toString(),
				metric.toString().contains("@"));

		assertToStringContains(metric, metric.getClass().getSimpleName());
	}

	protected static void assertToStringContains(StringMetric tokenizer,
			String content) {
		String string = tokenizer.toString();
		String message = String.format("%s must contain %s ", string, content);

		assertTrue(message, message.contains(content));
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