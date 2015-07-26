/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics;

import static java.lang.String.format;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.simmetrics.utils.Math.max;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.simmetrics.Distance;

@SuppressWarnings("javadoc")
public abstract class DistanceTest<K> {

	protected static final class T<K> {
		protected final K a;
		protected final K b;
		protected final float distance;

		public T(float distance, K a, K b) {
			this.a = a;
			this.b = b;
			this.distance = distance;
		}
	}

	private static final float DEFAULT_DELTA = 0.0001f;
	private static <K> void testCoincidence(Distance<K> distance, K a, K b) {
		if (distance.distance(a, b) == 0.0f) {
			assertTrue(format("coincidence did not hold for %s and %s", a, b),
					a.equals(b));
		}
	}
	private static <K> void testNullPointerException(Distance<K> distance, K a, K b) {
		try {
			distance.distance(null, b);
			fail("Distance should have thrown a null pointer exception for the first argument");
		} catch (NullPointerException ignored) {
			// Ignored
		}

		try {
			distance.distance(a, null);
			fail("Distance should have thrown a null pointer exception for the second argument");
		} catch (NullPointerException ignored) {
			// Ignored
		}

		try {
			distance.distance(null, null);
			fail("Distance should have thrown a null pointer exception for either argument");
		} catch (NullPointerException ignored) {
			// Ignored
		}
	}
	private static <K> void testRange(Distance<K> metric, K a, K b) {
		float similarity = metric.distance(a, b);
		String message1 = String.format(
				"Distance %s-%s %f must be non-negative", a, b,
				similarity);
		assertTrue(message1, 0.0f <= similarity);
	}

	private static <K> void testReflexive(Distance<K> metric, K a, float delta) {
		assertEquals(0.0f, metric.distance(a, a), delta);
	}

	private static <K> void testDistance(Distance<K> metric, K a, K b,
			float expected, float delta) {
		float similarity = metric.distance(a, b);
		String message = String.format("\"%s\" vs \"%s\"", a, b);
		assertEquals(message, expected, similarity, delta);
	}

	private static <K> void testSubadditivity(Distance<K> metric, K a, K b, K c) {

		float ab = metric.distance(a, b);
		float ac = metric.distance(a, c);
		float bc = metric.distance(b, c);

		assertTrue(
				format("triangle ineqaulity must hold for %s, %s, %s with %s, %s, %s",
						a, b, c, ab, ac, bc), ab == 0.0f || ac == 0.0f
						|| bc == 0.0f || 2 * max(ab, ac, bc) <= ab + ac + bc);
	}

	private static <K> void testSymmetric(Distance<K> metric, K a, K b,
			float delta) {
		float distance = metric.distance(a, b);
		float distanceReversed = metric.distance(b, a);

		String message = String.format(
				"Distance relation \"%s\" vs \"%s\" must be symmetric", a, b);
		assertEquals(message, distanceReversed, distance, delta);

	}

	protected float delta;

	protected Distance<K> metric;

	protected T<K>[] tests;

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	protected abstract K getEmpty();

	protected abstract Distance<K> getMetric();

	protected abstract T<K>[] getTests();
	
	protected boolean satisfiesCoincidence() {
		return true;
	}

	protected boolean satisfiesSubadditivity() {
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
		assertEquals(0.0f, metric.distance(getEmpty(), getEmpty()), delta);
	}

	@Test
	public final void nullPointerException() {
		for (T<K> t : tests) {
			testNullPointerException(metric, t.a, t.b);
		}
	}

	@Test
	public final void optionalCoincidence() {
		if (!satisfiesCoincidence()) {
			return;
		}

		for (T<K> t : tests) {
			testCoincidence(metric, t.a, t.b);
		}
	}

	@Test
	public final void optionalSubadditivity() {
		if (!satisfiesSubadditivity()) {
			return;
		}

		for (T<K> n : tests) {
			for (T<K> m : tests) {
				testSubadditivity(metric, n.a, n.b, m.a);
				testSubadditivity(metric, n.a, n.b, m.b);
			}
		}
	}

	@Test
	public final void range() {
		for (T<K> t : tests) {
			testRange(metric, t.a, t.b);
		}
	}

	@Test
	public final void reflexive() {
		for (T<K> t : tests) {
			testReflexive(metric, t.a, delta);
			testReflexive(metric, t.b, delta);
		}
	}



	@Test
	public final void distance() {
		for (T<K> t : tests) {
			testDistance(metric, t.a, t.b, t.distance, delta);
		}
	}
	
	@Test
	@Ignore
	public final void generateDistance() {
		for (T<K> t : tests) {
			System.out.println(format("new T<>(%1.4ff, \"%s\", \"%s\"),", metric.distance(t.a, t.b), t.a, t.b));
		}
	}

	@Test
	public final void symmetric() {
		for (T<K> t : tests) {
			testSymmetric(metric, t.a, t.b, delta);
		}
	}

	@Test
	public final void implementsToString() {

		String metricToString = metric.toString();
		String defaultToString = metric.getClass().getName() + "@" + Integer.toHexString(metric.hashCode());

		assertFalse(
				"toString() was not implemented "
						+ metric.toString(), defaultToString.equals(metricToString));

		String metricName = metric.getClass().getSimpleName();

		assertTrue(format("%s must contain %s ", metricToString,
				metricName), metricToString.contains(metricName));
	}

}
