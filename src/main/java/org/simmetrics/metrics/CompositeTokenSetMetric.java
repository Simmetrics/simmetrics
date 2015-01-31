package org.simmetrics.metrics;

import org.simmetrics.StringMetric;
import org.simmetrics.TokenSetMetric;
import org.simmetrics.simplifier.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

public final class CompositeTokenSetMetric implements StringMetric {

	private final TokenSetMetric metric;
	private final Simplifier simplifier;
	private final Tokenizer tokenizer;

	public CompositeTokenSetMetric(TokenSetMetric metric,
			Simplifier simplifier, Tokenizer tokenizer) {
		super();
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
		return metric +" [" + simplifier + " -> " + tokenizer + "]";
	}

}
