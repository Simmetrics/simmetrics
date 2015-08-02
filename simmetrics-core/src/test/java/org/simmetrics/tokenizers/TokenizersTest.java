package org.simmetrics.tokenizers;

import static com.google.common.base.Predicates.in;
import static com.google.common.base.Predicates.not;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertSame;
import static org.simmetrics.tokenizers.Tokenizers.chain;
import static org.simmetrics.tokenizers.Tokenizers.filter;
import static org.simmetrics.tokenizers.Tokenizers.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;
import org.simmetrics.tokenizers.Whitespace;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

@SuppressWarnings("javadoc")
public final class TokenizersTest {

	public static final class FilteringFilteringTokenizerTest extends
			TokenizerTest {

		@Override
		public T[] getTests() {
			return new T[] {
					new T("the MOUSE and CAT or DOG are cool", "are", "cool"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(filter(new Whitespace(), theAndOr()),
					mouseCatDogUpperCase());
		}

	}

	public static final class FilteringTokenizerTest extends TokenizerTest {

		@Override
		public T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "mouse", "cat", "dog"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return Tokenizers.filter(new Whitespace(), theAndOr());
		}

	}

	public static final class FilteringTransformingTokenizerTest extends
			TokenizerTest {
		@Override
		public T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "MOUSE", "CAT", "DOG"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return Tokenizers.transform(filter(new Whitespace(), theAndOr()),
					toUpperCase());
		}
	}

	public static final class FilteringTransformingTransformingTokenizerTest
			extends TokenizerTest {
		@Override
		public T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "ESUOM", "TAC", "GOD"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return transform(
					transform(filter(new Whitespace(), theAndOr()),
							toUpperCase()), reverseCapitalized());
		}
	}

	public static final class FilterTransformingFilteringTokenizerTest extends
			TokenizerTest {
		@Override
		public T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog are cool", "ARE", "COOL"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(
					transform(filter(new Whitespace(), theAndOr()),
							toUpperCase()), mouseCatDogUpperCase());
		}
	}

	public static final class FilterTransformingFilteringTransformingTokenizerTest
			extends TokenizerTest {
		@Override
		public T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog are cool", "ERA", "LOOC"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return transform(
					filter(transform(filter(new Whitespace(), theAndOr()),
							toUpperCase()), mouseCatDogUpperCase()),
					reverseCapitalized());
		}
	}

	public static final class TokennizerChainTest {

		public static final class WithChain extends TokenizerTest {

			@Override
			public T[] getTests() {
				return new T[] {
						new T("Mouse", "Mo", "ou", "ou", "us", "ou", "us",
								"us", "se"), new T("") };
			}

			@Override
			protected Tokenizer getTokenizer() {
				return chain(new QGram(5), chain(new QGram(4), new QGram(3)),
						new QGram(2));
			}
		}

		public static final class WithEmpty extends TokenizerTest {

			@Override
			public T[] getTests() {
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
			public T[] getTests() {
				return new T[] {
						new T("the mouse and cat or dog", "the", "mou", "ous",
								"use", "and", "cat", "or", "dog"), new T("") };
			}

			@Override
			protected Tokenizer getTokenizer() {
				return chain(new Whitespace(), new QGram(3));
			}
		}

		@Test(expected = IllegalArgumentException.class)
		public void chainWithListContainingNull() {
			Tokenizers.chain(Arrays.asList(new Whitespace(), (Tokenizer) null));
		}

		@Test(expected = IllegalArgumentException.class)
		public void chainWithNull() {
			Tokenizers.chain((Tokenizer) null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void chainWithNullInVarArg() {
			Tokenizers.chain(new Whitespace(), null, new Whitespace());
		}

		@Test
		public void chainWithSingle() {
			Tokenizer t = new Whitespace();
			assertSame(t, chain(t));
		}

		@Test
		public void chainWithSingletonList() {
			Tokenizer t = new Whitespace();
			assertSame(t, Tokenizers.chain(Collections.singletonList(t)));
		}
	}

	public static final class TransformingFilteringFilteringTokenizerTest
			extends TokenizerTest {
		@Override
		public T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog are cool", "ARE", "COOL"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(
					filter(transform(new Whitespace(), toUpperCase()),
							mouseCatDogUpperCase()), theAndOrUpperCase());
		}
	}

	public static final class TransformingFilteringTokenizerTest extends
			TokenizerTest {
		@Override
		public T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "THE", "AND", "OR"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(transform(new Whitespace(), toUpperCase()),
					mouseCatDogUpperCase());
		}
	}

	public static final class TransformingFilteringTransformingFilterTokenizerTest
			extends TokenizerTest {
		@Override
		public T[] getTests() {
			return new T[] {
					new T("the Mouse and Cat or Dog", "ESUOM", "TAC", "GOD"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return filter(
					transform(
							filter(transform(new Whitespace(),
									reverseCapitalized()), theAndOr()),
							toUpperCase()), mouseCatDogUpperCase());
		}
	}

	public static final class TransformingFilteringTransformingTokenizerTest
			extends TokenizerTest {
		@Override
		public T[] getTests() {
			return new T[] {
					new T("the Mouse and Cat or Dog", "ESUOM", "TAC", "GOD"),
					new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return transform(
					filter(transform(new Whitespace(), reverseCapitalized()),
							theAndOr()), toUpperCase());
		}
	}

	public static final class TransformingTokenizerTest extends TokenizerTest {

		@Override
		public T[] getTests() {
			return new T[] {
					new T("the mouse and cat or dog", "THE", "MOUSE", "AND",
							"CAT", "OR", "DOG"), new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return Tokenizers.transform(new Whitespace(), toUpperCase());
		}
	}

	public static final class TransformingTransformingTokenizerTest extends
			TokenizerTest {

		@Override
		public T[] getTests() {
			return new T[] {
					new T("the Mouse and Cat or Dog", "THE", "ESUOM", "AND",
							"TAC", "OR", "GOD"), new T("") };
		}

		@Override
		protected Tokenizer getTokenizer() {
			return transform(transform(new Whitespace(), reverseCapitalized()),
					toUpperCase());
		}
	}

	static Predicate<String> mouseCatDogUpperCase() {
		return not(in(asList("CAT", "MOUSE", "DOG")));
	}

	static Function<String, String> reverseCapitalized() {
		return new Function<String, String>() {

			@Override
			public String apply(String input) {
				if (!Character.isUpperCase(input.charAt(0))) {
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
