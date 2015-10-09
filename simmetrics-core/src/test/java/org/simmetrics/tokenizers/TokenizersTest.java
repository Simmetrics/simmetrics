/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.simmetrics.tokenizers;

import static com.google.common.base.Predicates.in;
import static com.google.common.base.Predicates.not;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertSame;
import static org.simmetrics.tokenizers.Tokenizers.chain;
import static org.simmetrics.tokenizers.Tokenizers.filter;
import static org.simmetrics.tokenizers.Tokenizers.pattern;
import static org.simmetrics.tokenizers.Tokenizers.qGram;
import static org.simmetrics.tokenizers.Tokenizers.transform;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

@SuppressWarnings({ "javadoc", "static-method" })
@RunWith(Enclosed.class)
public final class TokenizersTest {

	public static final class FilteringFilteringTokenizerTest extends
			TokenizerTest {

		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the MOUSE and CAT or DOG are cool", "are", "cool"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(filter(pattern("\\s+"), theAndOr()),
					mouseCatDogUpperCase());
		}

	}

	public static final class FilteringTokenizerTest extends TokenizerTest {

		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "mouse", "cat", "dog"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return Tokenizers.filter(pattern("\\s+"), theAndOr());
		}

	}

	public static final class FilteringTransformingTokenizerTest extends
			TokenizerTest {
		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "MOUSE", "CAT", "DOG"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return Tokenizers.transform(filter(pattern("\\s+"), theAndOr()),
					toUpperCase());
		}
	}

	public static final class FilteringTransformingTransformingTokenizerTest
			extends TokenizerTest {
		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "ESUOM", "TAC", "GOD"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return transform(
					transform(filter(pattern("\\s+"), theAndOr()),
							toUpperCase()), reverseCapitalized());
		}
	}

	public static final class FilterTransformingFilteringTokenizerTest extends
			TokenizerTest {
		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog are cool", "ARE", "COOL"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(
					transform(filter(pattern("\\s+"), theAndOr()),
							toUpperCase()), mouseCatDogUpperCase());
		}
	}

	public static final class FilterTransformingFilteringTransformingTokenizerTest
			extends TokenizerTest {
		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog are cool", "ERA", "LOOC"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return transform(
					filter(transform(filter(pattern("\\s+"), theAndOr()),
							toUpperCase()), mouseCatDogUpperCase()),
					reverseCapitalized());
		}
	}

	@RunWith(Enclosed.class)
	public static final class TokenizerChainTest {

		public static final class WithChain extends TokenizerTest {

			@Override
			protected T[] getTests() {
				return new T[] {
						new T("Mouse", "Mo", "ou", "ou", "us", "ou", "us",
								"us", "se"), new T("") };
			}

			@Override
			protected Tokenizer getTokenizer() {
				return chain(qGram(5), chain(qGram(4), qGram(3)), qGram(2));
			}
		}

		public static final class WithEmpty extends TokenizerTest {

			@Override
			protected T[] getTests() {
				return new T[] {
						new T("the mouse and cat or dog",
								"the mouse and cat or dog"), new T("", "") };
			}

			@Override
			protected Tokenizer getTokenizer() {
				return chain(new ArrayList<Tokenizer>());
			}
		}

		public static final class WithTwo extends TokenizerTest {

			@Override
			protected T[] getTests() {
				return new T[] {
						new T("the mouse and cat or dog", "the", "mou", "ous",
								"use", "and", "cat", "or", "dog"), new T("") };
			}

			@Override
			protected Tokenizer getTokenizer() {
				return chain(whitespace(), qGram(3));
			}
		}

		public static final class WithIllegalArguments {

			@Test(expected = IllegalArgumentException.class)
			public void shouldThrowForListContainingNull() {
				Tokenizers.chain(Arrays.asList(whitespace(), (Tokenizer) null));
			}

			@Test(expected = IllegalArgumentException.class)
			public void shouldThrowForNull() {
				Tokenizers.chain((Tokenizer) null);
			}

			@Test(expected = IllegalArgumentException.class)
			public void shouldThrowForNullInVarArg() {
				Tokenizers.chain(whitespace(), null, whitespace());
			}
		}

		public static final class WithSingleArguments {

			@Test
			public void shouldBeSameForSingle() {
				Tokenizer t = whitespace();
				assertSame(t, chain(t));
			}

			@Test
			public void shouldBeSameForSingletonList() {
				Tokenizer t = whitespace();
				assertSame(t, Tokenizers.chain(Collections.singletonList(t)));
			}
		}
	}

	public static final class TransformingFilteringFilteringTokenizerTest
			extends TokenizerTest {
		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog are cool", "ARE", "COOL"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(
					filter(transform(pattern("\\s+"), toUpperCase()),
							mouseCatDogUpperCase()), theAndOrUpperCase());
		}
	}

	public static final class TransformingFilteringTokenizerTest extends
			TokenizerTest {
		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "THE", "AND", "OR"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(transform(pattern("\\s+"), toUpperCase()),
					mouseCatDogUpperCase());
		}
	}

	public static final class TransformingFilteringTransformingFilteringTokenizerTest
			extends TokenizerTest {
		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the Mouse and Cat or Dog", "ESUOM", "TAC", "GOD"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(
					transform(
							filter(transform(pattern("\\s+"),
									reverseCapitalized()), theAndOr()),
							toUpperCase()), mouseCatDogUpperCase());
		}
	}

	public static final class TransformingFilteringTransformingTokenizerTest
			extends TokenizerTest {
		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the Mouse and Cat or Dog", "ESUOM", "TAC", "GOD"),
					new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return transform(
					filter(transform(pattern("\\s+"), reverseCapitalized()),
							theAndOr()), toUpperCase());
		}
	}

	public static final class TransformingTokenizerTest extends TokenizerTest {

		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "THE", "MOUSE", "AND",
							"CAT", "OR", "DOG"), new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return Tokenizers.transform(pattern("\\s+"), toUpperCase());
		}
	}

	public static final class TransformingTransformingTokenizerTest extends
			TokenizerTest {

		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the Mouse and Cat or Dog", "THE", "ESUOM", "AND",
							"TAC", "OR", "GOD"), new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return transform(transform(pattern("\\s+"), reverseCapitalized()),
					toUpperCase());
		}
	}

	public static final class TransformingTransformingTransformingTokenizerTest
			extends TokenizerTest {

		@Override
		protected T[] getTests() {
			return new T[] {
					new T("the Mouse and Cat or Dog", "EHT", "MOUSE", "DNA",
							"CAT", "RO", "DOG"), new T("", "") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return transform(
					transform(transform(pattern("\\s+"), reverseCapitalized()),
							toUpperCase()), reverseCapitalized());
		}
	}

	public static final class Whitespace extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return Tokenizers.whitespace();
		}

		@Override
		protected T[] getTests() {

			return new T[] { new T(""), new T(" "), new T(" A", "A"),
					new T("A B C", "A", "B", "C"),
					new T("A   B  C", "A", "B", "C"), new T("A\nB", "A", "B"),
					new T("A\tB", "A", "B"), new T("A\t\nB", "A", "B"), };
		}
	}

	public static final class Pattern extends TokenizerTest {

		@Override
		protected Tokenizer getTokenizer() {
			return Tokenizers.pattern(",");
		}

		@Override
		protected T[] getTests() {

			return new T[] { new T("", ""), new T(" ", " "),
					new T(",", "", ""), new T(",,", "", "", ""),
					new T("A,B,C", "A", "B", "C"),
					new T("A,,B,,C", "A", "", "B", "", "C"), };
		}
	}

	static Predicate<String> mouseCatDogUpperCase() {
		return not(in(asList("CAT", "MOUSE", "DOG")));
	}

	static Function<String, String> reverseCapitalized() {
		return new Function<String, String>() {

			@Override
			public String apply(String input) {
				if (input.isEmpty() || !Character.isUpperCase(input.charAt(0))) {
					return input;
				}

				return new StringBuilder(input).reverse().toString();
			}

			@Override
			public String toString() {
				return "reverseCapitalized";
			}
		};
	}

	static Predicate<String> theAndOr() {
		return not(in(asList("the", "and", "or")));
	}

	static Predicate<String> theAndOrUpperCase() {
		return not(in(asList("THE", "AND", "OR")));
	}

	static Function<String, String> toUpperCase() {
		return new Function<String, String>() {

			@Override
			public String apply(String input) {
				return input.toUpperCase();
			}

			@Override
			public String toString() {
				return "toUpperCase";
			}
		};

	}

}
