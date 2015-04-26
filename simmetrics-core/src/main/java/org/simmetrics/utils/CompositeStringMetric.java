package org.simmetrics.utils;

import org.simmetrics.Metric;
import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.simplifiers.Simplifier;

import com.google.common.base.Preconditions;

/**
 * String metric composed of a simplifier and string metric. Applies
 * simplification to the input. The simplifierd strings are compared by the
 * metric.
 * 
 * <p>
 * This class is immutable and thread-safe if its components are.
 * 
 * @see StringMetricBuilder
 *
 */
public final class CompositeStringMetric implements StringMetric {

	private final Simplifier simplifier;

	private final Metric<String> metric;

	/**
	 * Constructs a new composite string metric.
	 * 
	 * @param metric
	 *            a list metric
	 * @param simplifier
	 *            a simplifier
	 */
	public CompositeStringMetric(Metric<String> metric, Simplifier simplifier) {
		Preconditions.checkNotNull(metric);
		Preconditions.checkNotNull(simplifier);

		this.metric = metric;
		this.simplifier = simplifier;
	}

	@Override
	public float compare(String a, String b) {
		return metric.compare(simplifier.simplify(a), simplifier.simplify(b));
	}

	@Override
	public String toString() {
		return metric + " [" + simplifier + "]";
	}

}