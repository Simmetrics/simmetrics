package org.simmetrics;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

final class ForSetWithSimplifier implements StringMetric {

	private final Metric<Set<String>> metric;
	private final Simplifier simplifier;
	private final Tokenizer tokenizer;

	ForSetWithSimplifier(Metric<Set<String>> metric, Simplifier simplifier,
			Tokenizer tokenizer) {
		checkNotNull(metric);
		checkNotNull(simplifier);
		checkNotNull(tokenizer);

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

	Metric<Set<String>> getMetric() {
		return metric;
	}

	Simplifier getSimplifier() {
		return simplifier;
	}

	Tokenizer getTokenizer() {
		return tokenizer;
	}

	@Override
	public String toString() {
		return metric + " [" + simplifier + " -> " + tokenizer + "]";
	}

}