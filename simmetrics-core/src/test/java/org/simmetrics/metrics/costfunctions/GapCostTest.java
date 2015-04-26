/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics.costfunctions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.simmetrics.metrics.functions.Gap;

public abstract class GapCostTest {

	protected static class T {
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

	protected Gap cost;

	public abstract Gap getCost();

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

			float actuall = cost.value(t.index1, t.index2);

			String costMessage = "Cost %.3f must fall within [%.3f - %.3f] range";
			costMessage = String.format(costMessage, actuall, cost.min(),
					cost.max());
			assertTrue(costMessage, cost.min() <= actuall
					&& actuall <= cost.max());

			String message = String.format("\"%s\" vs \"%s\"",
					t.string.charAt(t.index1), t.string.charAt(t.index2));
			assertEquals(message, t.cost, actuall, delta);
		}
	}

	
	public void generateTest() {
		for (T t : getTests()) {
			float actuall = cost.value(t.index1, t.index2);

			String message = String.format("new T(%.4ff, testString, %s, %s),",
					actuall, t.index1, t.index2);
			System.out.println(message);
		}
	}

	@Test
	public void testToString() {
		assertFalse(
				"@ indicates toString() was not implemented " + cost.toString(),
				cost.toString().contains("@"));

		assertToStringContains(cost, cost.getClass().getSimpleName());
	}

	protected static void assertToStringContains(Gap metric,
			String content) {
		String string = metric.toString();
		String message = String.format("%s must contain %s ", string, content);

		assertTrue(message, message.contains(content));
	}

}
