/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics;

import static org.simmetrics.StringMetricBuilder.with;

import java.util.List;

import org.simmetrics.metrics.BlockDistance;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.metrics.DiceSimilarity;
import org.simmetrics.metrics.EuclideanDistance;
import org.simmetrics.metrics.JaccardSimilarity;
import org.simmetrics.metrics.Jaro;
import org.simmetrics.metrics.JaroWinkler;
import org.simmetrics.metrics.Levenshtein;
import org.simmetrics.metrics.MatchingCoefficient;
import org.simmetrics.metrics.MongeElkan;
import org.simmetrics.metrics.NeedlemanWunch;
import org.simmetrics.metrics.OverlapCoefficient;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.metrics.SmithWaterman;
import org.simmetrics.metrics.SmithWatermanGotoh;
import org.simmetrics.simplifiers.Soundex;
import org.simmetrics.tokenizers.QGramExtended;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Whitespace;

/**
 * Utility class for StringMetrics.
 * <p>
 * Consists of well known metrics and methods to apply a StringMetric to arrays
 * and lists of elements. All metrics are setup with sensible defaults, to
 * customize metrics use {@link StringMetricBuilder}.
 * <p>
 * The available metrics are:
 * 
 * <ul>
 * <li>Block Distance
 * <li>Cosine Similarity
 * <li>Dice Similarity
 * <li>Euclidean Distance
 * <li>Jaccard Similarity
 * <li>Jaro
 * <li>Jaro-Winkler
 * <li>LevenShtein
 * <li>Matching Coefficient
 * <li>Monge-Elkan
 * <li>NeedleMan Wunch
 * <li>Overlap Coefficient
 * <li>q-Grams Distance
 * <li>SimonWhite
 * <li>Smith-Waterman
 * <li>Smith-Waterman-Gotoh
 * <li>Smith-Waterman-Gotoh Windowed Affine
 * <li>Soundex
 * </ul>
 *
 */
public final class StringMetrics {

	private StringMetrics() {
		// Utility class.
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

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link BlockDistance} metric.
	 * 
	 * @return a block distance metric
	 */
	public static StringMetric blockDistance() {
		return with(new BlockDistance<String>())
				.tokenize(new Whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link CosineSimilarity} metric.
	 * 
	 * @return a cosine similarity metric
	 */
	public static StringMetric cosineSimilarity() {
		return with(new CosineSimilarity<String>())
				.tokenize(new Whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link DiceSimilarity} metric.
	 * 
	 * @return a dice similarity metric
	 */
	public static StringMetric diceSimilarity() {
		return with(new DiceSimilarity<String>())
				.tokenize(new Whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link EuclideanDistance} metric.
	 * 
	 * @return a Euclidean distance similarity metric
	 */
	public static StringMetric euclideanDistance() {
		return with(new EuclideanDistance<String>())
				.tokenize(new Whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link JaccardSimilarity} metric.
	 * 
	 * @return a Jaccard similarity metric
	 */
	public static StringMetric jaccardSimilarity() {
		return with(new JaccardSimilarity<String>())
				.tokenize(new Whitespace()).build();
	}

	/**
	 * Returns a string metric that uses the {@link Jaro} metric.
	 * 
	 * @return a Jaro metric
	 */
	public static StringMetric jaro() {
		return new Jaro();
	}

	/**
	 * Returns a string metric that uses the {@link JaroWinkler} metric.
	 * 
	 * @return a Jaro-Winkler metric
	 */
	public static StringMetric jaroWinkler() {
		return new JaroWinkler();
	}

	/**
	 * Returns a string metric that uses the {@link Levenshtein} metric.
	 * 
	 * @return a Levenshtein metric
	 */
	public static StringMetric levenshtein() {
		return new Levenshtein();
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link MatchingCoefficient} metric.
	 * 
	 * @return a matching coefficient metric
	 */
	public static StringMetric matchingCoefficient() {
		return with(new MatchingCoefficient<String>())
				.tokenize(new Whitespace())
				.build();
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link MongeElkan} metric with an internal {@link SmithWatermanGotoh}
	 * metric.
	 * 
	 * @return a Monge-Elkan metric
	 */
	public static StringMetric mongeElkan() {
		return with(
				new MongeElkan(new SmithWatermanGotoh()))
				.tokenize(new Whitespace())
				.build();
	}

	/**
	 * Returns a string metric that uses the {@link NeedlemanWunch} metric.
	 * 
	 * @return a Needleman-Wunch metric
	 */
	public static StringMetric needlemanWunch() {
		return new NeedlemanWunch();
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link OverlapCoefficient} metric.
	 * 
	 * @return a overlap coefficient metric
	 */
	public static StringMetric overlapCoefficient() {
		return with(new OverlapCoefficient<String>())
				.tokenize(new Whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a {@link QGramExtended} for
	 * {@code q=3} and the {@link BlockDistance} metric.
	 * 
	 * @return a q-grams distance metric
	 */
	public static StringMetric qGramsDistance() {
		return with(new BlockDistance<String>())
				.tokenize(new QGramExtended(3)).build();
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} followed
	 * by a {@link QGramExtended} for {@code q=2} and the
	 * {@link SimonWhite} metric.
	 * 
	 * @return a Simon White metric
	 */
	public static StringMetric simonWhite() {
		return with(new SimonWhite<String>())
				.tokenize(new Whitespace())
				.tokenize(new QGram(2)).build();
	}

	/**
	 * Returns a string metric that uses the {@link SmithWaterman} metric.
	 * 
	 * @return a Smith-Waterman metric
	 */
	public static StringMetric smithWaterman() {
		return new SmithWaterman();
	}

	/**
	 * Returns a string metric that uses the {@link SmithWatermanGotoh} metric.
	 * 
	 * @return a Smith-Waterman-Gotoh metric
	 */
	public static StringMetric smithWatermanGotoh() {
		return new SmithWatermanGotoh();
	}

	/**
	 * Returns a string metric that uses a {@link Soundex} and
	 * {@link JaroWinkler} metric.
	 * 
	 * @return a Soundex metric
	 */
	public static StringMetric soundex() {
		return with(new JaroWinkler())
				.simplify(new Soundex()).build();
	}
}
