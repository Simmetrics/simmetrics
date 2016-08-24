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

package org.simmetrics.tokenizers;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@SuppressWarnings("javadoc")
@RunWith(Enclosed.class)
public class QGramTest {

	public static final class QGram1 extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new Tokenizers.QGram(1);

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
							"1", "2", "3", "4", "5", "6", "7", "8", "9", "1",
							"2", "3", "4", "5", "6", "7", "8", "9"),
					new T("Héllo",
					// Diacritics are their own code point
							"H", "e", "́", "l", "l", "o"),
					// Linear-A surrogate pairs, pairs should be kept together
					new T("𐘀𐘁𐘂", "𐘀", "𐘁", "𐘂") };
		}
	}

	public static final class QGram2WithPadding extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new Tokenizers.QGramExtended(2, "@", "@");
		}

		@Override
		protected T[] getTests() {

			return new T[] { new T(""), new T("1", "@1", "1@"),
					new T("12", "@1", "12", "2@") };
		}
	}

	public static final class QGram2WithTwoSidedPadding extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new Tokenizers.QGramExtended(2, "L", "R");
		}

		@Override
		protected T[] getTests() {

			return new T[] { new T(""), new T("1", "L1", "1R"),
					new T("12", "L1", "12", "2R") };
		}
	}

	public static final class QGram2WithDefaultPadding extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new Tokenizers.QGramExtended(2);
		}

		@Override
		protected T[] getTests() {

			return new T[] {
					new T(""),
					new T("1", "#1", "1#"),
					new T("12", "#1", "12", "2#"),
					new T("123", "#1", "12", "23", "3#"),

					new T("123456789", "#1", "12", "23", "34", "45", "56",
							"67", "78", "89", "9#"),
					new T("123456789123456789", "#1", "12", "23", "34", "45",
							"56", "67", "78", "89", "91", "12", "23", "34",
							"45", "56", "67", "78", "89", "9#") };
		}
	}

	public static final class QGram2 extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new Tokenizers.QGram(2);
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
							"12", "23", "34", "45", "56", "67", "78", "89",
							"91", "12", "23", "34", "45", "56", "67", "78",
							"89"), 
					new T("Héllo",
					// Diacritics are their own code point
							"He", "é", "́l", "ll", "lo"),
					// Linear-A 
					// tokenizer should split on code points
					new T("𐘀𐘁𐘂","𐘀𐘁", "𐘁𐘂"),
					new T("𐘀𐘁", "𐘀𐘁") };
		}
	}

	public static final class QGram2WithFilter extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new Tokenizers.QGram(2, true);
		}

		@Override
		protected T[] getTests() {

			return new T[] { 
					new T(""), 
					new T("1"), 
					new T("12", "12"),
					// Linear-A 
					// tokenizer should filter on code points
					new T("𐘀𐘂", "𐘀𐘂"), 
					new T("𐘀") };
		}
	}

	public static final class QGram3WithDefaultPadding extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return new Tokenizers.QGramExtended(3);
		}

		@Override
		protected T[] getTests() {

			return new T[] {

					new T(""),
					new T("1", "##1", "#1#", "1##"),
					new T("12", "##1", "#12", "12#", "2##"),
					new T("123", "##1", "#12", "123", "23#", "3##"),

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
			return new Tokenizers.QGram(3);
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
					new T("Héllo",
					// Diacritics are their own code point
							"Hé", "él", "́ll", "llo"),
					// Linear-A surrogate pairs, pairs should be kept together
					new T("𐘀𐘁𐘂", "𐘀𐘁𐘂")

			};
		}
	}
}
