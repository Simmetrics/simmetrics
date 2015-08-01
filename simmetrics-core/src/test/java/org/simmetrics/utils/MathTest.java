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
package org.simmetrics.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.simmetrics.utils.Math;

@SuppressWarnings("javadoc")
public class MathTest {

	@Test
	public void max3float() {	
		assertEquals(0.3f, Math.max(0.1f, 0.2f, 0.3f), 0.0);
		assertEquals(0.31f, Math.max(0.31f, 0.2f, 0.3f), 0.0);
		assertEquals(0.5f, Math.max(0.1f, 0.5f, 0.3f), 0.0);
		assertEquals(0.5f, Math.max(-10.1f, 0.5f, -0.3f), 0.0);
	}

	@Test
	public void max3int() {
		assertEquals(5, Math.max(-10, 5, 3));
		assertEquals(10, Math.max(10, 5, 3));
		assertEquals(13, Math.max(-10, 5, 13));
	}

	@Test
	public void max4float() {
		assertEquals(36.9f, Math.max(10.1f, 5.45f, -3.12f, 36.9f), 0.0);
		assertEquals(10.1f, Math.max(10.1f, 5.45f, -3.12f, 6.9f), 0.0);
		assertEquals(-3.12f, Math.max(-10.1f, -5.45f, -3.12f, -36.9f),
				0.0);
		assertEquals(25.45f, Math.max(10.1f, 25.45f, -3.12f, 16.9f), 0.0);
	}

	@Test
	public void max4int() {
		assertEquals(5, Math.max(-10, 5, 3, 1));
		assertEquals(10, Math.max(10, 5, 3, 1));
		assertEquals(13, Math.max(-10, 5, 13, 1));
		assertEquals(15, Math.max(-10, 5, 3, 15));
	}

	@Test
	public void min3float() {
		assertEquals(5.45f, Math.min(10.1f, 5.45f, 13.12f), 0.0);
		assertEquals(0.1f, Math.min(0.1f, 5.45f, 13.12f), 0.0);
		assertEquals(-3.12f, Math.min(10.1f, 5.45f, -3.12f), 0.0);
	}

	@Test
	public void min3int() {
		assertEquals(-10, Math.min(-10, 5, 13));
		assertEquals(-13, Math.min(-10, 5, -13));
		assertEquals(5, Math.min(10, 5, 13));
	}

	@Test
	public void min4int() {
		assertEquals(-10, Math.min(-10, 5, 3, 1));
		assertEquals(-5, Math.min(10, -5, 13, 1));
		assertEquals(3, Math.min(10, 5, 3, 15));
		assertEquals(1, Math.min(10, 5, 3, 1));

	}

	@Test
	public void min4float() {
		assertEquals(-10.1f, Math.min(-10.1f, 5.45f, -3.12f, 36.9f), 0.0);
		assertEquals(-5.45f, Math.min(10.1f, -5.45f, -3.12f, 6.9f), 0.0);
		assertEquals(3.12f, Math.min(10.1f, 5.45f, 3.12f, 36.9f),0.0);
		assertEquals(16.9f, Math.min(100.1f, 25.45f, 23.12f, 16.9f), 0.0);
	}

}
