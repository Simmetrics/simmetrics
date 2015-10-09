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

@SuppressWarnings("javadoc")
public abstract class StringDistanceTest extends DistanceTest<String> {
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

	private static DistanceTest.T<String>[] transformTest(T... tests) {
		@SuppressWarnings("unchecked")
		DistanceTest.T<String>[] transformed = new DistanceTest.T[tests.length];
		for (int i = 0; i < tests.length; i++) {
			T t = tests[i];
			transformed[i] = new DistanceTest.T<>(t.similarity,t.string1,t.string2);
		}
		return transformed;
	}

	@Override
	protected final DistanceTest.T<String>[] getTests() {
		return transformTest(getStringTests());
	}

	protected abstract T[] getStringTests();
	
	@Override
	protected final String getEmpty() {
		return "";
	}
}
