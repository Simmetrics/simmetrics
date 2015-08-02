
package org.simmetrics.example;

import static org.simmetrics.StringMetricBuilder.with;

import java.util.Arrays;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetrics;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.Case;
import org.simmetrics.tokenizers.QGram;

public class BatchExample {

	public static void main(String[] args) {

		String[] names = new String[] { "Maryjo Murff", "Page Desmarais",
				"Alayna Sawin", "Charmain Scoggin", "Sanora Larkey" };
		String name = "Gearldine Desanti";

		StringMetric metric = with(new CosineSimilarity<String>())
				.simplify(new Case.Lower())
				.tokenize(new QGram(2))
				.build();

		float[] scores = StringMetrics.compare(metric, name, names);

		System.out.println(String.format(
				"Comparing %s to %s using %s gives: %s",
				Arrays.toString(names), name, metric, Arrays.toString(scores)));

	}
}
