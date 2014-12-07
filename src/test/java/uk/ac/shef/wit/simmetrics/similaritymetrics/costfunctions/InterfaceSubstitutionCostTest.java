package uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public abstract class InterfaceSubstitutionCostTest {

	protected class T {
		protected final float cost;
		protected final String string1;
		protected final String string2;
		protected final int string1Index;
		protected final int string2Index;

		public T(float cost, String string1, int string1Index, String string2,
				int string2Index) {
			this.string1 = string1;
			this.string1Index = string1Index;
			this.string2 = string2;
			this.string2Index = string2Index;
			this.cost = cost;
		}

	}

	private static final float DEFAULT_DELTA = 0.0001f;
	private float delta;

	protected InterfaceSubstitutionCost cost;

	public abstract InterfaceSubstitutionCost getCost();

	public abstract T[] getTests();

	@Before
	public void setUp() throws Exception {
		cost = getCost();
		delta = getDelta();
	}

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	@Test
	public void testGetSimilarity() {
		for (T t : getTests()) {

			float actuall = cost.getCost(t.string1, t.string1Index, t.string2,
					t.string2Index);

			String costMessage = "Cost must fall within [%.3f - %.3f] range";
			costMessage = String.format(costMessage, cost.getMinCost(), cost.getMaxCost());
			assertTrue(costMessage, cost.getMinCost() <= actuall && actuall <= cost.getMaxCost());

			String message = String.format("\"%s\" vs \"%s\"",
					t.string1.charAt(t.string1Index),
					t.string2.charAt(t.string2Index));
			assertEquals(message, t.cost, actuall, delta);
		}
	}

	@Test
	public void generateTest() {
		for (T t : getTests()) {
			float actuall = cost.getCost(t.string1, t.string1Index, t.string2,
					t.string2Index);
			String message = String.format("new T(%.4ff, testString1, %s, testString2, %s),",
					actuall, t.string1Index,  t.string2Index);
			System.out.println(message);
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetShortDescriptionString() {
		assertEquals(cost.getClass().getSimpleName(),
				cost.getShortDescriptionString());
	}

}
