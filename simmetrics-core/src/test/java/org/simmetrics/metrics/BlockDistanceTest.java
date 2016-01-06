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
import static org.simmetrics.tokenizers.Tokenizers.qGram;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.Distance;
import org.simmetrics.Metric;
import org.simmetrics.MultisetDistanceTest;
import org.simmetrics.MultisetMetricTest;

import com.google.common.collect.Multiset;

@SuppressWarnings("javadoc")
@RunWith(Enclosed.class)
public final class BlockDistanceTest {

	public final static class DistanceTest extends MultisetDistanceTest {

		@Override
		protected Distance<Multiset<String>> getMetric() {
			return new BlockDistance<>();
		}

		@Override
		protected boolean satisfiesSubadditivity() {
			return true;
		}

		@Override
		protected T[] getTests() {
			return new T[] {
					new T(0.0000f, "test string1", "test string1"),
					new T(2.0000f, "test string1", "test string2"),
					new T(1.0000f, "test", "test string2"),
					new T(2.0000f, "", "test string2"),
					new T(2.0000f, getEmpty(), asList("test","string2")),
					new T(2.0000f, asList("test", null), asList("test","string2")),
					new T(2.0000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(2.0000f, "aaa bbb", "aaa aaa"),
					new T(1.0000f, "aaa", "aaa aaa"),
					new T(2.0000f, "a b c d", "a b c e"),
					new T(4.0000f, "a b c d", "a b e f"),
					new T(3.0000f, "a b c", "a b c e f g"),
					new T(5.0000f, "a b b c c", "a b c e f g"),
			};
		}
	}

	public final static class MetricTest extends MultisetMetricTest {

		@Override
		protected Metric<Multiset<String>> getMetric() {
			return new BlockDistance<>();
		}
		
		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}
		
		@Override
		protected T[] getTests() {
			return new T[] { 
					new T(1.0000f, "test string1", "test string1"),
					new T(0.5000f, "test string1", "test string2"),
					new T(0.6666f, "test", "test string2"),
					new T(0.0000f, "", "test string2"),
					new T(0.5000f, asList("test", null), asList("test","string2")),

					new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.5000f, "aaa bbb", "aaa aaa"),
					new T(0.6666f, "aaa", "aaa aaa"),
					
					new T(0.7500f, "a b c d", "a b c e"),
					new T(0.5000f, "a b c d", "a b e f"),
					new T(0.6666f, "a b c", "a b c e f g"),
					new T(0.5454f, "a b b c c", "a b c e f g"),
					
					new T(qGram(1), 0.8333f, "Healed", "Sealed"),
					new T(qGram(1), 0.6153f, "Healed", "Healthy"),
					new T(qGram(1), 0.7272f, "Healed", "Heard"),
					new T(qGram(1), 0.6666f, "Healed", "Herded"),
					new T(qGram(1), 0.6000f, "Healed", "Help"),
					new T(qGram(1), 0.4000f, "Healed", "Sold"),
					new T(qGram(1), 0.6000f, "Healed", "Help")
			};
		}



	}
}
