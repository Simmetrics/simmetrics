package org.simmetrics.metrics;

import static java.lang.String.format;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.simmetrics.utils.Math.max3;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.simmetrics.Metric;

public abstract class MetricTest<K> {

	protected static final class T<K> {
		protected final float similarity;
		protected final K a;
		protected final K b;

		public T(float similarity, K a, K b) {
			this.a = a;
			this.b = b;
			this.similarity = similarity;
		}
	}

	static final float DEFAULT_DELTA = 0.0001f;
	protected float delta;
	protected Metric<K> metric;
	protected T<K>[] tests;

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	@Before
	public void setUp() throws Exception {
		this.delta = getDelta();
		this.metric = getMetric();
		this.tests = getTests();
	}

	protected abstract T<K>[] getTests();

	protected abstract Metric<K> getMetric();

	@Test
	public void nullPointerException() {
		for (T<K> t : tests) {
			testNullPointerException(metric, t.a, t.b);
		}
	}

	@Test
	public void range() {
		for (T<K> t : tests) {
			testRange(metric, t.a, t.b);
		}
	}

	@Test
	public void reflexive() {
		for (T<K> t : tests) {
			testReflexive(metric, t.a);
			testReflexive(metric, t.b);
		}
	}

	@Test
	public void symmetric() {
		for (T<K> t : tests) {
			testReflexive(metric, t.a);
			testReflexive(metric, t.b);
		}
	}

	@Test
	public void similarity() {
		for (T<K> t : tests) {
			testSimilarity(metric, t.a, t.b, t.similarity);
		}
	}

	@Test
	public void optionalSubadditivity () {
		if(!satisfiesSubadditivity()){
			return;
		}
		
		for (T<K> n : tests) {
			for (T<K> m : tests) {
				testSubadditivity(metric, n.a, n.b, m.a);
				testSubadditivity(metric, n.a, n.b, m.b);
			}
		}
	}

	protected boolean satisfiesSubadditivity(){
		return true;
	}

	private void testSubadditivity(Metric<K> metric, K a, K b, K c) {

		float ab = 1.0f - metric.compare(a, b);
		float ac = 1.0f - metric.compare(a, c);
		float bc = 1.0f - metric.compare(b, c);

		assertTrue(
				format("triangle ineqaulity must hold for %s, %s, %s with %s, %s, %s",
						a, b, c, ab, ac, bc), ab == 0.0f || ac == 0.0f
						|| bc == 0.0f || 2 * max3(ab, ac, bc) <= ab + ac + bc);
	}

	private void testNullPointerException(Metric<K> metric, K a, K b) {
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

	private void testSimilarity(Metric<K> metric, K a, K b, float expected) {
		float similarity = metric.compare(a, b);
		String message = String.format("\"%s\" vs \"%s\"", a, b);
		assertEquals(message, expected, similarity, delta);
	}

	private void testReflexive(Metric<K> metric, K a) {
		assertEquals(1.0f, metric.compare(a, a), delta);
	}

	private void testRange(Metric<K> metric, K a, K b) {
		float similarity = metric.compare(a, b);
		String message1 = String.format(
				"Similarity %s-%s %f must fall within [0.0 - 1.0] range", a, b,
				similarity);
		assertTrue(message1, 0.0f <= similarity && similarity <= 1.0f);
	}

	private void testSymmetric(Metric<K> metric, K a, K b) {
		float similarity = metric.compare(a, b);
		float similarityReversed = metric.compare(b, a);

		String message = String.format(
				"Similarity relation \"%s\" vs \"%s\" must be symmetric", a, b);
		assertEquals(message, similarityReversed, similarity, delta);

	}

	@Test
	public void testToString() {

		String string = metric.toString();

		assertFalse(
				"@ indicates toString() was not implemented "
						+ metric.toString(), string.contains("@"));

		String metricName = metric.getClass().getSimpleName();
		String message = String.format("%s must contain %s ", string,
				metricName);

		assertTrue(message, message.contains(metricName));
	}
	
	@Test
	public void testEmpty() {
		assertEquals(1.0f, metric.compare(getEmpty(), getEmpty()), delta);
	}

	protected abstract K getEmpty();


}
