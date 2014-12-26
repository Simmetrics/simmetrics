package org.simmetrics;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.metrics.TokenizingStringMetric;

public abstract class TokenizingStringMetricTest extends
		SimplyfingStringMetricTest {

	private TokenizingStringMetric metric;

	public abstract TokenizingStringMetric getMetric();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		metric = getMetric();
	}

	@Test
	public void testToString() {
		super.testToString();

		assertToStringContains(metric, metric.getTokenizer().toString());
	}

}
