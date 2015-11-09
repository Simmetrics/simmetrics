package org.simmetrics;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.simmetrics.StringMetrics.ForString;
import org.simmetrics.metrics.Identity;

@SuppressWarnings("javadoc")
public class ForStringTest extends StringMetricTest{

	private final Metric<String> metric = new Identity<>();
	
	@Override
	protected ForString getMetric() {
		return new ForString(metric);
	}
	
	@Override
	protected T[] getStringTests() {
		return new T[]{
				new T(0.0f, "To repeat repeat is to repeat", ""),
				new T(1.0f, "To repeat repeat is to repeat", "To repeat repeat is to repeat"),
		};
	}
	
	@Test
	public void shouldReturnMetric(){
		assertSame(metric, getMetric().getMetric());
	}
}
