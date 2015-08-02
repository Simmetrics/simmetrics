package org.simmetrics.simplifier;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public abstract class SimplifierTest {
	protected static final class T {
		final String expected;
		final String string;

		public T(String string, String expected) {
			this.string = string;
			this.expected = expected;
		}

		public String string() {
			return string;
		}

	}

	private static void testSimplified(String expected, String simplified) {
		assertNotNull(simplified);
		assertEquals(expected, simplified);
	}

	protected Simplifier simplifier;

	protected T[] tests;

	@Test
	public void containsEmptyTest() {
		for (T t : tests) {
			if (t.string.isEmpty()) {
				return;
			}
		}

		fail("Test must contain a case with empty string");
	}

	protected abstract Simplifier getSimplifier();

	protected abstract T[] getTests();

	@Before
	public final void setUp() throws Exception {
		simplifier = getSimplifier();
		tests = getTests();
	}

	@Test
	public final void implementsToString() {

		String simplifierToString = simplifier.toString();
		String defaultToString = simplifier.getClass().getName() + "@"
				+ Integer.toHexString(simplifier.hashCode());

		assertFalse("toString() was not implemented " + simplifier.toString(),
				defaultToString.equals(simplifierToString));
	}

	@Test
	public final void simplfy() {
		for (T t : tests) {
			testSimplified(t.expected, simplifier.simplify(t.string));
		}
	}

	@Test(expected = NullPointerException.class)
	public final void simplfyNullPointerException() {
		simplifier.simplify(null);
	}

	@Test
	@Ignore
	public final void generateSimplified() {
		System.out.println(simplifier);
		for (T t : tests) {
			System.out.println(format("new T(\"%s\", \"%s\"),", t.string,
					simplifier.simplify(t.string)));
		}
	}
}
