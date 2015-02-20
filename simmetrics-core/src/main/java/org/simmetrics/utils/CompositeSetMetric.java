package org.simmetrics.utils;

import java.util.Set;

import org.simmetrics.Metric;
import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Preconditions;

/**
 * String metric that is composed of a simplifier, tokenizer and set metric.
 * Applies simplification to the input and tokenizes the simplified input to a
 * set. The sets are compared by the metric.
 * 
 * @author mpkorstanje
 * 
 * @see StringMetricBuilder
 *
 */
public final class CompositeSetMetric implements StringMetric {

	private final Metric<Set<String>> metric;
	private final Simplifier simplifier;
	private final Tokenizer tokenizer;

	/**
	 * 
	 * @param metric
	 * @param simplifier
	 * @param tokenizer
	 */
	public CompositeSetMetric(Metric<Set<String>> metric,
			Simplifier simplifier, Tokenizer tokenizer) {
		super();
		Preconditions.checkNotNull(metric);
		Preconditions.checkNotNull(simplifier);
		Preconditions.checkNotNull(tokenizer);

		this.metric = metric;
		this.simplifier = simplifier;
		this.tokenizer = tokenizer;
	}

	@Override
	public float compare(String a, String b) {
		return metric.compare(tokenizer.tokenizeToSet(simplifier.simplify(a)),
				tokenizer.tokenizeToSet(simplifier.simplify(b)));
	}

	@Override
	public String toString() {
		return metric + " [" + simplifier + " -> " + tokenizer + "]";
	}

}