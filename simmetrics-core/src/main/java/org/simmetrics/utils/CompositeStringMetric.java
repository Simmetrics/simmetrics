package org.simmetrics.utils;

import org.simmetrics.Metric;
import org.simmetrics.StringMetric;
import org.simmetrics.simplifiers.Simplifier;

import com.google.common.base.Preconditions;

public final class CompositeStringMetric implements StringMetric {

	private final Simplifier simplifier;

	private final Metric<String> metric;

	public CompositeStringMetric(Metric<String> metric, Simplifier simplifier) {
		Preconditions.checkNotNull(metric);
		Preconditions.checkNotNull(simplifier);
		
		this.metric = metric;
		this.simplifier = simplifier;
	}

	@Override
	public float compare(String a, String b) {
		return metric.compare(simplifier.simplify(a),
				simplifier.simplify(b));
	}

	@Override
	public String toString() {
		return metric + " [" + simplifier + "]";
	}

}