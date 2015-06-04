package org.simmetrics.metrics;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.Metric;

public abstract class MetricTest<T> {

	static final float DEFAULT_DELTA = 0.0001f;
	protected float delta;
	protected Metric<T> metric;

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	@Before
	public void setUp() throws Exception {
		this.delta = getDelta();
		this.metric = getMetric();
	}

	protected abstract Metric<T> getMetric();

	protected void testMetric(Metric<T> metric, T a, T b, float expected) {

		testRange(metric, a, b);
		testReflexive(metric, a);
		testReflexive(metric, b);
		testSymmetric(metric, a, b);
		testExpected(metric, a, b, expected);
		testNullPointerException(metric, a, b);
	}

	private void testNullPointerException(Metric<T> metric, T a, T b) {
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

	private void testExpected(Metric<T> metric, T a, T b, float expected) {
		float similarity = metric.compare(a, b);
		String message = String.format("\"%s\" vs \"%s\"", a, b);
		assertEquals(message, expected, similarity, delta);
	}

	private void testReflexive(Metric<T> metric, T a) {
		assertEquals(1.0f, metric.compare(a, a), delta);
	}

	private void testRange(Metric<T> metric, T a, T b) {
		float similarity = metric.compare(a, b);
		String message1 = String.format(
				"Similarity %s-%s %f must fall within [0.0 - 1.0] range", a, b,
				similarity);
		assertTrue(message1, 0.0f <= similarity && similarity <= 1.0f);
	}

	private void testSymmetric(Metric<T> metric, T a, T b) {
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

}
