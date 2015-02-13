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
		
		assertEquals("s-5200", simplifier.simplify("SNAKE"));
		assertEquals("c-2000", simplifier.simplify("CAKE"));
		assertEquals("f-5000", simplifier.simplify("Finn"));
		assertEquals("j-2000", simplifier.simplify("Jake"));
		assertEquals("p-1000", simplifier.simplify("PeeBee"));
		assertEquals("m-6245", simplifier.simplify("Marceline"));
		assertEquals("s-6000", simplifier.simplify("Sarah"));
		assertEquals("e-4213", simplifier.simplify("Elizabeth"));

	}
	
	@Test
	public void testLong() {
		
		assertEquals("w-4124242352500", new SoundexSimplifier(15).simplify("Wolfeschlegelsteinhausenbergerdorff"));
		assertEquals("k-5222400000000", new SoundexSimplifier(15).simplify("Keihanaikukauakahihuliheekahaunaele"));


	}

}
