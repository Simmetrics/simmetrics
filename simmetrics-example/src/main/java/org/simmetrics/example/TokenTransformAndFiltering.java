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

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.tokenizers.Whitespace;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public class TokenTransformAndFiltering {

	public static void main(String[] args) {

		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similair. It has almost the same words";

		StringMetric metric = with(new CosineSimilarity<String>())
				.tokenize(new Whitespace())
				.filter(longWords())
				.transform(removePossessiveS())
				.filter(longWords()).build();

		float result = metric.compare(str1, str2); // 0.4472

		String message = "Using metric %s on strings \"%s\" & \"%s\" gives a similarity score of %.4f";
		message = String.format(message, metric, str1, str2, result);
		System.out.println(message);

	}

	private static Predicate<String> longWords() {
		return new Predicate<String>() {

			@Override
			public boolean apply(String input) {
				return input.length() >= 3;
			}

			@Override
			public String toString() {
				return "LongWords";
			}
		};

	}

	private static Function<String, String> removePossessiveS() {
		return new Function<String, String>() {

			@Override
			public String apply(String input) {
				if (input.endsWith("s")) {
					return input.substring(0, input.length() - 1);
				}
				return input;
			}

			@Override
			public String toString() {
				return "RemovePossessiveS";
			}
		};
	}

}
