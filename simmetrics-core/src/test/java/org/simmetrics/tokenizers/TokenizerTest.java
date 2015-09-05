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

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
public abstract class TokenizerTest {

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

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

	private static void testTokens(String string, Collection<String> expected,
			Collection<String> actual) {
		assertEquals(string + " did not tokenize to " + expected + " but "
				+ actual, expected, actual);
		assertFalse(actual + " contained null", actual.contains(null));
	}

	protected T[] tests;

	protected Tokenizer tokenizer;

	protected abstract T[] getTests();

	protected boolean supportsTokenizeToList() {
		return true;
	}

	protected boolean supportsTokenizeToSet() {
		return true;
	}

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

		String metricToString = tokenizer.toString();
		String defaultToString = tokenizer.getClass().getName() + "@"
				+ Integer.toHexString(tokenizer.hashCode());

		assertFalse("toString() was not implemented " + tokenizer.toString(),
				defaultToString.equals(metricToString));
	}

	@Test
	public final void shouldTokenizeToList() {
		if (!supportsTokenizeToList()) {
			thrown.expect(UnsupportedOperationException.class);
		}

		for (T t : tests) {
			testTokens(t.string(), t.tokensAsList(),
					tokenizer.tokenizeToList(t.string()));
		}
	}

	@Test
	public final void tokenizeToListShouldThrowNullPointerException() {
		if (supportsTokenizeToList()) {
			thrown.expect(NullPointerException.class);
		} else {
			thrown.expect(UnsupportedOperationException.class);
		}

		tokenizer.tokenizeToList(null);
	}

	@Test
	public final void shouldTokenizeToSet() {
		if (!supportsTokenizeToSet()) {
			thrown.expect(UnsupportedOperationException.class);
		}

		for (T t : tests) {
			testTokens(t.string(), t.tokensAsSet(),
					tokenizer.tokenizeToSet(t.string()));
		}
	}

	@Test
	public final void tokenizeToSetShouldThrowNullPointerException() {
		if (supportsTokenizeToSet()) {
			thrown.expect(NullPointerException.class);
		} else {
			thrown.expect(UnsupportedOperationException.class);
		}
		tokenizer.tokenizeToSet(null);
	}

}
