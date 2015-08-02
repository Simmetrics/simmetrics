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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.simmetrics.simplifiers.Simplifiers.chain;
import static org.simmetrics.tokenizers.Tokenizers.chain;

import java.util.List;
import java.util.Set;

import org.simmetrics.metrics.BlockDistance;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.metrics.DamerauLevenshtein;
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
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Soundex;
import org.simmetrics.tokenizers.QGramExtended;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Tokenizer;
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
 * <li>Damerau Levenshtein
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

	private static final class ForList implements StringMetric {
		private final Metric<List<String>> metric;
		private final Tokenizer tokenizer;

		ForList(Metric<List<String>> metric, Tokenizer tokenizer) {

			checkNotNull(metric);
			checkNotNull(tokenizer);

			this.metric = metric;
			this.tokenizer = tokenizer;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(tokenizer.tokenizeToList(a),
					tokenizer.tokenizeToList(b));
		}

		Metric<List<String>> getMetric() {
			return metric;
		}

		Tokenizer getTokenizer() {
			return tokenizer;
		}

		@Override
		public String toString() {
			return metric + " [" + tokenizer + "]";
		}
	}

	private static final class ForListWithSimplifier implements StringMetric {
		private final Metric<List<String>> metric;
		private final Simplifier simplifier;
		private final Tokenizer tokenizer;

		ForListWithSimplifier(Metric<List<String>> metric,
				Simplifier simplifier, Tokenizer tokenizer) {

			checkNotNull(metric);
			checkNotNull(simplifier);
			checkNotNull(tokenizer);

			this.metric = metric;
			this.simplifier = simplifier;
			this.tokenizer = tokenizer;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(
					tokenizer.tokenizeToList(simplifier.simplify(a)),
					tokenizer.tokenizeToList(simplifier.simplify(b)));
		}

		Metric<List<String>> getMetric() {
			return metric;
		}

		Simplifier getSimplifier() {
			return simplifier;
		}

		Tokenizer getTokenizer() {
			return tokenizer;
		}

		@Override
		public String toString() {
			return metric + " [" + simplifier + " -> " + tokenizer + "]";
		}
	}

	private static final class ForSet implements StringMetric {

		private final Metric<Set<String>> metric;
		private final Tokenizer tokenizer;

		ForSet(Metric<Set<String>> metric, Tokenizer tokenizer) {
			checkNotNull(metric);
			checkNotNull(tokenizer);

			this.metric = metric;
			this.tokenizer = tokenizer;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(tokenizer.tokenizeToSet(a),
					tokenizer.tokenizeToSet(b));
		}

		Metric<Set<String>> getMetric() {
			return metric;
		}

		Tokenizer getTokenizer() {
			return tokenizer;
		}

		@Override
		public String toString() {
			return metric + " [" + tokenizer + "]";
		}

	}

	private static final class ForSetWithSimplifier implements StringMetric {

		private final Metric<Set<String>> metric;
		private final Simplifier simplifier;
		private final Tokenizer tokenizer;

		ForSetWithSimplifier(Metric<Set<String>> metric, Simplifier simplifier,
				Tokenizer tokenizer) {
			checkNotNull(metric);
			checkNotNull(simplifier);
			checkNotNull(tokenizer);

			this.metric = metric;
			this.simplifier = simplifier;
			this.tokenizer = tokenizer;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(
					tokenizer.tokenizeToSet(simplifier.simplify(a)),
					tokenizer.tokenizeToSet(simplifier.simplify(b)));
		}

		Metric<Set<String>> getMetric() {
			return metric;
		}

		Simplifier getSimplifier() {
			return simplifier;
		}

		Tokenizer getTokenizer() {
			return tokenizer;
		}

		@Override
		public String toString() {
			return metric + " [" + simplifier + " -> " + tokenizer + "]";
		}

	}

	private static final class ForStringWithSimplifier implements StringMetric {

		private final Metric<String> metric;

		private final Simplifier simplifier;

		ForStringWithSimplifier(Metric<String> metric, Simplifier simplifier) {
			checkNotNull(metric);
			checkNotNull(simplifier);

			this.metric = metric;
			this.simplifier = simplifier;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(simplifier.simplify(a),
					simplifier.simplify(b));
		}

		Metric<String> getMetric() {
			return metric;
		}

		Simplifier getSimplifier() {
			return simplifier;
		}

		@Override
		public String toString() {
			return metric + " [" + simplifier + "]";
		}

	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link BlockDistance} metric.
	 * 
	 * @return a block distance metric
	 */
	public static StringMetric blockDistance() {
		return createForListMetric(new BlockDistance<String>(),
				new Whitespace());
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
	public static float[] compare(StringMetric metric, final String c,
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
	public static float[] compare(StringMetric metric, final String c,
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
	 *             when a and b are of a different length
	 * @return a list of similarity values for each pair a[n] b[n].
	 */
	public static float[] compareArrays(StringMetric metric, final String[] a,
			final String[] b) {
		checkArgument(a.length == b.length, "arrays must have the same length");

		final float[] results = new float[a.length];

		for (int i = 0; i < a.length; i++) {
			results[i] = metric.compare(a[i], b[i]);
		}
		return results;
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link CosineSimilarity} metric.
	 * 
	 * @return a cosine similarity metric
	 */
	public static StringMetric cosineSimilarity() {
		return createForSetMetric(new CosineSimilarity<String>(),
				new Whitespace());
	}

	/**
	 * Constructs a new composite string metric. The simplifier will be applied
	 * before the metric compares the strings.
	 * 
	 * @param metric
	 *            a list metric
	 * @param simplifier
	 *            a simplifier
	 * @return a new composite string metric
	 */
	static StringMetric create(Metric<String> metric, Simplifier simplifier) {
		if (metric instanceof ForStringWithSimplifier) {
			ForStringWithSimplifier fsws = (ForStringWithSimplifier) metric;
			return new ForStringWithSimplifier(fsws.getMetric(), chain(
					simplifier, fsws.getSimplifier()));
		} else if (metric instanceof ForList) {
			ForList fl = (ForList) metric;
			return createForListMetric(fl.getMetric(), simplifier,
					fl.getTokenizer());
		} else if (metric instanceof ForListWithSimplifier) {
			ForListWithSimplifier fl = (ForListWithSimplifier) metric;
			return createForListMetric(fl.getMetric(),
					chain(simplifier, fl.getSimplifier()), fl.getTokenizer());
		} else if (metric instanceof ForSet) {
			ForSet fl = (ForSet) metric;
			return createForSetMetric(fl.getMetric(), simplifier,
					fl.getTokenizer());
		} else if (metric instanceof ForSetWithSimplifier) {
			ForSetWithSimplifier fl = (ForSetWithSimplifier) metric;
			return createForSetMetric(fl.getMetric(),
					chain(simplifier, fl.getSimplifier()), fl.getTokenizer());
		}

		return new ForStringWithSimplifier(metric, simplifier);
	}

	/**
	 * Creates a new composite string metric.The tokenizer is used to tokenize
	 * the simplified strings. The list metric compares the the tokens.
	 * 
	 * @param metric
	 *            a list metric
	 * @param simplifier
	 *            a simplifier
	 * @param tokenizer
	 *            a tokenizer
	 * @return a new composite list metric.
	 */
	static StringMetric createForListMetric(Metric<List<String>> metric,
			Simplifier simplifier, Tokenizer tokenizer) {
		return new ForListWithSimplifier(metric, simplifier, tokenizer);
	}

	/**
	 * Creates a new composite string metric. The tokenizer is used to tokenize
	 * the strings. The list metric compares the the tokens.
	 * 
	 * @param metric
	 *            a list metric
	 * @param tokenizer
	 *            a tokenizer
	 * @return a new composite string metric.
	 */
	static StringMetric createForListMetric(Metric<List<String>> metric,
			Tokenizer tokenizer) {
		return new ForList(metric, tokenizer);
	}

	/**
	 * Creates a new composite string metric.The tokenizer is used to tokenize
	 * the simplified strings. The set metric compares the the tokens.
	 * 
	 * @param metric
	 *            a list metric
	 * @param simplifier
	 *            a simplifier
	 * @param tokenizer
	 *            a tokenizer
	 * @return a new composite string metric.
	 */
	static StringMetric createForSetMetric(Metric<Set<String>> metric,
			Simplifier simplifier, Tokenizer tokenizer) {
		return new ForSetWithSimplifier(metric, simplifier, tokenizer);
	}

	/**
	 * Creates a new composite string metric. The tokenizer is used to tokenize
	 * the strings. The set metric compares the the tokens.
	 * 
	 * @param metric
	 *            a set metric
	 * 
	 * @param tokenizer
	 *            a tokenizer
	 * @return a new composite string metric
	 */
	static StringMetric createForSetMetric(Metric<Set<String>> metric,
			Tokenizer tokenizer) {
		return new ForSet(metric, tokenizer);
	}

	/**
	 * Returns a string metric that uses a {@link DamerauLevenshtein} metric.
	 * 
	 * @return a dice similarity metric
	 */
	public static StringMetric damerauLevenshtein() {
		return new DamerauLevenshtein();
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link DiceSimilarity} metric.
	 * 
	 * @return a dice similarity metric
	 */
	public static StringMetric diceSimilarity() {
		return createForSetMetric(new DiceSimilarity<String>(),
				new Whitespace());
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link EuclideanDistance} metric.
	 * 
	 * @return a Euclidean distance similarity metric
	 */
	public static StringMetric euclideanDistance() {
		return createForListMetric(new EuclideanDistance<String>(),
				new Whitespace());
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link JaccardSimilarity} metric.
	 * 
	 * @return a Jaccard similarity metric
	 */
	public static StringMetric jaccardSimilarity() {
		return createForSetMetric(new JaccardSimilarity<String>(),
				new Whitespace());
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
		return createForListMetric(new MatchingCoefficient<String>(),
				new Whitespace());
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} and the
	 * {@link MongeElkan} metric with an internal {@link SmithWatermanGotoh}
	 * metric.
	 * 
	 * @return a Monge-Elkan metric
	 */
	public static StringMetric mongeElkan() {
		return createForListMetric(new MongeElkan(new SmithWatermanGotoh()),
				new Whitespace());
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
		return createForSetMetric(new OverlapCoefficient<String>(),
				new Whitespace());
	}

	/**
	 * Returns a string metric that uses a {@link QGramExtended} for {@code q=3}
	 * and the {@link BlockDistance} metric.
	 * 
	 * @return a q-grams distance metric
	 */
	public static StringMetric qGramsDistance() {
		return createForListMetric(new BlockDistance<String>(),
				new QGramExtended(3));
	}

	/**
	 * Returns a string metric that uses a {@link Whitespace} followed by a
	 * {@link QGramExtended} for {@code q=2} and the {@link SimonWhite} metric.
	 * 
	 * @return a Simon White metric
	 */
	public static StringMetric simonWhite() {
		return createForListMetric(new SimonWhite<String>(),
				chain(new Whitespace(), new QGram(2)));
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
		return create(new JaroWinkler(), new Soundex());
	}

	private StringMetrics() {
		// Utility class.
	}

}
