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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.ListMetric;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetricTest.T;
import org.simmetrics.tokenizers.Tokenizer;

public abstract class ListMetricTest {

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

	private ListMetric<String> metric;

	static final float DEFAULT_DELTA = 0.0001f;
	protected float delta;

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	public abstract ListMetric<String> getMetric();

	@Before
	public void setUp() throws Exception {
		this.metric = getMetric();
		this.delta = getDelta();
	}

	@Test
	public void testUnmodifiable() {
		metric.compare(
				Collections.unmodifiableList(Arrays.asList("a", "b", "c", "d")),
				Collections.unmodifiableList(Arrays.asList("c", "d", "e", "f")));
	}

	@Test
	public void testToString() {

		String string = metric.toString();

		assertFalse(
				"@ indicates toString() was not implemented "
						+ metric.toString(), string.contains("@"));

		String metricName = metric.getClass().getSimpleName();
		String message = String.format("%s must contain %s ", string,
				metricName);

		assertTrue(message, message.contains(metricName));
	}

	protected void testSimilarity(ListMetric<String> metric,
			Tokenizer tokenizer, T... tests) {
		
		generateTest(metric, tokenizer, tests);

		for (T t : tests) {

			float similarity = metric.compare(
					tokenizer.tokenizeToList(t.string1),
					tokenizer.tokenizeToList(t.string2));

			String message1 = String.format(
					"Similarity %f must fall within [0.0 - 1.0] range",
					similarity);
			assertTrue(message1, 0.0f <= similarity && similarity <= 1.0f);

			String message = String.format("\"%s\" vs \"%s\"", t.string1,
					t.string2);

			assertEquals(message, t.similarity, similarity, delta);
		}
	}
	
	protected void generateTest(ListMetric<String> metric,
			Tokenizer tokenizer, T... tests) {
		for (T t : tests) {
			float actuall = metric.compare(
					tokenizer.tokenizeToList(t.string1),
					tokenizer.tokenizeToList(t.string2));
		
			String message = String.format(
					"new T(%.4ff, \"%s\", \"%s\"),",
					actuall,
					t.string1,
					t.string2);
			System.out.println(message);
		}
	}

	@Test
	public void testEmpty() {
		metric.compare(Collections.<String> emptyList(),
				Collections.<String> emptyList());
	}

	@Test
	public void testEqual() {
		assertEquals(1.0, metric.compare(
				Arrays.asList("candy", "ice", "slime", "fire"),
				Arrays.asList("candy", "ice", "slime", "fire")), delta);
	}
	
	
}
