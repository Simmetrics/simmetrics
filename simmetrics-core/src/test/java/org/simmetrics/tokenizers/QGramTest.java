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

@SuppressWarnings("javadoc")
public final class QGramTest {

	public static final class QGram1TokenizerTest extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new QGram(1);

		}

		@Override
		public T[] getTests() {

			return new T[] {
					new T(""),
					new T("1", "1"),
					new T("12", "1", "2"),

					new T("123456789",
					// Expected output
							"1", "2", "3", "4", "5", "6", "7", "8", "9"),
					new T("123456789123456789",
							// Expected output
							"1", "2", "3", "4", "5", "6", "7", "8", "9", "1", "2",
							"3", "4", "5", "6", "7", "8", "9") };
		}
	}
	
	public static final class QGram2ExtendedTokenizerTest extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new QGramExtended(2);
		}

		@Override
		public T[] getTests() {

			return new T[] {
					new T(""),
					new T("1", "#1", "1#"),
					new T("12", "#1", "12", "2#"),
					new T("123", "#1", "12", "23","3#"),
					
					new T("123456789", 
							"#1", "12", "23", "34", "45", "56", "67", "78", "89","9#"),
					new T("123456789123456789", 
							"#1", "12", "23", "34", "45", "56", "67", "78", "89",
							"91", "12", "23", "34", "45", "56", "67", "78", "89","9#"
							) };
		}
	}
	
	public static final class QGram2TokenizerTest extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new QGram(2);
		}

		@Override
		public T[] getTests() {

			return new T[] {
					new T(""),
					new T("1", "1"),
					new T("12", "12"),
					new T("123456789",
					// Expected output
							"12", "23", "34", "45", "56", "67", "78", "89"),
					new T("123456789123456789",
							// Expected output
							"12", "23", "34", "45", "56", "67", "78", "89", "91",
							"12", "23", "34", "45", "56", "67", "78", "89") };
		}
	}
	
	public static final class QGram3ExtendedTokenizerTest extends TokenizerTest {

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
	
	public static final class QGram3TokenizerTest extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new QGram(3);
		}

		@Override
		public T[] getTests() {

			return new T[] {
					new T(""),
					new T("1", "1"),
					new T("12", "12"),
					new T("123", "123"),
					new T("12345678", "123", "234", "345", "456", "567", "678"),
					new T("123123", "123", "231", "312", "123"),

			};
		}
	}
}
