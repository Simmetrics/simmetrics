package org.simmetrics.tokenizers;

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
		
		assertEquals("s5200", simplifier.simplify("SNAKE"));
		assertEquals("c2000", simplifier.simplify("CAKE"));
		assertEquals("f5000", simplifier.simplify("Finn"));
		assertEquals("j2000", simplifier.simplify("Jake"));
		assertEquals("p1000", simplifier.simplify("PeeBee"));
		assertEquals("m6245", simplifier.simplify("Marceline"));
		assertEquals("s6000", simplifier.simplify("Sarah"));
		assertEquals("e4213", simplifier.simplify("Elizabeth"));

	}
	
	@Test
	public void testEmpty() {
		assertEquals("", simplifier.simplify(""));
	}
	
	@Test
	public void testLong() {
		SoundexSimplifier simplifier = new SoundexSimplifier(14);
		assertEquals("w4124242352516", simplifier.simplify("Wolfeschlegelsteinhausenbergerdorff"));
		assertEquals("k5222425400000", simplifier.simplify("Keihanaikukauakahihuliheekahaunaele"));


	}
	
	@Test
	public void testShort() {
		assertEquals("a0000", simplifier.simplify("a"));
		assertEquals("a1000", simplifier.simplify("ab"));
		assertEquals("a1200", simplifier.simplify("abc"));

		assertEquals("j2000", simplifier.simplify("Jack"));
		assertEquals("j4000", simplifier.simplify("Jill"));
		assertEquals("j5250", simplifier.simplify("Johnson"));

	}

}
