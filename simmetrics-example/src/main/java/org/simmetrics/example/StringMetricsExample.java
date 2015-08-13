/*
 * #%L
 * Simmetrics Examples
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package org.simmetrics.example;

import static org.simmetrics.StringMetricBuilder.with;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.StringMetrics;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizers;

/**
 * The {@link StringMetrics} utility class contains a predefined list of well
 * known metrics.
 */
@SuppressWarnings("javadoc")
public final class StringMetricsExample {

	/**
	 * Two strings can be compared using a predefined string metric.
	 */
	public static float example01() {

		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similair. It has almost the same words";

		StringMetric metric = StringMetrics.jaro();

		return metric.compare(str1, str2); // 0.7341
	}

	/**
	 * A tokenizer is included when the metric is a string or list metric. In
	 * the case of cosine similarity, it is a whitespace tokenizer.
	 */
	public static float example02() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similair. A quirky thing it is.";

		StringMetric metric = StringMetrics.cosineSimilarity();

		return metric.compare(str1, str2); // 0.7777
	}

	/**
	 * Using the string {@link StringMetricBuilder} metrics can be
	 * customized. Instead of a whitespace tokenizer a qgram tokenizer is used.
	 *
	 * For more examples see {@link StringMetricBuilderExample}.
	 */
	public static float example03() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similair. A quirky thing it is.";

		StringMetric metric = 
				with(new CosineSimilarity<String>())
				.tokenize(Tokenizers.qGram(3))
				.build();

		return metric.compare(str1, str2); // 0.7777
	}

	/**
	 * A single string can be compared against an array or list of strings.
	 */
	public static float[] example04() {
		
		String[] names = new String[] { "Childéric", "Childericus", "Clovis I",
				"Merovech", "Meroveus", "Merovius" };
		String name = "Chilpéric";

		StringMetric metric = 
				with(new CosineSimilarity<String>())
				.simplify(Simplifiers.removeDiacritics())
				.simplify(Simplifiers.toLowerCase())
				.tokenize(Tokenizers.qGram(3))
				.build();

		// [0.5714,0.5039,0.0000,0.0000,0.0000,0.0000]
		return StringMetrics.compare(metric, name, names);
	}

}
