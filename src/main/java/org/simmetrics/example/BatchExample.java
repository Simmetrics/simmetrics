package org.simmetrics.example;

import java.util.Arrays;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetrics;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifier.CaseSimplifier;
import org.simmetrics.tokenisers.QGram2Tokenizer;

public class BatchExample {

	private static String[] names = new String[] { "Maryjo Murff",
			"Page Desmarais", "Alayna Sawin", "Charmain Scoggin",
			"Sanora Larkey" };

	public static void main(String[] args) {

		StringMetric metric = new CosineSimilarity() {

			{
				setSimplifier(new CaseSimplifier.Lower());
				setTokenizer(new QGram2Tokenizer());
			}

		};

		float[] scores = StringMetrics.compare(metric, "Gearldine Desanti",
				names);

		System.out.println(Arrays.toString(scores));

	}

}
