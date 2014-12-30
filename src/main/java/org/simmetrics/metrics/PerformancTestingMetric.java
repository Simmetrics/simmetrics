/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package org.simmetrics.metrics;

import org.perf4j.StopWatch;
import org.simmetrics.StringMetric;

import com.google.common.base.Preconditions;

public class PerformancTestingMetric implements StringMetric {

	private final StringMetric metric;

	private long elapsedTime = 0;

	private int repeats = 1000;

	public void setRepeats(int repeats) {
		Preconditions.checkArgument(repeats > 0);
		this.repeats = repeats;
	}

	public int getRepeats() {
		return repeats;
	}

	public PerformancTestingMetric(StringMetric metric) {
		this.metric = metric;
	}



	public float compare(String string1, String string2) {
		StopWatch stopWatch = new StopWatch();
		float similarity = 0;
		for (int n = 0; n < repeats; n++) {
			similarity = metric.compare(string1, string2);
		}
		elapsedTime = stopWatch.getElapsedTime();
		return similarity;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	@Override
	public String toString() {
		return metric.toString();
	}

}
