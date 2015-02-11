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
import org.simmetrics.metrics.SmithWatermanGotohWindowedAffine;
import org.simmetrics.simplifiers.SoundexSimplifier;
import org.simmetrics.tokenizers.QGramExtendedTokenizer;
import org.simmetrics.tokenizers.QGramTokenizer;
import org.simmetrics.tokenizers.WhitespaceTokenizer;

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
 * 
 * @author mpkorstanje
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

	public static StringMetric blockDistance() {
		return new StringMetricBuilder().with(new BlockDistance<String>())
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	public static StringMetric cosineSimilarity() {
		return new StringMetricBuilder().with(new CosineSimilarity<String>())
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	public static StringMetric diceSimilarity() {
		return new StringMetricBuilder().with(new DiceSimilarity<String>())
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	public static StringMetric euclideanDistance() {
		return new StringMetricBuilder().with(new EuclideanDistance<String>())
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	public static StringMetric jaccardSimilarity() {
		return new StringMetricBuilder().with(new JaccardSimilarity<String>())
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	public static StringMetric jaro() {
		return new Jaro();
	}

	public static StringMetric jaroWinkler() {
		return new JaroWinkler();
	}

	public static StringMetric levenshtein() {
		return new Levenshtein();
	}

	public static StringMetric matchingCoefficient() {
		return new StringMetricBuilder().with(new MatchingCoefficient<String>())
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	public static StringMetric mongeElkan() {
		return new StringMetricBuilder().with(
				new MongeElkan(new SmithWatermanGotoh()))
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	public static StringMetric needlemanWunch() {
		return new NeedlemanWunch();
	}

	public static StringMetric overlapCoefficient() {
		return new StringMetricBuilder().with(new OverlapCoefficient<String>())
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	public static StringMetric qGramsDistance() {
		return new StringMetricBuilder().with(new BlockDistance<String>())
				.tokenize(new QGramExtendedTokenizer(3))
				.build();
	}

	public static StringMetric simonWhite() {
		return new StringMetricBuilder().with(new SimonWhite<String>())
				.tokenize(new WhitespaceTokenizer())
				.tokenize(new QGramTokenizer(2))
				.build();
	}

	public static StringMetric smithWaterman() {
		return new SmithWaterman();
	}

	public static StringMetric smithWatermanGotoh() {
		return new SmithWatermanGotoh();
	}

	public static StringMetric smithWatermanGotohWindowedAffine() {
		return new SmithWatermanGotohWindowedAffine();
	}

	public static StringMetric soundex() {
		return new StringMetricBuilder().with(new JaroWinkler())
				.simplify(new SoundexSimplifier())
				.build();
	}
}
