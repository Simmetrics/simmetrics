/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package org.simmetrics.tokenisers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.tokenisers.Tokenizer;

public abstract class TokeniserTest {

	private Tokenizer tokenizer;
	
	protected abstract Tokenizer getTokenizer();

	public abstract T[] getTests();

	protected class T {
		protected final String string;
		protected final String[] tokens;

		public T(String string, String... tokens) {
			this.string = string;
			this.tokens = tokens;
		}

	}

	@Before
	public void setUp() throws Exception {
		tokenizer = getTokenizer();
	}




	@Test
	public void testTokenizeToArrayList() {
		for (T t : getTests()) {
			ArrayList<String> tokens = tokenizer.tokenizeToList(t.string);
			String message = String.format("for %s expected: %s found: %s",
					t.string,
					Arrays.toString(t.tokens),
					Arrays.toString(tokens.toArray()));
			assertArrayEquals(message, t.tokens,
					tokens.toArray(new String[tokens.size()]));
		}
	}

	@Test
	public void testTokenizeToSet() {
		for (T t : getTests()) {
			Set<String> tokens = tokenizer.tokenizeToSet(t.string);
			Set<String> expected = new HashSet<String>(Arrays.asList(t.tokens));

			assertEquals(expected, tokens);

		}
	}
	
	@Test
	public void testToString() {
		assertFalse(
				"@ indicates toString() was not implemented "
						+ tokenizer.toString(), tokenizer.toString().contains("@"));

		assertToStringContains(tokenizer, tokenizer.getClass().getSimpleName());
	}

	protected static void assertToStringContains(Tokenizer tokenizer,
			String content) {
		String string = tokenizer.toString();
		String message = String.format("%s must contain %s ", string, content);

		assertTrue(message, message.contains(content));
	}

}
