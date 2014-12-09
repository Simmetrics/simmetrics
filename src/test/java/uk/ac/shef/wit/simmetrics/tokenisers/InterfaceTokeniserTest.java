package uk.ac.shef.wit.simmetrics.tokenisers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public abstract class InterfaceTokeniserTest {

	private InterfaceTokeniser tokenizer;

	protected abstract InterfaceTokeniser getTokenizer();

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

	@SuppressWarnings("deprecation")
	@Test
	public void testGetShortDescriptionString() {
		assertEquals(tokenizer.getClass().getSimpleName(),tokenizer.getShortDescriptionString());
	}

	@Test
	public void testGetStopWordHandler() {
		assertNotNull(tokenizer.getStopWordHandler());
	}

//	@Test
//	public void testSetStopWordHandler() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testTokenizeToArrayList() {
		for (T t : getTests()) {
			ArrayList<String> tokens = tokenizer.tokenizeToArrayList(t.string);
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

}
