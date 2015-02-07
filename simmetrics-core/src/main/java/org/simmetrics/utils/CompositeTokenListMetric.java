package org.simmetrics.utils;

import org.simmetrics.ListMetric;
import org.simmetrics.StringMetric;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Preconditions;

public final class CompositeTokenListMetric implements StringMetric {

	private final ListMetric<String> metric;
	private final Simplifier simplifier;
	private final Tokenizer tokenizer;

	public CompositeTokenListMetric(ListMetric<String> metric,
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
		return metric.compare(
				tokenizer.tokenizeToList(simplifier.simplify(a)),
				tokenizer.tokenizeToList(simplifier.simplify(b)));
	}

	@Override
	public String toString() {
		return metric + " [" + simplifier + " -> " + tokenizer + "]";
	}

}