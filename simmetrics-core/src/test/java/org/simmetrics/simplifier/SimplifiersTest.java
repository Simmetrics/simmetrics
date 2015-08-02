package org.simmetrics.simplifier;

import static junit.framework.Assert.assertSame;
import static org.simmetrics.simplifiers.Simplifiers.chain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.simmetrics.simplifiers.Case;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.WordCharacters;

@SuppressWarnings("javadoc")
public final class SimplifiersTest {

	public static final class WithChainSimplifier extends SimplifierTest {

		@Override
		protected Simplifier getSimplifier() {
			return chain(toSheep(), chain(reverseCapitalized(), toGoat()),
					toUpperCase());
		}

		@Override
		protected T[] getTests() {
			return new T[] { new T("a", "A SHEEP GOAT"),
					new T("A cat", "PEEHS TAC A GOAT"),
					new T("", " SHEEP GOAT") };
		}

	}

	public static final class WithEmpty extends SimplifierTest {

		@Override
		protected Simplifier getSimplifier() {
			return chain(new ArrayList<Simplifier>());
		}

		@Override
		protected T[] getTests() {
			return new T[] { new T("a Sheep Goat", "a Sheep Goat"),
					new T("", "") };
		}

	}
	
	public static final class WithTwo extends SimplifierTest {

		@Override
		protected Simplifier getSimplifier() {
			return chain(toSheep(), toGoat());
		}

		@Override
		protected T[] getTests() {
			return new T[] { new T("a", "a Sheep Goat"),
					new T("", " Sheep Goat") };
		}

	}

	static Simplifier reverseCapitalized() {
		return new Simplifier() {

			@Override
			public String simplify(String input) {
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

	static Simplifier toGoat() {
		return new Simplifier() {

			@Override
			public String simplify(String input) {
				return input + " Goat";
			}

			@Override
			public String toString() {
				return "Goat";
			}
		};
	}

	static Simplifier toSheep() {
		return new Simplifier() {

			@Override
			public String simplify(String input) {
				return input + " Sheep";
			}

			@Override
			public String toString() {
				return "Sheep";
			}
		};
	}

	static Simplifier toUpperCase() {
		return new Simplifier() {

			@Override
			public String simplify(String input) {
				return input.toUpperCase();
			}

			@Override
			public String toString() {
				return "toUpperCase";
			}
		};

	}

	@Test(expected = IllegalArgumentException.class)
	public void chainWithListContainingNull() {
		chain(Arrays.asList(new Case.Lower(), (Simplifier) null,
				new WordCharacters()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void chainWithNull() {
		chain((Simplifier) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void chainWithNullInVarArg() {
		chain(new Case.Lower(), null, new WordCharacters());
	}

	@Test
	public void chainWithSingle() {
		Simplifier lower = new Case.Lower();
		assertSame(lower, chain(lower));
	}

	@Test
	public void chainWithSingletonList() {
		Simplifier lower = new Case.Lower();
		assertSame(lower, chain(Collections.singletonList(lower)));
	}

}
