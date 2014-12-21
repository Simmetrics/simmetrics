package org.simmetrics;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.simplifier.CaseSimplifier;

import static org.simmetrics.StringMetrics.*;

public class StringMetricsTest {
	private static final float DELTA = 0.001f;
	private final String[] names1 = new String[] {
			"Louis Philippe, le Roi Citoyen", "Charles X", "Louis XVIII",
			"Napoleon II", "Napoleon I" };
	private final String[] names2 = new String[] { "Louis XVIII",
			"Napoléon Ier, le Grand",
			"Louis XVI le Restaurateur de la Liberté Française" };

	private final float[] expected = new float[] { 0.071f, 0.153f, 0.000f,
			0.933f, 1.000f };
	private final float[] expected2 = new float[] { 0.275f, 0.095f, 0.285f,
			0.000f, 0.000f };

	private StringMetric metric = new SimonWhite() {
		{
			setSimplifier(new CaseSimplifier.Lower());
		}
	};


	@Test
	public void testCompareList() {
		assertArrayEquals(expected,
				compare(metric, "Napoleon I", Arrays.asList(names1)), DELTA);
	}

	@Test
	public void testCompareArray() {
		assertArrayEquals(expected, compare(metric, "Napoleon I", names1),
				DELTA);

	}

	@Test
	public void testCompareArrays() {
		assertArrayEquals(new float[0],
				compareArrays(metric, new String[] {}, new String[] {}), DELTA);

		assertArrayEquals(expected2, compareArrays(metric, names1, names2),
				DELTA);

	}

}
