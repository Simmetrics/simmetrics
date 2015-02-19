package org.simmetrics.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		testReflexsive(metric, a);
		testReflexsive(metric, b);
		testSymetric(metric, a, b);
		testExpected(metric, a, b, expected);
	}

	private void testExpected(Metric<T> metric, T a, T b, float expected) {
		float similarity = metric.compare(a, b);
		String message = String.format("\"%s\" vs \"%s\"", a, b);
		assertEquals(message, expected, similarity, delta);
	}

	private void testReflexsive(Metric<T> metric, T a) {
		assertEquals(1.0f, metric.compare(a, a), delta);
	}

	private void testRange(Metric<T> metric, T a, T b) {
		float similarity = metric.compare(a, b);
		String message1 = String.format(
				"Similarity %s-%s %f must fall within [0.0 - 1.0] range", a,b, similarity);
		assertTrue(message1, 0.0f <= similarity && similarity <= 1.0f);
	}

	private void testSymetric(Metric<T> metric, T a, T b) {
		float similarity = metric.compare(a, b);
		float similarityReversed = metric.compare(b, a);

		String message = String
				.format("Similarity relation \"%s\" vs \"%s\" must be symmetric",
						a, b, similarity, similarityReversed);
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
