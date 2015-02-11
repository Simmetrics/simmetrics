package org.simmetrics.utils;

import org.simmetrics.SetMetric;
import org.simmetrics.StringMetric;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Preconditions;

public final class CompositeTokenSetMetric implements StringMetric {

	private final SetMetric<String> metric;
	private final Simplifier simplifier;
	private final Tokenizer tokenizer;

	public CompositeTokenSetMetric(SetMetric<String> metric,
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
				tokenizer.tokenizeToSet(simplifier.simplify(a)),
				tokenizer.tokenizeToSet(simplifier.simplify(b)));
	}

	@Override
	public String toString() {
		return metric + " [" + simplifier + " -> " + tokenizer + "]";
	}

}