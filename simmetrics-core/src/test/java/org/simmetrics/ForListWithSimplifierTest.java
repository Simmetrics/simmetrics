package org.simmetrics;

import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.Test;
import org.simmetrics.metrics.Identity;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;
import static org.simmetrics.StringMetrics.ForListWithSimplifier;

@SuppressWarnings("javadoc")
public class ForListWithSimplifierTest extends StringMetricTest{

	private final Tokenizer tokenizer = Tokenizers.whitespace();
	private final Metric<List<String>> metric = new Identity<>();
	private final Simplifier simplifier = Simplifiers.toLowerCase();
	
	@Override
	protected ForListWithSimplifier getMetric() {
		return new ForListWithSimplifier(metric, simplifier, tokenizer);
	}
	
	@Override
	protected T[] getStringTests() {
		return new T[]{
				new T(0.0f, "To repeat repeat is to repeat", ""),
				new T(1.0f, "To repeat repeat is to repeat", "to repeat repeat is to repeat"),
				new T(1.0f, "To repeat repeat is to repeat", "to  repeat  repeat  is  to  repeat")
		};
	}
	
	@Override
	protected boolean satisfiesCoincidence() {
		return false;
	}

	@Test
	public void shouldReturnTokenizer(){
		assertSame(tokenizer,getMetric().getTokenizer());
	}
	
	@Test
	public void shouldReturnMetric(){
		assertSame(metric, getMetric().getMetric());
	}
	
	@Test
	public void shouldReturnSimplifier(){
		assertSame(simplifier, getMetric().getSimplifier());
	}
}
