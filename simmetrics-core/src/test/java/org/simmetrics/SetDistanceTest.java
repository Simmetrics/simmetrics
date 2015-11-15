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

import static java.util.Collections.unmodifiableSet;
import static org.junit.Assert.fail;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
public abstract class SetDistanceTest extends DistanceTest<Set<String>> {

	protected static final class T {
		protected final Set<String> a;
		protected final Set<String> b;
		protected final float similarity;

		public T(float similarity, Set<String> a, Set<String> b) {
			this.a = a;
			this.b = b;
			this.similarity = similarity;
		}

		public T(float similarity, String a, String b) {
			this(whitespace(), similarity, a, b);
		}
		public T(float similarity, List<String> a, List<String> b) {
			this(similarity, new HashSet<>(a), new HashSet<>(b));
		}
		public T(Tokenizer t, float similarity, String a, String b) {
			this(similarity, t.tokenizeToSet(a), t.tokenizeToSet(b));
		}

	}

	private static DistanceTest.T<Set<String>>[] transformTest(T... tests) {
		@SuppressWarnings("unchecked")
		DistanceTest.T<Set<String>>[] transformed = new DistanceTest.T[tests.length];
		for (int i = 0; i < tests.length; i++) {
			T t = tests[i];
			transformed[i] = new DistanceTest.T<>(t.similarity,
					unmodifiableSet(t.a), unmodifiableSet(t.b));
		}
		return transformed;
	}

	@Test
	public final void containsSetWithNullVsSetWithouthNullTest() {
		for (T t : getSetTests()) {
			if (t.a.contains(null) ^ t.b.contains(null)) {
				return;
			}
		}

		fail("tests did not contain set with null vs set without null test");
	}

	@Override
	protected final Set<String> getEmpty() {
		return Collections.emptySet();
	}

	protected abstract T[] getSetTests();

	@Override
	protected final org.simmetrics.DistanceTest.T<Set<String>>[] getTests() {
		return transformTest(getSetTests());
	}

}
