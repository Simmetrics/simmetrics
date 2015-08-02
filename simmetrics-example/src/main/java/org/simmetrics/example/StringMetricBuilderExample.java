
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
