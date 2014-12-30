package org.simmetrics.performance;

import java.util.Arrays;
import java.util.Set;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.PerformancTestingMetric;

public class Performance {

	private static final String string1 = "Sam J Chapman";
	private static final String string2 = "Samuel Chapman";
	private static final String string3 = "S Chapman";
	private static final String string4 = "Samuel John Chapman";
	private static final String string5 = "John Smith";
	private static final String string6 = "Richard Smith";
	private static final String string7 = "aaaa mnop zzzz";
	private static final String string8 = "bbbb mnop yyyy";
	private static final String string9 = "aa mnop zzzzzz";
	private static final String string10 = "a";
	private static final String string11 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	private static final String string12 = "aaaaa bcdefgh mmmmmmmm stuvwx zzzzzz";
	private static final String string13 = "jjjjj bcdefgh qqqqqqqq stuvwx yyyyyy";
	private static final String string14 = "aaaaa bcdefgh stuvwx zzzzzz";
	private static final String string15 = "aaaaa aaaaa aaaaa zzzzzz";
	private static final String string16 = "aaaaa aaaaa";

	private static final String[][] testCases = { { string1, string2 },
			{ string1, string3 }, { string2, string3 }, { string1, string1 },
			{ string4, string5 }, { string5, string6 }, { string5, string1 },
			{ string1, string6 }, { string1, string4 }, { string2, string4 },
			{ string7, string8 }, { string7, string9 }, { string8, string9 },
			{ string10, string10 }, { string11, string11 },
			{ string10, string11 }, { string12, string13 },
			{ string12, string14 }, { string14, string15 },
			{ string16, string16 } };

	public static void main(String[] args) {

		Set<StringMetric> metrics = MetricHandler.getMetricsAvailable();

		for (StringMetric m : metrics) {
			PerformancTestingMetric metric = new PerformancTestingMetric(m);
			float[] similarity = new float[testCases.length];
			long[] duration = new long[testCases.length];
			for (int i = 0; i < testCases.length; i++) {
				String[] test = testCases[i];
				similarity[i] = metric.compare(test[0], test[1]);
				duration[i] = metric.getElapsedTime();
			}
			
			System.out.println(Arrays.toString(similarity));
			System.out.println(Arrays.toString(duration));
		}

	}

}
