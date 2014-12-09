/*
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

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * MathFuncs implements a number of handy maths functions.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public abstract class Math {

	/**
	 * Returns the max of three numbers.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return the max of three numbers
	 */
	public static float max3(final float x, final float y, final float z) {
		return max(x, max(y, z));
	}

	/**
	 * Returns the max of three numbers.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return the max of three numbers
	 */
	public static int max3(final int x, final int y, final int z) {
		return max(x, max(y, z));
	}

	/**
	 * Returns the max of four numbers.
	 *
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @return the max of four numbers
	 */
	public static float max4(final float w, final float x, final float y,
			final float z) {
		return max(max(w, x), max(y, z));
	}

	/**
	 * Returns the max of four numbers.
	 *
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @return the max of four numbers
	 */
	public static int max4(final int w, final int x, final int y, final int z) {
		return max(max(w, x), max(y, z));
	}

	/**
	 * Returns the min of three numbers.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return the min of three numbers
	 */
	public static float min3(final float x, final float y, final float z) {
		return min(x, min(y, z));
	}

	/**
	 * Returns the min of three numbers.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return the min of three numbers
	 */
	public static int min3(final int x, final int y, final int z) {
		return min(x, min(y, z));
	}

	/**
	 * Returns the min of four numbers.
	 *
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @return the min of four numbers
	 */
	public static float min4(final float w, final float x, final float y,final float z) {
		return min(min(w, x), min(y, z));
	}

	/**
	 * Returns the min of four numbers.
	 *
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @return the min of four numbers
	 */
	public static int min4(final int w, final int x, final int y, final int z) {
		return min(min(w, x), min(y, z));
	}

	public static int clamp(int lower, int x, int upper) {
		return min(max(lower,x),upper);
	}
}
