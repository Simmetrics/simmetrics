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

package org.simmetrics;

import static com.google.common.primitives.Floats.max;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.simmetrics.matchers.ImplementsToString.implementsToString;
import static org.simmetrics.matchers.ToStringContainsSimpleClassName.toStringContainsSimpleClassName;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public abstract class MetricTest<K> {

	protected static class TestCase<K> {
		protected final K a;
		protected final K b;
		protected final float similarity;

		public TestCase(float similarity, K a, K b) {
			this.a = a;
			this.b = b;
			this.similarity = similarity;
		}
	}

	private static final float DEFAULT_DELTA = 0.0001f;

	private static <K> boolean testCoincidence(Metric<K> metric, K a, K b) {
		return metric.compare(a, b) != 1.0f || a.equals(b);
	}

	private static <K> void testNullPointerException(Metric<K> metric, K a, K b) {
		try {
			metric.compare(null, b);
			fail("Metric should have thrown a null pointer exception for the first argument");
		} catch (NullPointerException ignored) {
			// Ignored
		}

		try {
			metric.compare(a, null);
			fail("Metric should have thrown a null pointer exception for the second argument");
		} catch (NullPointerException ignored) {
			// Ignored
		}

		try {
			metric.compare(null, null);
			fail("Metric should have thrown a null pointer exception for either argument");
		} catch (NullPointerException ignored) {
			// Ignored
		}
	}

	private static <K> void testRange(Metric<K> metric, K a, K b) {
		float similarity = metric.compare(a, b);
		String message1 = String.format(
				"Similarity %s-%s %f must fall within [0.0 - 1.0] range", a, b,
				similarity);
		assertTrue(message1, 0.0f <= similarity && similarity <= 1.0f);
	}

	private static <K> void testReflexive(Metric<K> metric, K a, float delta) {
		assertEquals("metric should be reflexive for " + a, 1.0f, metric.compare(a, a), delta);
	}

	private static <K> void testSimilarity(Metric<K> metric, K a, K b,
			float expected, float delta) {
		float similarity = metric.compare(a, b);
		String message = String.format("\"%s\" vs \"%s\"", a, b);
		assertEquals(message, expected, similarity, delta);
	}

	private static <K> boolean testSubadditivity(Metric<K> metric, K a, K b, K c) {

		float ab = 1.0f - metric.compare(a, b);
		float ac = 1.0f - metric.compare(a, c);
		float bc = 1.0f - metric.compare(b, c);

		return (ab == 0.0f || ac == 0.0f || bc == 0.0f || 2 * max(ab, ac, bc) <= ab
				+ ac + bc);
	}

	private static <K> void testSymmetric(Metric<K> metric, K a, K b,
			float delta) {
		float similarity = metric.compare(a, b);
		float similarityReversed = metric.compare(b, a);

		String message = String.format(
				"Similarity relation \"%s\" vs \"%s\" must be symmetric", a, b);
		assertEquals(message, similarityReversed, similarity, delta);

	}

	private float delta;

	protected Metric<K> metric;

	private TestCase<K>[] tests;

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	protected abstract K getEmpty();

	protected abstract Metric<K> getMetric();

	protected abstract TestCase<K>[] getTests();

	protected boolean satisfiesCoincidence() {
		return true;
	}

	protected boolean satisfiesSubadditivity() {
		return true;
	}
	
	protected boolean toStringIncludesSimpleClassName(){
		return true;
	}
	
	@Before
	public final void setUp() throws Exception {
		this.delta = getDelta();
		this.metric = getMetric();
		this.tests = getTests();
	}

	@Test
	public final void empty() {
		assertEquals(1.0f, metric.compare(getEmpty(), getEmpty()), delta);
	}

	@Test
	public final void nullPointerException() {
		for (TestCase<K> t : tests) {
			testNullPointerException(metric, t.a, t.b);
		}
	}

	@Test
	public final void coincidence() {
		if (satisfiesCoincidence()) {
			for (TestCase<K> t : tests) {
				assertTrue(
						format("coincidence did not hold for %s and %s", t.a,
								t.b), testCoincidence(metric, t.a, t.b));
			}
		} else {
			for (TestCase<K> t : tests) {
				if (!testCoincidence(metric, t.a, t.b)) {
					return;
				}
			}
			fail("no counter example for the coincidence property was found");
		}
	}

	@Test
	public final void subadditivity() {
		if (satisfiesSubadditivity()) {
			for (TestCase<K> n : tests) {
				for (TestCase<K> m : tests) {
					assertTrue(
							format("triangle ineqaulity must hold for %s, %s, %s",
									n.a, n.b, m.a),
							testSubadditivity(metric, n.a, n.b, m.a));
					assertTrue(
							format("triangle ineqaulity must hold for %s, %s, %s",
									n.a, n.b, m.b),
							testSubadditivity(metric, n.a, n.b, m.b));
					
				}
			}
		} else {
			for (TestCase<K> n : tests) {
				for (TestCase<K> m : tests) {
					if (!testSubadditivity(metric, n.a, n.b, m.a)
							|| !testSubadditivity(metric, n.a, n.b, m.b)) {
						return;
					}
				}
			}
			fail("no counter example for the subadditive property was found");
		}
	}

	@Test
	public final void range() {
		for (TestCase<K> t : tests) {
			testRange(metric, t.a, t.b);
		}
	}

	@Test
	public final void reflexive() {
		for (TestCase<K> t : tests) {
			testReflexive(metric, t.a, delta);
			testReflexive(metric, t.b, delta);
		}
	}

	@Test
	public final void similarity() {
		for (TestCase<K> t : tests) {
			testSimilarity(metric, t.a, t.b, t.similarity, delta);
		}
	}


	public final void generateSimilarity() {
		for (TestCase<K> t : tests) {
			System.out.println(format("new T<>(%1.4ff, \"%s\", \"%s\"),",
					metric.compare(t.a, t.b), t.a, t.b));
		}
	}

	@Test
	public final void symmetric() {
		for (TestCase<K> t : tests) {
			testSymmetric(metric, t.a, t.b, delta);
		}
	}

	@Test
	public final void containsEmptyVsNonEmptyTest() {
		final K empty = getEmpty();
		for (TestCase<K> t : tests) {
			if (t.a.equals(empty) ^ t.b.equals(empty)) {
				return;
			}
		}

		fail("tests did not contain empty vs non-empty test");
	}

	@Test
	public final void shouldImplementToString() {
		assertThat(metric, implementsToString());
	}
	
	@Test
	public final void shouldContainSimpleClassNameToString() {
		if(toStringIncludesSimpleClassName()){
			assertThat(metric, toStringContainsSimpleClassName());
		} else {
			assertThat(metric, not(toStringContainsSimpleClassName()));	
		}
	}

}
