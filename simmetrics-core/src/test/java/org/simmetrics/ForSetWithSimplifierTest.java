package org.simmetrics;

import static org.junit.Assert.assertSame;

import java.util.Set;

import org.junit.Test;
import org.simmetrics.StringMetrics.ForSetWithSimplifier;
import org.simmetrics.metrics.Identity;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;

@SuppressWarnings("javadoc")
public class ForSetWithSimplifierTest extends StringMetricTest{

	private final Tokenizer tokenizer = Tokenizers.whitespace();
	private final Metric<Set<String>> metric = new Identity<>();
	private final Simplifier simplifier = Simplifiers.toLowerCase();
	
	@Override
	protected ForSetWithSimplifier getMetric() {
		return new ForSetWithSimplifier(metric, simplifier, tokenizer);
	}
	
	@Override
	protected T[] getStringTests() {
		return new T[]{
				new T(0.0f, "To repeat repeat is to repeat", ""),
				new T(1.0f, "To repeat repeat is to repeat", "to repeat repeat is to repeat"),
				new T(1.0f, "To repeat repeat is to repeat", "to  repeat  is  to  repeat")
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
