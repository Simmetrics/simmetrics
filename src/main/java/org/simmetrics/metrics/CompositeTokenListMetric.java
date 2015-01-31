package org.simmetrics.metrics;

import org.simmetrics.StringMetric;
import org.simmetrics.TokenListMetric;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

public final class CompositeTokenListMetric implements StringMetric {

	private final TokenListMetric metric;
	private final Simplifier simplifier;
	private final Tokenizer tokenizer;

	public CompositeTokenListMetric(TokenListMetric metric,
			Simplifier simplifier, Tokenizer tokenizer) {
		super();
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
		return metric +" [" + simplifier + " -> " + tokenizer + "]";
	}

}
