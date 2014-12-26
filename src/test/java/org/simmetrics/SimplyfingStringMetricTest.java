package org.simmetrics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.metrics.SimplyfingStringMetric;
import org.simmetrics.simplifier.PassThroughSimplifier;

public abstract class SimplyfingStringMetricTest extends StringMetricTest {

	private SimplyfingStringMetric metric;

	public abstract SimplyfingStringMetric getMetric();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		metric = getMetric();
	}

	@Test
	public void testToString() {
		super.testToString();

		if (metric.getSimplifier() instanceof PassThroughSimplifier) {
			assertFalse("Must not contain name of : "
					+ PassThroughSimplifier.class.getSimpleName(), metric
					.toString().contains(metric.getSimplifier().toString()));
			return;
		}

		assertToStringContains(metric, metric.getSimplifier().toString());
	}

	@Test
	public void testSetSimplifier() {
		PassThroughSimplifier simplifier = new PassThroughSimplifier();
		metric.setSimplifier(simplifier);
		assertSame(simplifier, metric.getSimplifier());
	}

	@Test
	public void testGetSimplifier() {
		assertNotNull(metric.getSimplifier());
	}
}