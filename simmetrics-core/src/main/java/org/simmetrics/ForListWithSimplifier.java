package org.simmetrics;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

final class ForListWithSimplifier implements StringMetric {
	private final Metric<List<String>> metric;
	private final Simplifier simplifier;
	private final Tokenizer tokenizer;

	ForListWithSimplifier(Metric<List<String>> metric,
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
		return metric.compare(
				tokenizer.tokenizeToList(simplifier.simplify(a)),
				tokenizer.tokenizeToList(simplifier.simplify(b)));
	}

	Metric<List<String>> getMetric() {
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