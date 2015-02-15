package org.simmetrics.simplifiers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.SoundexSimplifier;

public class SoundexSimplifierTest {

	private Simplifier simplifier;

	@Before
	public void setUp() throws Exception {
		simplifier = new SoundexSimplifier();
	}

	@Test
	public void test() {

		assertEquals("S5200", simplifier.simplify("SNAKE"));
		assertEquals("C2000", simplifier.simplify("CAKE"));
		assertEquals("F5000", simplifier.simplify("Finn"));
		assertEquals("J2000", simplifier.simplify("Jake"));
		assertEquals("P1000", simplifier.simplify("PeeBee"));
		assertEquals("M6245", simplifier.simplify("Marceline"));
		assertEquals("S6000", simplifier.simplify("Sarah"));
		assertEquals("E4213", simplifier.simplify("Elizabeth"));

		assertEquals("H4300", simplifier.simplify("Healthy"));
		assertEquals("H4300", simplifier.simplify("Healed"));

	}

	/**
	 * Tests references from <a href="http://en.wikipedia.org/wiki/Soundex"
	 * >Wikipedia - Soundex</a>
	 */
	@Test
	public void testWikipediaExamples() {

		Simplifier simplifier = new SoundexSimplifier(4);

		assertEquals("R163", simplifier.simplify("Robert"));
		assertEquals("R163", simplifier.simplify("Rupert"));
		assertEquals("R150", simplifier.simplify("Rubin"));

		assertEquals("A261", simplifier.simplify("Ashcraft"));
		assertEquals("A261", simplifier.simplify("Ashcroft"));

		assertEquals("T522", simplifier.simplify("Tymczak"));
		assertEquals("P236", simplifier.simplify("Pfister"));
	}

	@Test
	public void testEmpty() {
		assertEquals("", simplifier.simplify(""));
	}

	@Test
	public void testLong() {
		Simplifier simplifier = new SoundexSimplifier(14);
		assertEquals("W4124242352516",
				simplifier.simplify("Wolfeschlegelsteinhausenbergerdorff"));
		assertEquals("K5222425400000",
				simplifier.simplify("Keihanaikukauakahihuliheekahaunaele"));

	}

	@Test
	public void testShort() {
		assertEquals("A0000", simplifier.simplify("a"));
		assertEquals("A1000", simplifier.simplify("ab"));
		assertEquals("A1200", simplifier.simplify("abc"));

		assertEquals("J2000", simplifier.simplify("Jack"));
		assertEquals("J4000", simplifier.simplify("Jill"));
		assertEquals("J5250", simplifier.simplify("Johnson"));

	}

	@Test
	public void testNonWord() {
		assertEquals("A0000", simplifier.simplify(" a"));
		assertEquals("A1000", simplifier.simplify(" ab"));
		assertEquals("A1200", simplifier.simplify(" a b c"));

		assertEquals("J2000", simplifier.simplify("&Jack"));
		assertEquals("J4000", simplifier.simplify("Jill2000"));
		assertEquals("J5250", simplifier.simplify("150Johnson"));

		assertEquals("A1230", simplifier.simplify("aaa bbb ccc ddd"));
		assertEquals("A1200", simplifier.simplify("aaa bbb ccc eee"));

	}

}
