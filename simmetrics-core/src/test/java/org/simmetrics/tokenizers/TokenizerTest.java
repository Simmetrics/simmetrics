/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.simmetrics.matchers.ImplementsToString.implementsToString;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

@SuppressWarnings("javadoc")
public abstract class TokenizerTest {

	@Rule
	public final MockitoRule mockitoRule = MockitoJUnit.rule();
	
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

		public Multiset<String> tokensAsMultiset() {
			return HashMultiset.create(tokensAsList());
		}

	}

	private static void testTokens(String string, Collection<String> expected,
			Collection<String> actual) {
		assertEquals(string + " did not tokenize correctly", expected, actual);
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
	protected boolean supportsTokenizeToMultiset() {
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
	public final void shouldImplementToString() {
		assertThat(tokenizer, implementsToString());
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
	
	
	@Test
	public final void shouldTokenizeToMutiset() {
		if (!supportsTokenizeToMultiset()) {
			thrown.expect(UnsupportedOperationException.class);
		}

		for (T t : tests) {
			testTokens(t.string(), t.tokensAsMultiset(),
					tokenizer.tokenizeToMultiset(t.string()));
		}
	}
	
	@Test
	public final void tokenizeToMultisetShouldThrowNullPointerException() {
		if (supportsTokenizeToMultiset()) {
			thrown.expect(NullPointerException.class);
		} else {
			thrown.expect(UnsupportedOperationException.class);
		}
		tokenizer.tokenizeToMultiset(null);
	}
	
}
