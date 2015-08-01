package org.simmetrics.simplifier;

import static org.simmetrics.simplifiers.Simplifiers.chain;

import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public final class SimplifiersTest {
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

	public static final class ChainSimplifier extends SimplifierTest {

		@Override
		protected Simplifier getSimplifier() {
			return chain(toSheep(), toGoat());
		}

		@Override
		protected T[] getTests() {
			return new T[] { new T("a", "a Sheep Goat"), new T("", " Sheep Goat") };
		}

	}
	
	public static final class ChainChainSimplifier extends SimplifierTest {

		@Override
		protected Simplifier getSimplifier() {
			return chain(toSheep(), chain(reverseCapitalized(), toGoat()),toUpperCase());
		}

		@Override
		protected T[] getTests() {
			return new T[] { 
					new T("a", "A SHEEP GOAT"), 
					new T("A cat", "PEEHS TAC A GOAT"),
					new T("", " SHEEP GOAT") };
		}

	}
}
