package org.simmetrics;

import static com.google.common.base.Preconditions.checkNotNull;

import org.simmetrics.simplifiers.Simplifier;

final class ForStringWithSimplifier implements StringMetric {

	private final Metric<String> metric;

	private final Simplifier simplifier;

	ForStringWithSimplifier(Metric<String> metric, Simplifier simplifier) {
		checkNotNull(metric);
		checkNotNull(simplifier);

		this.metric = metric;
		this.simplifier = simplifier;
	}

	@Override
	public float compare(String a, String b) {
		return metric.compare(simplifier.simplify(a),
				simplifier.simplify(b));
	}

	Metric<String> getMetric() {
		return metric;
	}

	Simplifier getSimplifier() {
		return simplifier;
	}

	@Override
	public String toString() {
		return metric + " [" + simplifier + "]";
	}

}