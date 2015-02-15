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

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.ListMetric;
import org.simmetrics.SetMetric;
import org.simmetrics.tokenizers.Tokenizer;

public abstract class SetMetricTest {

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

	private SetMetric<String> metric;

	static final float DEFAULT_DELTA = 0.0001f;
	protected float delta;

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	public abstract SetMetric<String> getMetric();

	@Before
	public void setUp() throws Exception {
		this.metric = getMetric();
		this.delta = getDelta();
	}

	@Test
	public void testUnmodifiable() {
		metric.compare(
				unmodifiableSet(newHashSet("a", "b", "c", "d")),
				unmodifiableSet(newHashSet("c", "d", "e", "f")));
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

	protected void testSimilarity(SetMetric<String> metric,
			Tokenizer tokenizer, T... tests) {

		for (T t : tests) {

			float similarity = metric.compare(
					tokenizer.tokenizeToSet(t.string1),
					tokenizer.tokenizeToSet(t.string2));

			String message1 = String.format(
					"Similarity %f must fall within [0.0 - 1.0] range",
					similarity);
			assertTrue(message1, 0.0f <= similarity && similarity <= 1.0f);

			String message = String.format("\"%s\" vs \"%s\"", t.string1,
					t.string2);

			assertEquals(message, t.similarity, similarity, delta);
		}
	}

	@Test
	public void testEmpty() {
		metric.compare(Collections.<String> emptySet(),
				Collections.<String> emptySet());
	}

	
	@Test
	public void testEqual1() {
		assertEquals(1.0, metric.compare(
				newHashSet("candy"),
				newHashSet("candy")), delta);
	}
	@Test
	public void testEqual2() {
		assertEquals(1.0, metric.compare(
				newHashSet("candy","ice"),
				newHashSet("candy","ice")), delta);
	}
	
	@Test
	public void testEqual3() {
		assertEquals(1.0, metric.compare(
				newHashSet("candy","ice","slime"),
				newHashSet("candy","ice","slime")), delta);
	}
	
	@Test
	public void testEqual4() {
		assertEquals(1.0, metric.compare(
				newHashSet("candy", "ice", "slime", "fire"),
				newHashSet("candy", "ice", "slime", "fire")), delta);
	}
}
