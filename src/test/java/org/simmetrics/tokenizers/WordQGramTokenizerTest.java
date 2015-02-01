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
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.tokenizers;

import org.simmetrics.tokenizers.QGram2Tokenizer;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.WordQGramTokenizer;

public class WordQGramTokenizerTest extends TokenizerTest {

	@Override
	protected Tokenizer getTokenizer() {
		return new WordQGramTokenizer(new QGram2Tokenizer());
	}

	@Override
	public T[] getTests() {

		return new T[] {
				new T("1234 5678 90 a", 
						"12", "23", "34", 
						"56","67", "78", 
						"90"),
				new T("1234 1234 90 a", 
						"12", "23", "34",
						"12", "23", "34", 
						"90"), };
	}
}
