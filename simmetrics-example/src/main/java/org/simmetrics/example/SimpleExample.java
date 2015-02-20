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
package org.simmetrics.example;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetrics;

/**
 * SimpleExample implements a simple example to demonstrate the ease to use a
 * similarity metric.
 * 
 * @author mpkorstanje
 */
public class SimpleExample {

	/**
	 * Runs a simple example.
	 *
	 * @param args
	 *            two strings required for comparison
	 */
	public static void main(final String[] args) {

		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similair. It has almost the same words";

		StringMetric metric = StringMetrics.cosineSimilarity();

		float result = metric.compare(str1, str2); //0.4472

		String message = "Using metric %s on strings \"%s\" & \"%s\" gives a similarity score of %.4f";
		message = String.format(message,metric,
				str1, str2, result);
		System.out.println(message);

	}


}
