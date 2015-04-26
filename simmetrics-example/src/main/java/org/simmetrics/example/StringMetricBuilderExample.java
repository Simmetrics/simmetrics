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

import static org.simmetrics.StringMetricBuilder.with;

import java.util.Locale;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.metrics.*;
import org.simmetrics.simplifiers.Case;
import org.simmetrics.simplifiers.WordCharacters;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Whitespace;

/**
 * A simple demonstration of the {@link StringMetricBuilder} 
 * 
 */
public class StringMetricBuilderExample {

	/**
	 * Runs a simple example.
	 *
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		final String str1 = "String!with&Junk";
		final String str2 = "##Junk with String##";

		StringMetric metric = with(new CosineSimilarity<String>())
				.simplify(new Case.Lower(Locale.ENGLISH))
				.simplify(new WordCharacters())
				.tokenize(new Whitespace())
				.tokenize(new QGram(2)).build();

		final float result = metric.compare(str1, str2);

		String message = "Using metric %s on strings \"%s\" & \"%s\" gives a similarity score of %.4f";
		System.out.format(message, metric, str1, str2, result);

	}

}
