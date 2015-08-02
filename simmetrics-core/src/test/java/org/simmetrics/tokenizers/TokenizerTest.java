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

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
public abstract class TokenizerTest {

	protected static class T {
		private final String string;
		private final String[] tokens;

		public T(String string, String... tokens) {
			this.string = string;
			this.tokens = tokens;
		}

		public String string() {
			return string;
		}

		public List<String> tokensAsList() {
			return asList(tokens);
		}

		public Set<String> tokensAsSet() {
			return new HashSet<>(tokensAsList());
		}

	}

	protected static void assertToStringContains(Tokenizer tokenizer,
			String content) {
		String string = tokenizer.toString();
		String message = String.format("%s must contain %s ", string, content);

		assertTrue(message, message.contains(content));
	}

	private static void testTokens(String string, Collection<String> expected,
			Collection<String> actual) {
		assertEquals(string + " did not tokenize to " + expected + " but " + actual, expected, actual);
		assertFalse(actual + " contained null", actual.contains(null));
	}

	protected T[] tests;

	protected Tokenizer tokenizer;

	protected abstract T[] getTests();

	protected abstract Tokenizer getTokenizer();

	@Before
	public final void setUp() throws Exception {
		tokenizer = getTokenizer();
		tests = getTests();
	}

	@Test
	public final void containsEmptyTest() {
		for (T t : tests) {
			if (t.string().isEmpty()) {
				return;
			}
		}

		fail("Test must contain a case with empty string");
	}

	@Test
	public final void implementsToString() {
		assertFalse(
				"@ indicates toString() was not implemented "
						+ tokenizer.toString(),
				tokenizer.toString().contains("@"));

		assertToStringContains(tokenizer, tokenizer.getClass().getSimpleName());
	}

	@Test
	public final void tokenizeToArrayList() {
		for (T t : tests) {
			testTokens(t.string(), t.tokensAsList(), tokenizer.tokenizeToList(t.string()));
		}
	}

	@Test(expected = NullPointerException.class)
	public final void tokenizeToListNullPointerException() {
		tokenizer.tokenizeToList(null);
	}

	@Test
	public final void tokenizeToSet() {
		for (T t : tests) {
			testTokens(t.string(), t.tokensAsSet(), tokenizer.tokenizeToSet(t.string()));
		}
	}

	@Test(expected = NullPointerException.class)
	public final void tokenizeToSetNullPointerException() {
		tokenizer.tokenizeToSet(null);
	}

}
