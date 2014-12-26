package org.simmetrics.tokenisers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.tokenisers.Tokenizer;
import org.simmetrics.wordhandlers.TermHandler;

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
