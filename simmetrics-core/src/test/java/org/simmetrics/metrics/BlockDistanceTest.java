/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
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

import java.util.List;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.Distance;
import org.simmetrics.ListDistanceTest;
import org.simmetrics.ListMetricTest;
import org.simmetrics.Metric;
import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
@RunWith(Enclosed.class)
public final class BlockDistanceTest {

	public final static class DistanceList extends ListDistanceTest {

		@Override
		protected Distance<List<String>> getMetric() {
			return new BlockDistance<>();
		}

		@Override
		protected boolean satisfiesSubadditivity() {
			return true;
		}

		@Override
		protected T[] getListTests() {
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
			};
		}
	}

	public final static class MetricList extends ListMetricTest {

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

		@Override
		protected T[] getListTests() {
			final Tokenizer q1 = qGram(1);
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
