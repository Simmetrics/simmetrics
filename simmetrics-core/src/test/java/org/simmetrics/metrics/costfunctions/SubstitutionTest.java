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
import org.junit.Ignore;
import org.junit.Test;
import org.simmetrics.metrics.functions.Substitution;

@SuppressWarnings("javadoc")
public abstract class SubstitutionTest {

	protected static class T {
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

	protected Substitution cost;

	public abstract Substitution getCost();

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

			float actuall = cost.compare(t.string1, t.string1Index, t.string2,
					t.string2Index);

			String costMessage = "Cost must fall within [%.3f - %.3f] range";
			costMessage = String.format(costMessage, cost.min(),
					cost.max());
			assertTrue(costMessage, cost.min() <= actuall
					&& actuall <= cost.max());

			String message = String.format("\"%s\" vs \"%s\"",
					t.string1.charAt(t.string1Index),
					t.string2.charAt(t.string2Index));
			assertEquals(message, t.cost, actuall, delta);
		}
	}

	@Test
	@Ignore
	public void generateTest() {
		for (T t : getTests()) {
			float actuall = cost.compare(t.string1, t.string1Index, t.string2,
					t.string2Index);
			String message = String.format(
					"new T(%.4ff, testString1, %s, testString2, %s),", actuall,
					t.string1Index, t.string2Index);
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

	protected static void assertToStringContains(Substitution metric,
			String content) {
		String string = metric.toString();
		String message = String.format("%s must contain %s ", string, content);

		assertTrue(message, message.contains(content));
	}

}
