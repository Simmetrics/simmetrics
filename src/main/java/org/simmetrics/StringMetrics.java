package org.simmetrics;

import java.util.List;

import org.simmetrics.simplifier.CachingSimplifier;
import org.simmetrics.simplifier.Simplifier;
import org.simmetrics.tokenisers.CachingTokenizer;
import org.simmetrics.tokenisers.Tokenizer;

/**
 * This class consists exclusively of static methods that apply a metric to
 * lists and arrays of strings.
 * 
 * 
 * @author mpkorstanje
 *
 */
public abstract class StringMetrics {

	/**
	 * Applies a metric to a string c and a list of strings. Returns an array
	 * with the similarity value for c and each string in the list.
	 * 
	 * @param metric
	 *            to compare c with each each value in the list
	 * @param c
	 *            string to compare the list against
	 * @param strings
	 *            to compare c against
	 * @return an array with the similarity value for c and each string in the
	 *         list
	 */
	public static final float[] compare(StringMetric metric, final String c,
			final List<String> strings) {

		final float[] results = new float[strings.size()];

		// Iterate because List.get() may not be efficient (e.g. LinkedList).
		int i = 0;
		for (String s : strings) {
			results[i++] = metric.compare(c, s);
		}

		return results;
	}

	/**
	 * Applies a metric to a string c and a list of strings. Returns an array
	 * with the similarity value for c and each string in the list.
	 * 
	 * @param metric
	 *            to compare c with each each value in the list
	 * @param c
	 *            string to compare the list against
	 * @param strings
	 *            to compare c against
	 * @return an array with the similarity value for c and each string in the
	 *         list
	 */
	public static final float[] compare(StringMetric metric, final String c,
			final String... strings) {

		final float[] results = new float[strings.length];
		for (int i = 0; i < strings.length; i++) {
			// perform similarity test
			results[i] = metric.compare(c, strings[i]);
		}

		return results;
	}

	/**
	 * Applies a metric to each pair of a[n] and b[n]. Returns an array where
	 * result[n] contains the similarity between a[n] and b[n].
	 * 
	 * @param metric
	 *            to compare each element in a and b
	 * @param a
	 *            array of string to compare
	 * @param b
	 *            array of string to compare
	 * @throws IllegalArgumentException
	 *             when a and b are of a different size
	 * @return a list of similarity values for each pair a[n] b[n].
	 */
	public static final float[] compareArrays(StringMetric metric,
			final String[] a, final String[] b) {

		if (a.length != b.length) {
			throw new IllegalArgumentException("arrays must have the same size");
		}

		final float[] results = new float[a.length];

		for (int i = 0; i < a.length; i++) {
			results[i] = metric.compare(a[i], b[i]);
		}
		return results;
	}
}
