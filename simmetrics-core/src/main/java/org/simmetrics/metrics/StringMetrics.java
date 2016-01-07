/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * #L%
 */

package org.simmetrics.metrics;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.simmetrics.builders.StringMetricBuilder.with;
import static org.simmetrics.simplifiers.Simplifiers.chain;
import static org.simmetrics.tokenizers.Tokenizers.qGram;
import static org.simmetrics.tokenizers.Tokenizers.qGramWithPadding;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.util.List;
import java.util.Set;

import org.simmetrics.Metric;
import org.simmetrics.StringMetric;
import org.simmetrics.builders.StringMetricBuilder;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Soundex;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;

import com.google.common.collect.Multiset;

/**
 * Utility class for string metrics.
 * <p>
 * Consists of well known metrics and methods to create string metrics from
 * list- or set metrics. All metrics are setup with sensible defaults, to
 * customize metrics use {@link StringMetricBuilder}.
 * <p>
 * The available metrics are:
 * 
 * <ul>
 * <li>Block Distance
 * <li>Cosine Similarity
 * <li>Damerau Levenshtein
 * <li>Dice
 * <li>SimonWhite (Quantitative Dice)
 * <li>Euclidean Distance
 * <li>Jaccard
 * <li>Generalized Jaccard
 * <li>Jaro
 * <li>Jaro-Winkler
 * <li>LevenShtein
 * <li>Monge-Elkan
 * <li>NeedleMan Wunch
 * <li>Overlap Coefficient
 * <li>q-Grams Distance
 * <li>Smith-Waterman
 * <li>Smith-Waterman-Gotoh
 * <li>Soundex
 * </ul>
 * 
 * <p>
 * All methods return immutable objects provided the arguments are also
 * immutable.
 */
public final class StringMetrics {

