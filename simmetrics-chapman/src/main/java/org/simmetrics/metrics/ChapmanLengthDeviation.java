/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics;

import org.simmetrics.StringMetric;


/**
 * 
 * Implements the Chapman Length Deviation algorithm whereby the length
 * deviation of the input strings is used to determine if the strings are
 * similar in size. This is the ratio of difference in string lengths.
 * 
 * This approach is not intended to be used single handedly but rather alongside
 * other approaches
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class ChapmanLengthDeviation implements StringMetric {

	@Override
	public float compare(final String a, final String b) {
		
		if(a.isEmpty() && b.isEmpty()){
			return 1.0f;
		} else if(a.isEmpty() || b.isEmpty()){
			return 0.0f;
		}
		
		
		if (a.length() >= b.length()) {
			return (float) b.length() / (float) a.length();
		} else {
			return (float) a.length() / (float) b.length();
		}
	}

	@Override
	public String toString() {
		return "ChapmanLengthDeviation";
	}
	
	

}
