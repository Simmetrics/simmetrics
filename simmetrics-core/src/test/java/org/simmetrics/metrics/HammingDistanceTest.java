/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.simmetrics.metrics;

import static java.util.Arrays.asList;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.Distance;
import org.simmetrics.ListDistanceTest;
import org.simmetrics.StringDistanceTest;

@SuppressWarnings("javadoc")
@RunWith(Enclosed.class)
public final class HammingDistanceTest {

	public final static class DistanceList extends ListDistanceTest {

		@Override
		protected Distance<List<String>> getMetric() {
			return HammingDistance.forList();
		}
		@Override
		protected T[] getTests() {
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
		public void shouldThrowForDifferentLength() {
			getMetric().distance(asList("test", "string1"), asList("test"));
		}

	}

	public final static class DistanceString extends StringDistanceTest {

		@Override
		protected Distance<String> getMetric() {
			return HammingDistance.forString();
		}


		@Override
		protected T[] getTests() {
			return new T[] { new T(0.0000f, "test 1", "test 1"),
					new T(1.0000f, "test 1", "test 2"),
					new T(3.0000f, "aaabbb", "aaaaaa"),
					new T(1.0000f, "abcdxy", "abcexy"),
					new T(2.0000f, "abcdxy", "abefxy") };
		}

		@Test(expected = IllegalArgumentException.class)
		public void shouldThrowForDifferentLength() {
			getMetric().distance("test", "test string2");
		}

	}

}
