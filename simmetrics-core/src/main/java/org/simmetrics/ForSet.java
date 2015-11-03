package org.simmetrics;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

final class ForSet implements StringMetric {

	private final Metric<Set<String>> metric;
	private final Tokenizer tokenizer;

	ForSet(Metric<Set<String>> metric, Tokenizer tokenizer) {
		checkNotNull(metric);
		checkNotNull(tokenizer);

		this.metric = metric;
		this.tokenizer = tokenizer;
	}

	@Override
	public float compare(String a, String b) {
		return metric.compare(tokenizer.tokenizeToSet(a),
				tokenizer.tokenizeToSet(b));
	}

	Metric<Set<String>> getMetric() {
		return metric;
	}

	Tokenizer getTokenizer() {
		return tokenizer;
	}

	@Override
	public String toString() {
		return metric + " [" + tokenizer + "]";
	}

}