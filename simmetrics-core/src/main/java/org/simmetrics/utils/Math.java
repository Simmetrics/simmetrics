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
	 * Returns the maximum of x,y and z.
	 *
	 * @param x
	 *            x-value
	 * @param y
	 *            y-value
	 * @param z
	 *            z-value
	 * @return the maximum of x,y and z
	 */
	public static float max3(final float x, final float y, final float z) {
		return max(x, max(y, z));
	}

	/**
	 * Returns the maximum of x,y and z.
	 * 
	 * @param x
	 *            x-value
	 * @param y
	 *            y-value
	 * @param z
	 *            z-value
	 * @return the maximum of x,y and z
	 */
	public static int max3(final int x, final int y, final int z) {
		return max(x, max(y, z));
	}

	/**
	 * Returns the maximum of w,x,y and z.
	 * 
	 * @param w
	 *            w-value
	 * @param x
	 *            x-value
	 * @param y
	 *            y-value
	 * @param z
	 *            z-value
	 * @return the maximum of w,x,y and z
	 * 
	 */
	public static float max4(final float w, final float x, final float y,
			final float z) {
		return max(max(w, x), max(y, z));
	}

	/**
	 * Returns the maximum of w,x,y and z.
	 * 
	 * @param w
	 *            w-value
	 * @param x
	 *            x-value
	 * @param y
	 *            y-value
	 * @param z
	 *            z-value
	 * @return the maximum of w,x,y and z
	 * 
	 */
	public static int max4(final int w, final int x, final int y, final int z) {
		return max(max(w, x), max(y, z));
	}

	/**
	 * Returns the minimum of x,y and z.
	 * 
	 * @param x
	 *            x-value
	 * @param y
	 *            y-value
	 * @param z
	 *            z-value
	 * @return the minimum of x,y and z
	 */
	public static float min3(final float x, final float y, final float z) {
		return min(x, min(y, z));
	}

	/**
	 * Returns the minimum of x,y and z.
	 * 
	 * @param x
	 *            x-value
	 * @param y
	 *            y-value
	 * @param z
	 *            z-value
	 * @return the minimum of x,y and z
	 */
	public static int min3(final int x, final int y, final int z) {
		return min(x, min(y, z));
	}

	/**
	 * Returns the minimum of w,x,y and z.
	 * 
	 * @param w
	 *            w-value
	 * @param x
	 *            x-value
	 * @param y
	 *            y-value
	 * @param z
	 *            z-value
	 * @return the maximum of w,x,y and z
	 * 
	 */
	public static float min4(final float w, final float x, final float y,
			final float z) {
		return min(min(w, x), min(y, z));
	}

	/**
	 * Returns the minimum of w,x,y and z.
	 * 
	 * @param w
	 *            w-value
	 * @param x
	 *            x-value
	 * @param y
	 *            y-value
	 * @param z
	 *            z-value
	 * @return the maximum of w,x,y and z
	 * 
	 */
	public static int min4(final int w, final int x, final int y, final int z) {
		return min(min(w, x), min(y, z));
	}

	/**
	 * Clamps x between the upper and lower bounds. The returned value will be
	 * no lower then the lower bound and no higher then the upper bound. If x
	 * falls between the upper and lower bound x is returned.
	 * 
	 * 
	 * @param lower
	 *            lower bound for x
	 * @param x
	 *            x-value
	 * @param upper
	 *            upper bound for x
	 * @return the value of x clamped between the upper and lower bounds.
	 */
	public static int clamp(int lower, int x, int upper) {
		return min(max(lower, x), upper);
	}
}
