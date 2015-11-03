package org.simmetrics;

final class ForString implements StringMetric {
	private final Metric<String> metric;

	ForString(Metric<String> metric) {
		this.metric = metric;
	}

	@Override
	public float compare(String a, String b) {
		return metric.compare(a, b);
	}
	
	@Override
	public String toString() {
		return metric.toString();
	}

	Metric<String> getMetric() {
		return metric;
	}

}