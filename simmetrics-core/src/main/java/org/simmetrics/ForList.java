package org.simmetrics;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.simmetrics.tokenizers.Tokenizer;

final class ForList implements StringMetric {
	private final Metric<List<String>> metric;
	private final Tokenizer tokenizer;

	ForList(Metric<List<String>> metric, Tokenizer tokenizer) {

		checkNotNull(metric);
		checkNotNull(tokenizer);

		this.metric = metric;
		this.tokenizer = tokenizer;
	}

	@Override
	public float compare(String a, String b) {
		return metric.compare(tokenizer.tokenizeToList(a),
				tokenizer.tokenizeToList(b));
	}

	Metric<List<String>> getMetric() {
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