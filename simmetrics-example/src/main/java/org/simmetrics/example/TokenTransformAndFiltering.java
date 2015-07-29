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
