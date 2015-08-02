
package org.simmetrics.example;

import static org.simmetrics.StringMetricBuilder.with;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.JaroWinkler;
import org.simmetrics.metrics.MongeElkan;
import org.simmetrics.simplifiers.Soundex;
import org.simmetrics.tokenizers.Whitespace;

/**
 * SimpleExample implements a simple example to demonstrate the ease to use a
 * similarity metric.
 * 
 */
public class MatchingSoundexExample {

	/**
	 * Runs a simple example.
	 *
	 * @param args
	 *            two strings required for comparison
	 */
	public static void main(final String[] args) {

		String str1 = "Jack Samuel Jhonson";
		String str2 = "Jhonsonn Jack Sam";

		StringMetric metric = 
				with(new MongeElkan(
						with(new JaroWinkler())
						.simplify(new Soundex())
						.build()))
				.tokenize(new Whitespace())
				.build();

		float result = metric.compare(str1, str2); // 0.9644

		String message = "Using metric %s on strings \"%s\" & \"%s\" gives a similarity score of %.4f";
		message = String.format(message, metric, str1, str2, result);
		System.out.println(message);

	}

}
