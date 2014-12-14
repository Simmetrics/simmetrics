package org.simmetrics.metrics;

import org.perf4j.StopWatch;
import org.simmetrics.StringMetric;

public class PerformancTestingMetric implements StringMetric {

	private final StringMetric metric;

	public PerformancTestingMetric(StringMetric metric) {
		this.metric = metric;
	}

	public long getSimilarityTimingActual(String string1, String string2) {
		StopWatch stopWatch = new StopWatch();
		metric.compare(string1, string2);
		return stopWatch.getElapsedTime();
	}

	
	public float compare(String string1, String string2) {
		return metric.compare(string1, string2);
	}
	
	@Override
	public String toString() {
		return metric.toString();
	}

}
