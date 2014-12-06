package uk.ac.shef.wit.simmetrics.similaritymetrics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for SimonWhite implementation.
 */
public class SimonWhiteTest extends TestCase {

	private static final double DELTA = 0.0001;

	/**
	 * Simple test, compares Healed with Sealed, Healthy, Heard, Herded, Help
	 * and Sold
	 */
	public void testSimple() {
		InterfaceStringMetric metric = new SimonWhite();
		String healed = "Healed";
		assertEquals("Healed vs Sealed",
				metric.getSimilarity(healed, "Sealed"), 0.8f, DELTA);
		assertEquals("Healed vs Healthy",
				metric.getSimilarity(healed, "Healthy"), 0.54545456f, DELTA);
		assertEquals("Healed vs Heard", metric.getSimilarity(healed, "Heard"),
				0.44444445f, DELTA);
		assertEquals("Healed vs Herded",
				metric.getSimilarity(healed, "Herded"), 0.4f, DELTA);
		assertEquals("Healed vs Help", metric.getSimilarity(healed, "Help"),
				0.25f, DELTA);
		assertEquals("Healed vs Sold", metric.getSimilarity(healed, "Sold"),
				0f, DELTA);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testRigourous() {
		InterfaceStringMetric metric = new SimonWhite();
		String[] books = {
				"Web Database Applications with PHP & MySQL",
				"Creating Database Web Applications with PHP and ASP",
				"Building Database Applications on the Web Using PHP3",
				"Building Web Database Applications with Visual Studio 6",
				"Web Application Development With PHP",
				"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection",
				"Structural Assessment: The Role of Large and Full-Scale Testing",
				"How to Find a Scholarship Online" };
		String[] phrases = { "Web Database Applications",
				"PHP Web Applications", "Web Aplications" };
		float[][] values = { { 0.81632656f, 0.6818182f, 0.58536583f },
				{ 0.71428573f, 0.5882353f, 0.5f },
				{ 0.7017544f, 0.5769231f, 0.48979592f },
				{ 0.6666667f, 0.47272727f, 0.46153846f },
				{ 0.5106383f, 0.6666667f, 0.5641026f },
				{ 0.4878049f, 0.33766234f, 0.3243243f },
				{ 0.121212125f, 0.06557377f, 0.06896552f },
				{ 0.09756097f, 0.11111111f, 0.121212125f }, };

		for (int i = 0; i < books.length; i++) {
			for (int j = 0; j < phrases.length; j++) {
				assertEquals(
						books[i] + " vs " + phrases[j],
						metric.getSimilarity(books[i].toLowerCase(),
								phrases[j].toLowerCase()), values[i][j], DELTA);
			}
		}
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(SimonWhiteTest.class);
	}

}
