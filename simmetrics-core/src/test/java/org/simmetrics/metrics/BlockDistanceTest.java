/*
 * SimMetrics SimMetrics is a java library of Similarity or Distance Metrics,
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

import java.util.List;

import org.simmetrics.Distance;
import org.simmetrics.ListDistanceTest;
import org.simmetrics.ListMetricTest;
import org.simmetrics.Metric;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
public final class BlockDistanceTest {

	public final static class DistanceList extends ListDistanceTest {

		@Override
		protected Distance<List<String>> getMetric() {
			return new BlockDistance<>();
		}

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

		@Override
		protected T[] getListTests() {
			return new T[] {
					new T(0.0000f, "test string1", "test string1"),
					new T(2.0000f, "test string1", "test string2"),
					new T(1.0000f, "test", "test string2"),
					new T(2.0000f, "", "test string2"),
					new T(2.0000f, asList("test", null), asList("test","string2")),
					new T(2.0000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(2.0000f, "aaa bbb", "aaa aaa"),
					new T(1.0000f, "aaa", "aaa aaa"),
					new T(2.0000f, "a b c d", "a b c e"),
					new T(4.0000f, "a b c d", "a b e f") };
		}
	}

	public final static class MetricList extends ListMetricTest {

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

		@Override
		protected T[] getListTests() {
			final Tokenizer q1 = new QGram(1);
			return new T[] { 
					new T(0.5000f, asList("test", null), asList("test","string2")),
					new T(1.0000f, "test string1", "test string1"),
					new T(0.5000f, "test string1", "test string2"),
					new T(0.6666f, "test", "test string2"),
					new T(0.0000f, "", "test string2"),
					new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.5000f, "aaa bbb", "aaa aaa"),
					new T(0.6666f, "aaa", "aaa aaa"),
					new T(0.7500f, "a b c d", "a b c e"),
					new T(0.5000f, "a b c d", "a b e f"),

					new T(q1, 0.8333f, "Healed", "Sealed"),
					new T(q1, 0.6153f, "Healed", "Healthy"),
					new T(q1, 0.7272f, "Healed", "Heard"),
					new T(q1, 0.6666f, "Healed", "Herded"),
					new T(q1, 0.6000f, "Healed", "Help"),
					new T(q1, 0.4000f, "Healed", "Sold"),
					new T(q1, 0.6000f, "Healed", "Help")

			};
		}

		@Override
		protected Metric<List<String>> getMetric() {
			return new BlockDistance<>();
		}

	}
}
