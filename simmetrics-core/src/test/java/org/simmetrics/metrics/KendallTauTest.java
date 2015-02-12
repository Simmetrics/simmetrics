package org.simmetrics.metrics;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class KendallTauTest {

	@Test
	public void empty() {

		List<String> a = Arrays.asList();
		List<String> b = Arrays.asList();

		KendallTauSimilarity<String> metric = new KendallTauSimilarity<>();

		assertEquals(1.0f,metric.compare(a, b),  0.001f);

	}
	
	
	@Test
	public void inverse() {

		List<String> a = Arrays.asList("A", "B", "C", "D", "E");
		List<String> b = Arrays.asList("E", "D", "C", "B", "A");

		KendallTauSimilarity<String> metric = new KendallTauSimilarity<>();

		assertEquals(0.0f,metric.compare(a, b),  0.001f);

	}
	
	@Test
	public void test() {

		List<String> a = Arrays.asList("A", "B", "C", "D", "E");
		List<String> b = Arrays.asList("C", "D", "A", "B", "E");

		KendallTauSimilarity<String> metric = new KendallTauSimilarity<>();

		assertEquals(0.6f,metric.compare(a, b), 0.0001f);

	}

}
