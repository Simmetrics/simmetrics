/**
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package uk.ac.shef.wit.simmetrics.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.shef.wit.simmetrics.utils.Math;

public class MathFuncsTest {

	@Test
	public void testMax3float() {
		assertEquals(0.3f, Math.max3(0.1f, 0.2f, 0.3f), 0.0);
		assertEquals(0.31f, Math.max3(0.31f, 0.2f, 0.3f), 0.0);
		assertEquals(0.5f, Math.max3(0.1f, 0.5f, 0.3f), 0.0);
		assertEquals(0.5f, Math.max3(-10.1f, 0.5f, -0.3f), 0.0);
	}

	@Test
	public void testMax3int() {
		assertEquals(5, Math.max3(-10, 5, 3));
		assertEquals(10, Math.max3(10, 5, 3));
		assertEquals(13, Math.max3(-10, 5, 13));
	}

	@Test
	public void testMax4float() {
		assertEquals(36.9f, Math.max4(10.1f, 5.45f, -3.12f, 36.9f), 0.0);
		assertEquals(10.1f, Math.max4(10.1f, 5.45f, -3.12f, 6.9f), 0.0);
		assertEquals(-3.12f, Math.max4(-10.1f, -5.45f, -3.12f, -36.9f),
				0.0);
		assertEquals(25.45f, Math.max4(10.1f, 25.45f, -3.12f, 16.9f), 0.0);
	}

	@Test
	public void testMax4int() {
		assertEquals(5, Math.max4(-10, 5, 3, 1));
		assertEquals(10, Math.max4(10, 5, 3, 1));
		assertEquals(13, Math.max4(-10, 5, 13, 1));
		assertEquals(15, Math.max4(-10, 5, 3, 15));
	}

	@Test
	public void testMin3float() {
		assertEquals(5.45f, Math.min3(10.1f, 5.45f, 13.12f), 0.0);
		assertEquals(0.1f, Math.min3(0.1f, 5.45f, 13.12f), 0.0);
		assertEquals(-3.12f, Math.min3(10.1f, 5.45f, -3.12f), 0.0);
	}

	@Test
	public void testMin3int() {
		assertEquals(-10, Math.min3(-10, 5, 13));
		assertEquals(-13, Math.min3(-10, 5, -13));
		assertEquals(5, Math.min3(10, 5, 13));
	}

	@Test
	public void testMin4int() {
		assertEquals(-10, Math.min4(-10, 5, 3, 1));
		assertEquals(-5, Math.min4(10, -5, 13, 1));
		assertEquals(3, Math.min4(10, 5, 3, 15));
		assertEquals(1, Math.min4(10, 5, 3, 1));

	}

	@Test
	public void testMin4float() {
		assertEquals(-10.1f, Math.min4(-10.1f, 5.45f, -3.12f, 36.9f), 0.0);
		assertEquals(-5.45f, Math.min4(10.1f, -5.45f, -3.12f, 6.9f), 0.0);
		assertEquals(3.12f, Math.min4(10.1f, 5.45f, 3.12f, 36.9f),0.0);
		assertEquals(16.9f, Math.min4(100.1f, 25.45f, 23.12f, 16.9f), 0.0);
	}

}
