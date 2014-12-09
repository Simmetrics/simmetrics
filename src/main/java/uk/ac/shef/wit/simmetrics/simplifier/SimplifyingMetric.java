package uk.ac.shef.wit.simmetrics.simplifier;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.InterfaceStringMetric;

public class SimplifyingMetric extends AbstractStringMetric {

	private Simplifier simplifier;

	private InterfaceStringMetric metric;

	public SimplifyingMetric(Simplifier simplifier, InterfaceStringMetric metric) {
		super();
		this.simplifier = simplifier;
		this.metric = metric;
	}

	public SimplifyingMetric() {
		// Empty constructor
	}

	public void setSimplifier(Simplifier simplifier) {
		this.simplifier = simplifier;
	}

	public void setMetric(InterfaceStringMetric metric) {
		this.metric = metric;
	}

	public float getSimilarity(String string1, String string2) {
		return metric.getSimilarity(simplifier.simplify(string1),
				simplifier.simplify(string2));
	}

}
