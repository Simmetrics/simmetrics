package uk.ac.shef.wit.simmetrics.similaritymetrics;

import org.perf4j.StopWatch;

public class PerformancTestingMetric implements InterfaceStringMetric {

	private final InterfaceStringMetric metric;

	public PerformancTestingMetric(InterfaceStringMetric metric) {
		this.metric = metric;
	}
	@Deprecated
	public String getShortDescriptionString() {
		return metric.getShortDescriptionString();
	}
	@Deprecated
	public String getLongDescriptionString() {
		return metric.getLongDescriptionString();
	}

	public long getSimilarityTimingActual(String string1, String string2) {
		StopWatch stopWatch = new StopWatch();
		metric.getSimilarity(string1, string2);
		return stopWatch.getElapsedTime();
	}

	
	public float getSimilarityTimingEstimated(String string1, String string2) {
		return metric.getSimilarityTimingEstimated(string1, string2);
	}

	public float getSimilarity(String string1, String string2) {
		return metric.getSimilarity(string1, string2);
	}

	@Deprecated
	public String getSimilarityExplained(String string1, String string2) {
		return metric.getSimilarityExplained(string1, string2);
	}
	
	@Override
	public String toString() {
		return metric.toString();
	}

}
