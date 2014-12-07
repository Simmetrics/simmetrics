package uk.ac.shef.wit.simmetrics.similaritymetrics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public abstract class InterfaceStringMetricTest {

	protected class T {
		protected final float similarity;
		protected final String string1;
		protected final String string2;

		public T(float similarity, String string1, String string2) {
			this.string1 = string1;
			this.string2 = string2;
			this.similarity = similarity;
		}

	}


	private static final float DEFAULT_DELTA = 0.0001f;
	private float delta;

	protected InterfaceStringMetric metric;

	public abstract InterfaceStringMetric getMetric();

	public abstract T[] getTests();

	@Before
	public void setUp() throws Exception {
		metric = getMetric();
		delta = getDelta();
	}

	protected float getDelta() {
		return DEFAULT_DELTA;
	}

	@Test
	public void testGetSimilarity() {
		for (T t : getTests()) {
			
			float actuall = metric.getSimilarity(t.string1, t.string2);
			assertTrue("Similarity must fall within [0.0 - 1.0] range",
					0.0f <= actuall && actuall <= 1.0f);
			
			String message = String.format("\"%s\" vs \"%s\"", t.string1,
					t.string2);
			assertEquals(message, t.similarity, actuall, delta);
		}
	}
	
	@Test
	public void generateTest() {
		for (T t : getTests()) {
			float actuall = metric.getSimilarity(t.string1, t.string2);
			String message = String.format("new T(%.4ff, \"%s\", \"%s\"),",actuall, t.string1,t.string2 );
			System.out.println(message);
		}
	}
	
	

	@SuppressWarnings("deprecation")
	@Test
	public void testGetSimilarityExplained() {
		assertNull(metric.getSimilarityExplained(null,null));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetShortDescriptionString() {
		assertEquals(metric.getClass().getSimpleName(),
				metric.getShortDescriptionString());
	}

}
