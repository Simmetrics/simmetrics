package org.simmetrics;

import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.Test;
import org.simmetrics.metrics.Identity;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;
import static org.simmetrics.StringMetrics.ForList;

@SuppressWarnings("javadoc")
public class ForListTest extends StringMetricTest {

	private final Tokenizer tokenizer = Tokenizers.whitespace();
	private final Metric<List<String>> metric = new Identity<>();

	@Override
	protected ForList getMetric() {
		return new ForList(metric, tokenizer);
	}

	@Override
	protected T[] getStringTests() {
		return new T[] { new T(0.0f, "To repeat repeat is to repeat", ""),
				new T(1.0f, "To repeat repeat is to repeat", "To repeat repeat is to repeat"),
				new T(1.0f, "To repeat repeat is to repeat", "To  repeat  repeat  is  to  repeat") };
	}

	@Override
	protected boolean satisfiesCoincidence() {
		return false;
	}

	@Test
	public void shouldReturnTokenizer() {
		assertSame(tokenizer, getMetric().getTokenizer());
	}

	@Test
	public void shouldReturnMetric() {
		assertSame(metric, getMetric().getMetric());
	}
}
