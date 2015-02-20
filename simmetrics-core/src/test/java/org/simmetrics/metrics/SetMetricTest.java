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

import static java.util.Collections.unmodifiableSet;
import java.util.Set;

import org.simmetrics.SetMetric;
import org.simmetrics.tokenizers.Tokenizer;

public abstract class SetMetricTest extends MetricTest<Set<String>> {

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
	
	protected void testSimilarity(SetMetric<String> metric,
			Tokenizer tokenizer, T... tests) {

		for (T t : tests) {

			Set<String> a = unmodifiableSet(tokenizer
					.tokenizeToSet(t.string1));
			Set<String> b = unmodifiableSet(tokenizer
					.tokenizeToSet(t.string2));
			testMetric(metric, a, b, t.similarity);
		}
	}

//	@Test
//	public void testEmpty() {
//		metric.compare(Collections.<String> emptySet(),
//				Collections.<String> emptySet());
//	}
//
//	
//	@Test
//	public void testEqual1() {
//		assertEquals(1.0, metric.compare(
//				newHashSet("candy"),
//				newHashSet("candy")), delta);
//	}
//	@Test
//	public void testEqual2() {
//		assertEquals(1.0, metric.compare(
//				newHashSet("candy","ice"),
//				newHashSet("candy","ice")), delta);
//	}
//	
//	@Test
//	public void testEqual3() {
//		assertEquals(1.0, metric.compare(
//				newHashSet("candy","ice","slime"),
//				newHashSet("candy","ice","slime")), delta);
//	}
//	
//	@Test
//	public void testEqual4() {
//		assertEquals(1.0, metric.compare(
//				newHashSet("candy", "ice", "slime", "fire"),
//				newHashSet("candy", "ice", "slime", "fire")), delta);
//	}
}
