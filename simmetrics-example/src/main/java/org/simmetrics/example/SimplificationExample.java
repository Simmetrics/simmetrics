
package org.simmetrics.example;

import static org.simmetrics.StringMetricBuilder.with;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.simplifiers.Case;
import org.simmetrics.simplifiers.NonDiacritics;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Whitespace;

/**
 * SimpleExample implements a simple example to demonstrate the ease to use a
 * similarity metric.
 * 
 * 
 */
public class SimplificationExample {

	/**
	 * Runs a simple example.
	 *
	 * @param args
	 *            two strings required for comparison
	 */
	public static void main(final String[] args) {

		final String str1 = "This is a sentence. It is made of words";
		final String str2 = "This sentence is similair. It has almost the same words";

		StringMetric metric = 
				with(new SimonWhite<String>())
				.simplify(new Case.Lower())
				.simplify(new NonDiacritics())
				.tokenize(new Whitespace())
				.tokenize(new QGram(2))
				.build();

		final float result = metric.compare(str1, str2); //0.5590

		String message = "Using metric %s on strings \"%s\" & \"%s\" gives a similarity score of %.4f";
		message = String.format(message, metric, str1, str2, result);
		System.out.println(message);

	}

}
