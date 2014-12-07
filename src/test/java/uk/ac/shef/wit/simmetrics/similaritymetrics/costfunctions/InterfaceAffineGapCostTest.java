package uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public abstract class InterfaceAffineGapCostTest {

	protected class T {
		protected final float cost;
		protected final String string;
		protected final int index1;
		protected final int index2;
		public T(float cost, String string, int index1, int index2) {
			super();
			this.cost = cost;
			this.string = string;
			this.index1 = index1;
			this.index2 = index2;
		}
	}

	private static final float DEFAULT_DELTA = 0.0001f;
	private float delta;

	protected InterfaceAffineGapCost cost;

	public abstract InterfaceAffineGapCost getCost();

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

			float actuall = cost.getCost(t.string, t.index1, t.index2);

			String costMessage = "Cost must fall within [%.3f - %.3f] range";
			costMessage = String.format(costMessage, cost.getMinCost(), cost.getMaxCost());
			assertTrue(costMessage, cost.getMinCost() <= actuall && actuall <= cost.getMaxCost());

			String message = String.format("\"%s\" vs \"%s\"",
					t.string.charAt(t.index1),
					t.string.charAt(t.index2));
			assertEquals(message, t.cost, actuall, delta);
		}
	}

	@Test
	public void generateTest() {
		for (T t : getTests()) {
			float actuall = cost.getCost(t.string, t.index1, t.index2);

			String message = String.format("new T(%.4ff, testString, %s, %s),",
					actuall,  t.index1, t.index2);
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
