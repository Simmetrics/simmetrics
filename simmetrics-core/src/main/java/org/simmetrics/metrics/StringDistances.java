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

import static org.simmetrics.builders.StringDistanceBuilder.with;
import static org.simmetrics.tokenizers.Tokenizers.qGram;
import static org.simmetrics.tokenizers.Tokenizers.qGramWithPadding;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import org.simmetrics.StringDistance;
import org.simmetrics.builders.StringDistanceBuilder;
import org.simmetrics.tokenizers.Tokenizers;

/**
 * Utility class for string distance metrics.
 * <p>
 * Consists of well known metrics. All distance metrics are setup with sensible
 * defaults, to customize metrics use {@link StringDistanceBuilder}.
 * <p>
 * The created distance metrics are immutable and thread-safe.
 */
public final class StringDistances {
	
	/**
	 * Returns a distance metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link CosineSimilarity} metric.
	 * 
	 * @return a cosine distance metric
	 */
	public static StringDistance cosineSimilarity() {
		return with(new CosineSimilarity<String>()).tokenize(whitespace()).build();
	}
	
	/**
	 * Returns a string distance metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link BlockDistance} metric.
	 * 
	 * @return a block distance metric
	 */
	public static StringDistance blockDistance() {
		return with(new BlockDistance<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns a string distance metric that uses a {@link DamerauLevenshtein} metric.
	 * 
	 * @return a damerau levenshtein metric
	 */
	public static StringDistance damerauLevenshtein() {
		return new DamerauLevenshtein();
	}

	/**
	 * Returns a string distance metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link Dice} metric.
	 * 
	 * @return a dice metric
	 */
	public static StringDistance dice() {
		return with(new Dice<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns a string distance metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link EuclideanDistance} metric.
	 * 
	 * @return a Euclidean distance similarity metric
	 */
	public static StringDistance euclideanDistance() {
		return with(new EuclideanDistance<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns a string distance metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link GeneralizedJaccard} metric.
	 * 
	 * @return a generalized jaccard index metric
	 */
	public static StringDistance generalizedJaccard() {
		return with(new GeneralizedJaccard<String>()).tokenize(whitespace()).build();
	}

	/**
	 * Returns an string distance metric that uses the {@link Identity} metric.
	 * 
	 * @return an identity distance metric
	 */
	public static StringDistance identity() {
		return new StringDistance() {
			
			private final Identity<String> metric = new Identity<>();
			
			@Override
			public float distance(String a, String b) {
				return metric.distance(a, b);
			}
			
			@Override
			public String toString() {
				return metric.toString();
			}
		};
	}

	/**
	 * Returns a string distance metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link Jaccard} metric.
	 * 
	 * @return a Jaccard distance metric
	 */
	public static StringDistance jaccard() {
		return with(new Jaccard<String>()).tokenize(whitespace()).build();
	}
	
	/**
	 * Returns a string distance metric that uses the {@link Jaro} metric.
	 * 
	 * @return a Jaro metric
	 */
	public static StringDistance jaro() {
		return new Jaro();
	}

	/**
	 * Returns a string distance metric that uses the {@link JaroWinkler} metric.
	 * 
	 * @return a Jaro-Winkler metric
	 */
	public static StringDistance jaroWinkler() {
		return new JaroWinkler();
	}
	/**
	 * Returns a string distance metric that uses the {@link Levenshtein} metric.
	 * 
	 * @return a Levenshtein metric
	 */
	public static StringDistance levenshtein() {
		return new Levenshtein();
	}
	
	/**
	 * Returns a string distance metric that uses a {@link Tokenizers#whitespace()} and
	 * the {@link OverlapCoefficient} distance metric.
	 * 
	 * @return a overlap coefficient metric
	 */
	public static StringDistance overlapCoefficient() {
		return with(new OverlapCoefficient<String>()).tokenize(whitespace()).build();
	}
	
	/**
	 * Returns a string distance metric that uses a
	 * {@link Tokenizers#qGramWithPadding(int)} for {@code q=3} and the
	 * {@link BlockDistance} metric.
	 * 
	 * @return a q-grams distance metric
	 */
	public static StringDistance qGramsDistance() {
		return with(new BlockDistance<String>()).tokenize(qGramWithPadding(3)).build();
	}

	/**
	 * Returns a string distance metric that uses a {@link Tokenizers#whitespace()}
	 * followed by a {@link Tokenizers#qGramWithPadding(int)} for {@code q=2}
	 * and the {@link SimonWhite} metric.
	 * 
	 * @return a Simon White metric
	 */
	public static StringDistance simonWhite() {
		return with(new SimonWhite<String>())
				.tokenize(whitespace())
				.tokenize(qGram(2))
				.build();
	}
	/**
	 * Returns a string distance metric that uses the {@link HammingDistance} metric.
	 * 
	 * @return a Hamming-distance metric
	 */
	public static StringDistance hammingDistance(){
		return HammingDistance.forString();
	}

	/**
	 * Returns a string distance metric that uses the {@link LongestCommonSubsequence} metric.
	 * 
	 * @return a longest common subsequence metric
	 */
	public static StringDistance longestCommonSubsequence(){
		return new LongestCommonSubsequence();
	}
	
	/**
	 * Returns a string distance metric that uses the {@link LongestCommonSubstring} metric.
	 * 
	 * @return a longest common substring metric
	 */
	public static StringDistance longestCommonSubstring(){
		return new LongestCommonSubstring();
	}
}
