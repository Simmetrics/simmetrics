/*
 * #%L
 * Simmetrics Examples
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
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

import static org.simmetrics.StringMetricBuilder.with;

import java.util.Locale;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetrics;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizers;

/**
 * Examples from README.md
 */
@SuppressWarnings("javadoc")
public final class ReadMeExample {
	public static float example01(){
		
		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similar. It has almost the same words";
		
		StringMetric metric = StringMetrics.cosineSimilarity();
		
		float result = metric.compare(str1, str2); //0.4472
		
		return result;
	}
	
	public static float example02(){
		
		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similar. It has almost the same words";

		StringMetric metric =
				with(new CosineSimilarity<String>())
				.simplify(Simplifiers.toLowerCase(Locale.ENGLISH))
				.simplify(Simplifiers.replaceNonWord())
				.tokenize(Tokenizers.whitespace())
				.build();

		float result = metric.compare(str1, str2); //0.5590
		
		return result;
	}

}
