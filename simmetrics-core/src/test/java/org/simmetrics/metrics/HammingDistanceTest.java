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

import java.util.List;

import org.junit.Test;
import org.simmetrics.Distance;
import org.simmetrics.ListDistanceTest;
import org.simmetrics.StringDistanceTest;

@SuppressWarnings("javadoc")
public final class HammingDistanceTest {

	public final static class DistanceList extends ListDistanceTest {

		@Override
		protected Distance<List<String>> getMetric() {
			return HammingDistance.forList();
		}
		@Override
		protected T[] getListTests() {
			return new T[] {
					new T(1.0000f, 
							asList("a", "b", "c", "d"),
							asList("a", "b", "c", "e")),
					new T(2.0000f, 
							asList("a", "b", "c", "d"),
							asList("a", "b", "e", "f")),
					new T(2.0000f, 
							asList("a", "b", "c", null),
							asList("a", "b", "e", "f"))
				};
		}

		@Test(expected = IllegalArgumentException.class)
		public void differentSize() {
			getMetric().distance(asList("test", "string1"), asList("test"));
		}

	}

	public final static class DistanceString extends StringDistanceTest {

		@Override
		protected Distance<String> getMetric() {
			return HammingDistance.forString();
		}


		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.0000f, "test 1", "test 1"),
					new T(1.0000f, "test 1", "test 2"),
					new T(3.0000f, "aaabbb", "aaaaaa"),
					new T(1.0000f, "abcdxy", "abcexy"),
					new T(2.0000f, "abcdxy", "abefxy") };
		}

		@Test(expected = IllegalArgumentException.class)
		public void differentLength() {
			getMetric().distance("test", "test string2");
		}

	}

}
