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

import org.junit.Test;
import org.simmetrics.ListMetric;
import org.simmetrics.metrics.BlockDistance;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Whitespace;

public class BlockDistanceTest extends ListMetricTest {

	
	@Override
	public ListMetric<String> getMetric() {
		return new BlockDistance<>();
	}

	@Test
	public void test1() {
		testSimilarity(
				getMetric(), 
				new Whitespace(), 
				new T(0.5000f, "test string1", "test string2"), 
				new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.7500f, "a b c d", "a b c e"));
	}

	@Test
	public void test2() {
		testSimilarity(
				getMetric(),
				new QGram(1) ,
				new T(0.8333f, "Healed", "Sealed"),
				new T(0.6153f, "Healed", "Healthy"),
				new T(0.7272f, "Healed", "Heard"),
				new T(0.6666f, "Healed", "Herded"),
				new T(0.6000f, "Healed", "Help"),
				new T(0.4000f, "Healed", "Sold"),
				new T(0.6000f, "Healed", "Help")
		);
	}
}
