package org.simmetrics;

import static java.lang.Math.min;
import static java.lang.Math.max;

import java.util.List;

import uk.ac.shef.wit.simmetrics.simplifier.CachingSimplifier;
import uk.ac.shef.wit.simmetrics.simplifier.Simplifier;
import uk.ac.shef.wit.simmetrics.tokenisers.CachingTokenizer;
import uk.ac.shef.wit.simmetrics.tokenisers.Tokenizer;

public abstract class StringMetrics {

	private static final void addCache(StringMetric metric) {
		if (metric instanceof Simplifying) {
			Simplifier simplifier = ((Simplifying) metric).getSimplifier();
			CachingSimplifier caching = new CachingSimplifier(simplifier);
			((Simplifying) metric).setSimplifier(caching);
		}

		if (metric instanceof Tokenizing) {
			Tokenizer tokenizer = ((Tokenizing) metric).getTokenizer();
			CachingTokenizer caching = new CachingTokenizer(tokenizer);
			((Tokenizing) metric).setTokenizer(caching);
		}

	}

	private static final void removeCache(StringMetric metric) {
		if (metric instanceof Simplifying) {
			CachingSimplifier simplifier = (CachingSimplifier) ((Simplifying) metric)
					.getSimplifier();
			((Simplifying) metric).setSimplifier(simplifier.getSimplifier());
		}

		if (metric instanceof Tokenizing) {
			Tokenizer tokenizer = ((Tokenizing) metric).getTokenizer();
			((Tokenizing) metric).setTokenizer(((CachingTokenizer) tokenizer)
					.getTokenizer());
		}

	}

	public static final float[] compareList(StringMetric metric,
			final String comparator, final List<String> set) {

		addCache(metric);

		final float[] results = new float[set.size()];
		int i = 0;
		for (String s : set) {
			results[i++] = metric.compare(comparator, s);
		}

		removeCache(metric);

		return results;
	}

	public static final float[] compareArray(StringMetric metric,
			final String comparator, final String... set) {

		addCache(metric);

		final float[] results = new float[set.length];
		for (int i = 0; i < set.length; i++) {
			// perform similarity test
			results[i] = metric.compare(comparator, set[i]);
		}

		removeCache(metric);

		return results;
	}

	public static final float[] compareArrays(StringMetric metric,
			final String[] a, final String[] b) {
		final float[] results = new float[max(a.length, b.length)];
		final int min = min(a.length, b.length);
		for (int i = 0; i < min; i++) {
			results[i] = metric.compare(a[i], b[i]);
		}
		return results;
	}
}
