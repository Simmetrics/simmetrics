/*
 * #%L
 * Simmetrics Examples
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
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
		String str2 = "This sentence is similair. It has almost the same words";
		
		StringMetric metric = StringMetrics.cosineSimilarity();
		
		float result = metric.compare(str1, str2); //0.4472
		
		return result;
	}
	
	public static float example02(){
		
		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similair. It has almost the same words";

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
