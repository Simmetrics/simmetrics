package org.simmetrics.metrics;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.ListMetric;

public abstract class ListMetricTest extends StringMetricTest {

	private ListMetric<String> listMetric;

	public abstract ListMetric<String> getListMetric();

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.listMetric = getListMetric();
	}

	@Test
	public void testUnmodifiable() {
		listMetric.compare(
				Collections.unmodifiableList(Arrays.asList("a", "b", "c", "d")),
				Collections.unmodifiableList(Arrays.asList("c", "d", "e", "f")));
	}

}
