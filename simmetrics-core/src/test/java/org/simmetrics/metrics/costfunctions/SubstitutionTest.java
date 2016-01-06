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

package org.simmetrics.metrics.costfunctions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.simmetrics.matchers.ImplementsToString.implementsToString;
import static org.simmetrics.matchers.ToStringContainsSimpleClassName.toStringContainsSimpleClassName;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.metrics.functions.Substitution;

@SuppressWarnings("javadoc")
public abstract class SubstitutionTest {

	protected static class T {
		protected final float cost;
		protected final String string1;
		protected final String string2;
		protected final int string1Index;
		protected final int string2Index;

		public T(float cost, String string1, int string1Index, String string2,
				int string2Index) {
			this.string1 = string1;
			this.string1Index = string1Index;
			this.string2 = string2;
			this.string2Index = string2Index;
			this.cost = cost;
		}

	}

	private static final float DEFAULT_DELTA = 0.0001f;
	private float delta;

	protected Substitution cost;

	protected abstract Substitution getCost();

	protected abstract T[] getTests();

	@Before
	public void setUp() throws Exception {
		cost = getCost();
		delta = getDelta();
	}

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	@Test
	public void testGetSimilarity() {
		for (T t : getTests()) {

			float actuall = cost.compare(t.string1, t.string1Index, t.string2,
					t.string2Index);

			String costMessage = "Cost must fall within [%.3f - %.3f] range";
			costMessage = String.format(costMessage, cost.min(),
					cost.max());
			assertTrue(costMessage, cost.min() <= actuall
					&& actuall <= cost.max());

			String message = String.format("\"%s\" vs \"%s\"",
					t.string1.charAt(t.string1Index),
					t.string2.charAt(t.string2Index));
			assertEquals(message, t.cost, actuall, delta);
		}
	}

	public void generateTest() {
		for (T t : getTests()) {
			float actuall = cost.compare(t.string1, t.string1Index, t.string2,
					t.string2Index);
			String message = String.format(
					"new T(%.4ff, testString1, %s, testString2, %s),", actuall,
					t.string1Index, t.string2Index);
			System.out.println(message);
		}
	}

	@Test
	public final void shouldImplementToString() {
		assertThat(cost, implementsToString());
		assertThat(cost, toStringContainsSimpleClassName());
	}

	protected static void assertToStringContains(Substitution metric,
			String content) {
		String string = metric.toString();
		String message = String.format("%s must contain %s ", string, content);

		assertTrue(message, message.contains(content));
	}

}
