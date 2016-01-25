/*
 * #%L
 * Simmetrics Examples
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.simmetrics.example;

import static org.simmetrics.builders.StringMetricBuilder.with;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.metrics.StringMetrics;
import org.simmetrics.tokenizers.Tokenizers;

/**
 * The StringMetrics utility class contains a predefined list of well
 * known similarity metrics for strings.
 */
public final class StringMetricsExample {

	/**
	 * Two strings can be compared using a predefined similarity metric.
	 */
	public static float example01() {

		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similar. It has almost the same words";

		StringMetric metric = StringMetrics.jaro();

		return metric.compare(str1, str2); // 0.7383
	}

	/**
	 * A tokenizer is included when the metric is a set or list metric. For the
	 * cosine similarity, it is a whitespace tokenizer.
	 * 
	 * Note that most predefined metrics are setup with a whitespace tokenizer.
	 */
	public static float example02() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similar. A quirky thing it is.";

		StringMetric metric = StringMetrics.cosineSimilarity();

		return metric.compare(str1, str2); // 0.7777
	}

	/**
	 * Using the string metric builder similarity metrics can be customized.
	 * Instead of a whitespace tokenizer a q-gram tokenizer is used.
	 *
	 * For more examples see StringMetricBuilderExample.
	 */
	public static float example03() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similar. A quirky thing it is.";

		StringMetric metric = 
				with(new CosineSimilarity<String>())
				.tokenize(Tokenizers.qGram(3))
				.build();

		return metric.compare(str1, str2); // 0.7473
	}

}
