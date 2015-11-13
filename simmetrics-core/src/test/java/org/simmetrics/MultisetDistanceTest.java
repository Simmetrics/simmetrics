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

import static org.junit.Assert.fail;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.util.List;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

@SuppressWarnings("javadoc")
public abstract class MultisetDistanceTest extends DistanceTest<Multiset<String>> {

	protected static final class T {
		protected final Multiset<String> a;
		protected final Multiset<String> b;
		protected final float similarity;

		public T(float similarity, List<String> a, List<String> b) {
			this(similarity, HashMultiset.create(a), b);
		}
		public T(float similarity, Multiset<String> a, List<String> b) {
			this.a = a;
			this.b = HashMultiset.create(b);
			this.similarity = similarity;
		}
		public T(float similarity, String a, String b) {
			this(whitespace(), similarity, a, b);
		}

		public T(Tokenizer t, float similarity, String a, String b) {
			this(similarity, t.tokenizeToList(a), t.tokenizeToList(b));
		}
	}

	private static DistanceTest.T<Multiset<String>>[] transformTest(T... tests) {
		@SuppressWarnings("unchecked")
		DistanceTest.T<Multiset<String>>[] transformed = new DistanceTest.T[tests.length];
		for (int i = 0; i < tests.length; i++) {
			T t = tests[i];
			transformed[i] = new DistanceTest.T<>(t.similarity,t.a, t.b);
		}
		return transformed;
	}

	@Test
	public final void containsListWithNullVsListWithouthNullTest() {
		for (T t : getListTests()) {
			if (t.a.contains(null) ^ t.b.contains(null)) {
				return;
			}
		}

		fail("tests did not contain list with null vs list without null test");
	}

	@Override
	protected final Multiset<String> getEmpty() {
		return HashMultiset.create();
	}

	protected abstract T[] getListTests();

	@Override
	protected final org.simmetrics.DistanceTest.T<Multiset<String>>[] getTests() {
		return transformTest(getListTests());
	}

}
