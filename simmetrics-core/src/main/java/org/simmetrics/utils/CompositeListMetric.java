package org.simmetrics.utils;

import java.util.List;

import org.simmetrics.Metric;
import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Preconditions;

/**
 * String metric composed of a simplifier, tokenizer and list metric. Applies
 * simplification to the input and tokenizes the simplified input to a list. The
 * list are compared by the metric.
 * 
 * <p>
 * This class is immutable and thread-safe if its components are.
 * 
 * @see StringMetricBuilder
 */
public final class CompositeListMetric implements StringMetric {

	private final Metric<List<String>> metric;
	private final Simplifier simplifier;
	private final Tokenizer tokenizer;

	/**
	 * Creates a new composite list metric.
	 * 
	 * @param metric
	 *            a list metric
	 * @param simplifier
	 *            a simplifier
	 * @param tokenizer
	 *            a tokenizer
	 */
	public CompositeListMetric(Metric<List<String>> metric,
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
		return metric.compare(tokenizer.tokenizeToList(simplifier.simplify(a)),
				tokenizer.tokenizeToList(simplifier.simplify(b)));
	}

	@Override
	public String toString() {
		return metric + " [" + simplifier + " -> " + tokenizer + "]";
	}

}