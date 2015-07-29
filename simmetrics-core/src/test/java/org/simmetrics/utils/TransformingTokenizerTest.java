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
package org.simmetrics.utils;

import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizerTest;
import org.simmetrics.tokenizers.Whitespace;

import com.google.common.base.Function;

@SuppressWarnings("javadoc")
public class TransformingTokenizerTest extends TokenizerTest {

	private static Function<String, String> firstThreeLetters() {
		return new Function<String, String>() {

			@Override
			public String apply(String input) {
				if (input.length() < 3) {
					return input;
				}
				return input.substring(0, 3);
			}
			
			@Override
			public String toString() {
				return "FirstThreeLetters";
			}
		};
		
	}

	@Override
	protected Tokenizer getTokenizer() {
		return new TransformingTokenizer(new Whitespace(), firstThreeLetters());
	}

	@Override
	public T[] getTests() {
		return new T[] { new T("the mouse and cat or dog", "the", "mou", "and",
				"cat", "or", "dog") };
	}
}
