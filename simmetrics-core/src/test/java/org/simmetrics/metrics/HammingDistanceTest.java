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
import static java.util.Collections.emptyList;

import java.util.List;

import org.junit.Test;
import org.simmetrics.Distance;
import org.simmetrics.DistanceTest;

@SuppressWarnings("javadoc")
public final class HammingDistanceTest {

	public final static class DistanceListTest extends
			DistanceTest<List<String>> {

		@Override
		protected Distance<List<String>> getMetric() {
			return HammingDistance.forList();
		}

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

		@Override
		protected List<String> getEmpty() {
			return emptyList();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected DistanceTest.T<List<String>>[] getTests() {
			return new T[] {
					new T<>(0.0000f, asList("test", "string1"), asList("test",
							"string1")),
					new T<>(1.0000f, asList("test", "string1"), asList("test",
							"string2")),
					new T<>(1.0000f, asList("aaa", "bbb", "ccc", "ddd"),
							asList("aaa", "bbb", "ccc", "eee")),
					new T<>(1.0000f, asList("aaa", "bbb"), asList("aaa", "aaa")),
					new T<>(1.0000f, asList("a", "b", "c", "d"), asList("a",
							"b", "c", "e")),
					new T<>(2.0000f, asList("a", "b", "c", "d"), asList("a",
							"b", "e", "f")) };
		}

		@Test(expected = IllegalArgumentException.class)
		public void differentSize() {
			getMetric().distance(asList("test", "string1"), asList("test"));
		}

	}

	public final static class StringDistanceTest extends DistanceTest<String> {

		@Override
		protected Distance<String> getMetric() {
			return HammingDistance.forString();
		}

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

		@Override
		protected String getEmpty() {
			return "";
		}

		@SuppressWarnings("unchecked")
		@Override
		protected DistanceTest.T<String>[] getTests() {
			return new T[] { new T<>(0.0000f, "test string1", "test string1"),
					new T<>(1.0000f, "test string1", "test string2"),
					new T<>(3.0000f, "aaabbb", "aaaaaa"),
					new T<>(1.0000f, "abcd", "abce"),
					new T<>(2.0000f, "abcd", "abef") };
		}

		@Test(expected = IllegalArgumentException.class)
		public void differentLength() {
			getMetric().distance("test", "test string2");
		}

	}

}
