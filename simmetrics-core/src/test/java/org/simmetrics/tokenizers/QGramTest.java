/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package org.simmetrics.tokenizers;

import static org.simmetrics.tokenizers.Tokenizers.qGram;
import static org.simmetrics.tokenizers.Tokenizers.qGramWithFilter;
import static org.simmetrics.tokenizers.Tokenizers.qGramWithPadding;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
@RunWith(Enclosed.class)
public final class QGramTest {

	public static final class QGram1 extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return Tokenizers.qGram(1);

		}

		@Override
		protected T[] getTests() {

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

	public static final class QGram2WithPadding extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return qGramWithPadding(2,"@");
		}

		@Override
		protected T[] getTests() {

			return new T[] {
					new T(""),
					new T("1", "@1", "1@"),
					new T("12", "@1", "12", "2@")};
		}
	}
	
	public static final class QGram2WithTwoSidedPadding extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return qGramWithPadding(2,"L","R");
		}

		@Override
		protected T[] getTests() {

			return new T[] {
					new T(""),
					new T("1", "L1", "1R"),
					new T("12", "L1", "12", "2R")};
		}
	}
	
	public static final class QGram2WithDefaultPadding extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return qGramWithPadding(2);
		}

		@Override
		protected T[] getTests() {

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
	
	public static final class QGram2 extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return qGram(2);
		}

		@Override
		protected T[] getTests() {

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
	public static final class QGram2WithFilter extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return qGramWithFilter(2);
		}

		@Override
		protected T[] getTests() {

			return new T[] {
					new T(""),
					new T("1"),
					new T("12","12")};
		}
	}
	
	public static final class QGram3WithDefaultPadding extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return qGramWithPadding(3);
		}

		@Override
		protected T[] getTests() {

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
	
	public static final class QGram3 extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return qGram(3);
		}

		@Override
		protected T[] getTests() {

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
