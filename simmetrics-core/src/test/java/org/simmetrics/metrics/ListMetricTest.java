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

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.simmetrics.ListMetric;
import org.simmetrics.tokenizers.Tokenizer;

public abstract class ListMetricTest extends MetricTest<List<String>> {

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

	protected void testSimilarity(ListMetric<String> metric,
			Tokenizer tokenizer, T... tests) {

		for (T t : tests) {
			List<String> a = unmodifiableList(tokenizer
					.tokenizeToList(t.string1));
			List<String> b = unmodifiableList(tokenizer
					.tokenizeToList(t.string2));
			testMetric(metric, a, b, t.similarity);
		}
	}


	@Test
	public void testEmpty() {
		metric.compare(Collections.<String> emptyList(),
				Collections.<String> emptyList());
	}

	@Test
	public void testEqual1() {
		testMetric(metric, asList("candy"), asList("candy"), 1.0f);
	}

	@Test
	public void testEqual2() {
		testMetric(metric, asList("candy", "ice"), asList("candy", "ice"), 1.0f);
	}

	@Test
	public void testEqual3() {
		testMetric(metric, asList("candy", "ice", "slime"),
				asList("candy", "ice", "slime"), 1.0f);
	}

	@Test
	public void testEqual4() {
		testMetric(metric, asList("candy", "ice", "slime", "fire"),
				asList("candy", "ice", "slime", "fire"), 1.0f);
	}

}
