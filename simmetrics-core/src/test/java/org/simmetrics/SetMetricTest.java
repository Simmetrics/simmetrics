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

package org.simmetrics;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
public abstract class SetMetricTest extends MetricTest<Set<String>> {

	protected static final class T {
		protected final float similarity;
		protected final String string1;
		protected final String string2;

		public T(float similarity, String string1, String string2) {
			this.string1 = string1;
			this.string2 = string2;
			this.similarity = similarity;
		}

	}
	
	@Override
	protected MetricTest.T<Set<String>>[] getTests() {
		return transformTest(getTokenizer(), getSetTests());
	}

	protected abstract T[] getSetTests();

	protected abstract Tokenizer getTokenizer();

	private static MetricTest.T<Set<String>>[] transformTest(
			Tokenizer tokenizer, T... tests) {
		@SuppressWarnings("unchecked")
		MetricTest.T<Set<String>>[] transformed = new MetricTest.T[tests.length];
		for (int i = 0; i < tests.length; i++) {
			T t = tests[i];
			transformed[i] = new MetricTest.T<>(t.similarity,
					unmodifiableSet(tokenizer.tokenizeToSet(t.string1)),
					unmodifiableSet(tokenizer.tokenizeToSet(t.string2)));
		}
		return transformed;
	}

	@Override
	protected Set<String> getEmpty() {
		return emptySet();
	}
}
