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
package org.simmetrics.tokenizers;

import org.simmetrics.tokenizers.Tokenizer;

public class QGram3ExtendedTokenizerTest extends TokenizerTest {

	@Override
	protected Tokenizer getTokenizer() {
		return new QGramExtended(3);
	}

	@Override
	public T[] getTests() {

		return new T[] {
				
				new T(""),
				new T("1", "##1", "#1#", "1##"),
				new T("12", "##1", "#12","12#", "2##"),
				new T("123", "##1", "#12","123", "23#","3##"),
				
				new T("12345678", "##1", "#12", "123", "234", "345", "456",
						"567", "678", "78#", "8##"),
				new T("123123", "##1", "#12", "123", "231", "312", "123",
						"23#", "3##"),

		};
	}
}
