/*
 * #%L
 * Simmetrics Examples
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package org.simmetrics.example;

import static com.google.common.base.Predicates.in;
import static org.simmetrics.StringMetricBuilder.with;

import java.util.Set;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.metrics.Levenshtein;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;

/**
 * The {@link StringMetricBuilder} can be used to compose metrics.
 * 
 * A metric is used to measure the similarity between strings. Metrics can work
 * on strings, lists or sets tokens. To compare strings with a metric that works
 * on a collection of tokens a tokenizer is required.
 * 
 * By adding simplifiers, tokenizers and filters and transform the effectiveness
 * of a metric can be improved. The exact combination is generally domain
 * specific. This builder supports these domain specific customizations. Some
 * example usages are shown below
 * 
 */
@SuppressWarnings("javadoc")
public final class StringMetricBuilderExample {

	/**
	 * By simplifying the input before applying the metric similar strings can
	 * be identified.
	 * 
	 * Any class implementing {@link Simplifier} can be used as a simplifier.
	 */
	public static float example01() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "A Quirky Thing It is. This is a Sentence.";

		StringMetric metric = 
				with(new Levenshtein())
				.simplify(Simplifiers.toLowerCase())
				.build();

		return metric.compare(str1, str2); // 1.0000
	}

	/**
	 * Simplifiers can be chained.
	 */
	public static float example02() {

		String str1 = "A *quirky* thing [it] is! This is a sentence?!";
		String str2 = "A Quirky Thing It is. This is a Sentence.";

		StringMetric metric = 
				with(new Levenshtein())
				.simplify(Simplifiers.toLowerCase())
				.simplify(Simplifiers.removeNonWord())
				.build();

		return metric.compare(str1, str2); // 1.0000
	}

	/**
	 * To use list and set metrics, a string has to be tokenized.
	 * 
	 * Any class implementing {@link Tokenizer} can be used as a tokenizer.
	 */
	public static float example03() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similair; a quirky thing it is.";

		StringMetric metric = 
				with(new CosineSimilarity<String>())
				.tokenize(Tokenizers.whitespace())
				.build();

		return metric.compare(str1, str2); // 0.7777
	}

	/**
	 * Tokenizers can also be chained.
	 */
	public static float example04() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similair; a quirky thing it is.";

		StringMetric metric = 
				with(new CosineSimilarity<String>())
				.tokenize(Tokenizers.whitespace())
				.tokenize(Tokenizers.qGram(3))
				.build();

		return metric.compare(str1, str2); // 0.8131
	}

	/**
	 * Tokens can be filtered to avoid comparing strings on common but otherwise
	 * low information words.
	 * 
	 * Filtering tokens can be done anywhere in the tokenization step. Filtered
	 * tokens are not passed onto the metric. This can be used to reduce false
	 * positives.
	 * 
	 * Filtering can be done repeatedly after a tokenization step.
	 * 
	 * Any class implementing {@link Predicate} can be used to filter.
	 */
	public static float example05() {
		Set<String> commonWords = Sets.newHashSet("it", "is");
		Set<String> otherCommonWords = Sets.newHashSet("a");

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similair; a quirky thing it is.";

		StringMetric metric = 
				with(new CosineSimilarity<String>())
				.simplify(Simplifiers.toLowerCase())
				.simplify(Simplifiers.removeNonWord())
				.tokenize(Tokenizers.whitespace())
				.filter(Predicates.not(in(commonWords)))
				.filter(Predicates.not(in(otherCommonWords)))
				.tokenize(Tokenizers.qGram(3))
				.build();

		return metric.compare(str1, str2); // 0.68061393
	}

	/**
	 * Tokenization and simplification can be expensive operations. To avoid
	 * executing expensive operations repeatedly, intermediate results can be
	 * cached. Note that Caching itself also has a non-trivial cost. Base your
	 * decision on metrics!
	 */
	public static float example06() {

		String str1 = "A quirky thing it is. This is a sentence.";
		String str2 = "This sentence is similair; a quirky thing it is.";

		StringMetric metric = 
				with(new CosineSimilarity<String>())
				.simplify(Simplifiers.toLowerCase())
				.simplify(Simplifiers.removeNonWord())
				.simplifierCache()
				.tokenize(Tokenizers.qGram(3))
				.tokenizerCache()
				.build();

		return metric.compare(str1, str2); // 0.8131
	}

}
