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

import static org.simmetrics.builders.StringDistanceBuilder.with;

import org.simmetrics.StringDistance;
import org.simmetrics.metrics.EuclideanDistance;
import org.simmetrics.metrics.StringDistances;
import org.simmetrics.tokenizers.Tokenizers;

/**
 * The StringDistances utility class contains a predefined list of well
 * known distance metrics for strings.
 */
public final class StringDistanceExample {

	/**
	 * Two strings can be compared using a predefined distance metric.
	 */
	public static float example01() {

		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similar. It has almost the same words";

		StringDistance metric = StringDistances.levenshtein();

		return metric.distance(str1, str2); // 30.0000
	}

	/**
	 * A tokenizer is included when the metric is a set or list metric. For the
	 * euclidean distance, it is a whitespace tokenizer.
	 * 
	 * Note that most predefined metrics are setup with a whitespace tokenizer.
	 */
	public static float example02() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similar. A quirky thing it is.";

		StringDistance metric = StringDistances.euclideanDistance();

		return metric.distance(str1, str2); // 2.0000
	}

	/**
	 * Using the string distance builder distance metrics can be customized.
	 * Instead of a whitespace tokenizer a q-gram tokenizer is used.
	 *
	 * For more examples see StringDistanceBuilderExample.
	 */
	public static float example03() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similar. A quirky thing it is.";

		StringDistance metric = 
				with(new EuclideanDistance<String>())
				.tokenize(Tokenizers.qGram(3))
				.build();

		return metric.distance(str1, str2); // 4.8989
	}

}
