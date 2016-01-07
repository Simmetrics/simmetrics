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
package org.simmetrics.simplifiers;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.simmetrics.matchers.ImplementsToString.implementsToString;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public abstract class SimplifierTest {
	protected static final class T {
		final String expected;
		final String string;

		public T(String string, String expected) {
			this.string = string;
			this.expected = expected;
		}

		public String string() {
			return string;
		}

	}

	private static void testSimplified(String expected, String simplified) {
		assertNotNull(simplified);
		assertEquals(expected, simplified);
	}

	protected Simplifier simplifier;

	protected T[] tests;

	@Test
	public void containsEmptyTest() {
		for (T t : tests) {
			if (t.string.isEmpty()) {
				return;
			}
		}

		fail("Test must contain a case with empty string");
	}

	protected abstract Simplifier getSimplifier();

	protected abstract T[] getTests();

	@Before
	public final void setUp() throws Exception {
		simplifier = getSimplifier();
		tests = getTests();
	}

	@Test
	public final void shouldImplementToString() {
		assertThat(simplifier, implementsToString());
	}

	@Test
	public final void simplfy() {
		for (T t : tests) {
			testSimplified(t.expected, simplifier.simplify(t.string));
		}
	}

	@Test(expected = NullPointerException.class)
	public final void simplfyNullPointerException() {
		simplifier.simplify(null);
	}

	public final void generateSimplified() {
		System.out.println(simplifier);
		for (T t : tests) {
			System.out.println(format("new T(\"%s\", \"%s\"),", t.string,
					simplifier.simplify(t.string)));
		}
	}
}
