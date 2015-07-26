package org.simmetrics.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import org.simmetrics.Metric;
import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;
/**
 * String metric composed of a simplifier, tokenizer and set metric. Applies
 * simplification to the input and tokenizes the simplified input to a set. The
 * sets are compared by the metric.
 * 
 * <p>
 * This class is immutable and thread-safe if its components are.
 * 
 * @see StringMetricBuilder
 *
 */
public final class CompositeSetMetric implements StringMetric {

	private final Metric<Set<String>> metric;
	private final Simplifier simplifier;
	private final Tokenizer tokenizer;

	/**
	 * Creates a new composite set metric.
	 * 
	 * @param metric
	 *            a list metric
	 * @param simplifier
	 *            a simplifier
	 * @param tokenizer
	 *            a tokenizer
	 */
	public CompositeSetMetric(Metric<Set<String>> metric,
			Simplifier simplifier, Tokenizer tokenizer) {
		checkNotNull(metric);
		checkNotNull(simplifier);
		checkNotNull(tokenizer);

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