	/**
	 * Returns a string metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link CosineSimilarity} metric.
	 * 
	 * @return a cosine similarity metric
	 */
	public static StringMetric cosineSimilarity() {
		return with(new CosineSimilarity<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link BlockDistance} metric.
	 * 
	 * @return a block distance metric
	 */
	public static StringMetric blockDistance() {
		return with(new BlockDistance<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a {@link DamerauLevenshtein} metric.
	 * 
	 * @return a damerau levenshtein metric
	 */
	public static StringMetric damerauLevenshtein() {
		return new DamerauLevenshtein();
	}

	/**
	 * Returns a string metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link Dice} metric.
	 * 
	 * @return a dice metric
	 */
	public static StringMetric dice() {
		return with(new Dice<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link EuclideanDistance} metric.
	 * 
	 * @return a Euclidean distance similarity metric
	 */
	public static StringMetric euclideanDistance() {
		return with(new EuclideanDistance<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link GeneralizedJaccard} metric.
	 * 
	 * @return a generalized jaccard index metric
	 */
	public static StringMetric generalizedJaccard() {
		return with(new GeneralizedJaccard<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns an string metric that uses the {@link Identity} metric.
	 * 
	 * @return an identity string metric
	 */
	public static StringMetric identity() {
		return new StringMetric() {
			
			private final Identity<String> metric = new Identity<>();
			
			@Override
			public float compare(String a, String b) {
				return metric.compare(a, b);
			}
			
			@Override
			public String toString() {
				return metric.toString();
			}
		};
	}

	/**
	 * Returns a string metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link Jaccard} metric.
	 * 
	 * @return a Jaccard similarity metric
	 */
	public static StringMetric jaccard() {
		return with(new Jaccard<String>()).tokenize(whitespace()).build();
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
	 * Returns a string metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link MongeElkan} metric with an internal {@link SmithWatermanGotoh}
	 * metric.
	 * 
	 * @return a Monge-Elkan metric
	 */
	public static StringMetric mongeElkan() {
		return with(new MongeElkan(new SmithWatermanGotoh())).tokenize(whitespace()).build();
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
	 * Returns a string metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link OverlapCoefficient} metric.
	 * 
	 * @return a overlap coefficient metric
	 */
	public static StringMetric overlapCoefficient() {
		return with(new OverlapCoefficient<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns a string metric that uses a
	 * {@link Tokenizers#qGramWithPadding(int)} for {@code q=3} and the
	 * {@link BlockDistance} metric.
	 * 
	 * @return a q-grams distance metric
	 */
	public static StringMetric qGramsDistance() {
		return with(new BlockDistance<String>()).tokenize(qGramWithPadding(3)).build();
	}

	/**
	 * Returns a string metric that uses a {@link Tokenizers#whitespace()}
	 * followed by a {@link Tokenizers#qGramWithPadding(int)} for {@code q=2}
	 * and the {@link SimonWhite} metric.
	 * 
	 * @return a Simon White metric
	 */
	public static StringMetric simonWhite() {
		return with(new SimonWhite<String>())
				.tokenize(whitespace())
				.tokenize(qGram(2))
				.build();
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
		return with(new JaroWinkler()).simplify(new Soundex()).build();
	}

	/**
	 * Returns a string metric that uses the {@link LongestCommonSubsequence} metric.
	 * 
	 * @return a longest common subsequence metric
	 */
	public static StringMetric longestCommonSubsequence(){
		return new LongestCommonSubsequence();
	}
	
	/**
	 * Returns a string metric that uses the {@link LongestCommonSubstring} metric.
	 * 
	 * @return a longest common substring metric
	 */
	public static StringMetric longestCommonSubstring(){
		return new LongestCommonSubstring();
	}
	
	/**
	 * Either constructs a new string metric or returns the original metric.
	 * 
	 * @param metric
	 *            a metric for strings
	 * 
	 * @return a string metric.
	 * 
	 * @deprecated Use {@link StringMetricBuilder} in favor of directly
	 *             constructing a metric.
	 */
	@Deprecated
	public static StringMetric create(Metric<String> metric) {
		if (metric instanceof StringMetric) {
			return (StringMetric) metric;
		}

		return new ForString(metric);
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
	 * 
	 * @throws NullPointerException
	 *             when either metric or simplifier are null
	 * 
	 * @see StringMetricBuilder
	 * 
	 * @deprecated Use {@link StringMetricBuilder} in favor of directly
	 *             constructing a metric.
	 */
	@Deprecated
	public static StringMetric create(Metric<String> metric,
			Simplifier simplifier) {
		if (metric instanceof ForString) {
			ForString forString = (ForString) metric;
			return new ForStringWithSimplifier(forString.getMetric(),
					simplifier);
		} else if (metric instanceof ForStringWithSimplifier) {
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
	 * @return a new composite list metric
	 * 
	 * @throws NullPointerException
	 *             when either metric, simplifier or tokenizer are null
	 * 
	 * @see StringMetricBuilder
	 * 
	 * @deprecated Use {@link StringMetricBuilder} in favor of directly
	 *             constructing a metric.
	 */
	@Deprecated
	public static StringMetric createForListMetric(Metric<List<String>> metric,
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
	 * @return a new composite string metric
	 * 
	 * @throws NullPointerException
	 *             when either metric or tokenizer are null
	 * 
	 * @see StringMetricBuilder
	 * 
	 * @deprecated Use {@link StringMetricBuilder} in favor of directly
	 *             constructing a metric.
	 */
	@Deprecated
	public static StringMetric createForListMetric(Metric<List<String>> metric,
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
	 * @return a new composite string metric
	 * 
	 * @throws NullPointerException
	 *             when either metric, simplifier or tokenizer are null
	 * 
	 * @see StringMetricBuilder
	 * 
	 * @deprecated Use {@link StringMetricBuilder} in favor of directly
	 *             constructing a metric.
	 */
	@Deprecated
	public static StringMetric createForSetMetric(Metric<Set<String>> metric,
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
	 * 
	 * @throws NullPointerException
	 *             when either metric or tokenizer are null
	 * 
	 * @see StringMetricBuilder
	 * 
	 * @deprecated Use {@link StringMetricBuilder} in favor of directly
	 *             constructing a metric.
	 */
	@Deprecated
	public static StringMetric createForSetMetric(Metric<Set<String>> metric,
			Tokenizer tokenizer) {
		return new ForSet(metric, tokenizer);
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
	 * @return a new composite string metric
	 * 
	 * @throws NullPointerException
	 *             when either metric, simplifier or tokenizer are null
	 * 
	 * @see StringMetricBuilder
	 * 
	 * @deprecated Use {@link StringMetricBuilder} in favor of directly
	 *             constructing a metric.
	 */
	@Deprecated
	public static StringMetric createForMultisetMetric(
			Metric<Multiset<String>> metric, Simplifier simplifier,
			Tokenizer tokenizer) {
		return new ForMultisetWithSimplifier(metric, simplifier, tokenizer);
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
	 * 
	 * @throws NullPointerException
	 *             when either metric or tokenizer are null
	 * 
	 * @see StringMetricBuilder
	 * 
	 * @deprecated Use {@link StringMetricBuilder} in favor of directly
	 *             constructing a metric.
	 */
	@Deprecated
	public static StringMetric createForMultisetMetric(
			Metric<Multiset<String>> metric, Tokenizer tokenizer) {
		return new ForMultiset(metric, tokenizer);
	}

	static final class ForList implements StringMetric {
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

	static final class ForListWithSimplifier implements StringMetric {
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

	static final class ForSet implements StringMetric {

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

	static final class ForSetWithSimplifier implements StringMetric {

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

	static final class ForMultiset implements StringMetric {

		private final Metric<Multiset<String>> metric;
		private final Tokenizer tokenizer;

		ForMultiset(Metric<Multiset<String>> metric, Tokenizer tokenizer) {
			checkNotNull(metric);
			checkNotNull(tokenizer);

			this.metric = metric;
			this.tokenizer = tokenizer;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(tokenizer.tokenizeToMultiset(a),
					tokenizer.tokenizeToMultiset(b));
		}

		Metric<Multiset<String>> getMetric() {
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

	static final class ForMultisetWithSimplifier implements StringMetric {

		private final Metric<Multiset<String>> metric;
		private final Simplifier simplifier;
		private final Tokenizer tokenizer;

		ForMultisetWithSimplifier(Metric<Multiset<String>> metric,
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
					tokenizer.tokenizeToMultiset(simplifier.simplify(a)),
					tokenizer.tokenizeToMultiset(simplifier.simplify(b)));
		}

		Metric<Multiset<String>> getMetric() {
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

	static final class ForString implements StringMetric {
		private final Metric<String> metric;

		ForString(Metric<String> metric) {
			this.metric = metric;
		}

		@Override
		public float compare(String a, String b) {
			return metric.compare(a, b);
		}

		@Override
		public String toString() {
			return metric.toString();
		}

		Metric<String> getMetric() {
			return metric;
		}

	}

	static final class ForStringWithSimplifier implements StringMetric {

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

	private StringMetrics() {
		// Utility class.
	}



}
