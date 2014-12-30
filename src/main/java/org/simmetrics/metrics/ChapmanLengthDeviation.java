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
public class ChapmanLengthDeviation extends SimplyfingStringMetric {

	protected float compareSimplified(final String string1, final String string2) {
		if (string1.length() >= string2.length()) {
			return (float) string2.length() / (float) string1.length();
		} else {
			return (float) string1.length() / (float) string2.length();
		}
	}

}
