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
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */

package org.simmetrics.utils;

/**
 * The class {@code Math} contains methods for performing useful functions.
 */
public final class Math {

	private Math() {
		// Utility class
	}

	/**
	 * Returns the greater of three {@code float} values. That is, the result is
	 * the argument closer to positive infinity. If the arguments have the same
	 * value, the result is that same value. If any value is {@code NaN}, then
	 * the result is {@code NaN}. Unlike the numerical comparison operators,
	 * this method considers negative zero to be strictly smaller than positive
	 * zero.
	 *
	 * @param a
	 *            an argument
	 * @param b
	 *            an other argument
	 * @param c
	 *            an other argument
	 * @return the larger of {@code a}, {@code b} and {@code c}.
	 */
	public static float max(float a, float b, float c) {
		return java.lang.Math.max(java.lang.Math.max(a, b), c);
	}

	/**
	 * Returns the greater of three {@code int} values. That is, the result is
	 * the argument closer to the value of {@link Integer#MAX_VALUE}. If the
	 * arguments have the same value, the result is that same value.
	 *
	 * @param a
	 *            an argument
	 * @param b
	 *            an other argument
	 * @param c
	 *            an other argument
	 * @return the larger of {@code a}, {@code b} and {@code c}.
	 */
	public static int max(final int a, final int b, final int c) {
		return java.lang.Math.max(java.lang.Math.max(a, b), c);
	}

	/**
	 * Returns the greater of four {@code float} values. That is, the result is
	 * the argument closer to positive infinity. If the arguments have the same
	 * value, the result is that same value. If any value is {@code NaN}, then
	 * the result is {@code NaN}. Unlike the numerical comparison operators,
	 * this method considers negative zero to be strictly smaller than positive
	 * zero.
	 *
	 * @param a
	 *            an argument
	 * @param b
	 *            an other argument
	 * @param c
	 *            an other argument
	 * @param d
	 *            an other argument
	 * @return the larger of {@code a}, {@code b}, {@code c} and {@code d}.
	 */
	public static float max(float a, float b, float c, float d) {
		return java.lang.Math.max(
				java.lang.Math.max(java.lang.Math.max(a, b), c), d);
	}

	/**
	 * Returns the greater of four {@code int} values. That is, the result is
	 * the argument closer to the value of {@link Integer#MAX_VALUE}. If the
	 * arguments have the same value, the result is that same value.
	 *
	 * @param a
	 *            an argument
	 * @param b
	 *            an other argument
	 * @param c
	 *            an other argument
	 * @param d
	 *            an other argument
	 * @return the larger of {@code a}, {@code b}, {@code c} and {@code d}.
	 */
	public static int max(final int a, final int b, final int c, final int d) {
		return java.lang.Math.max(
				java.lang.Math.max(java.lang.Math.max(a, b), c), d);
	}

	/**
	 * Returns the smaller of three {@code float} values. That is, the result is
	 * the value closer to negative infinity. If the arguments have the same
	 * value, the result is that same value. If any value is NaN, then the
	 * result is NaN. Unlike the numerical comparison operators, this method
	 * considers negative zero to be strictly smaller than positive zero.
	 *
	 * @param a
	 *            an argument
	 * @param b
	 *            an other argument
	 * @param c
	 *            an other argument
	 * 
	 * @return the smaller of {@code a}, {@code b} and {@code c}.
	 */
	public static float min(float a, float b, float c) {
		return java.lang.Math.min(java.lang.Math.min(a, b), c);
	}

	/**
	 * Returns the smaller of three {@code int} values. That is, the result the
	 * argument closer to the value of {@link Integer#MIN_VALUE}. If the
	 * arguments have the same value, the result is that same value.
	 *
	 * @param a
	 *            an argument
	 * @param b
	 *            an other argument
	 * @param c
	 *            an other argument
	 * 
	 * @return the smaller of {@code a}, {@code b} and {@code c}.
	 */
	public static int min(final int a, final int b, final int c) {
		return java.lang.Math.min(java.lang.Math.min(a, b), c);
	}

	/**
	 * Returns the smaller of four {@code float} values. That is, the result is
	 * the value closer to negative infinity. If the arguments have the same
	 * value, the result is that same value. If any value is NaN, then the
	 * result is NaN. Unlike the numerical comparison operators, this method
	 * considers negative zero to be strictly smaller than positive zero.
	 *
	 * @param a
	 *            an argument
	 * @param b
	 *            an other argument
	 * @param c
	 *            an other argument
	 * @param d
	 *            an other argument
	 * @return the smaller of {@code a}, {@code b}, {@code c} and {@code d}.
	 */
	public static float min(final float a, final float b, final float c,
			final float d) {
		return java.lang.Math.min(
				java.lang.Math.min(java.lang.Math.min(a, b), c), d);
	}

	/**
	 * Returns the smaller of four {@code int} values. That is, the result the
	 * argument closer to the value of {@link Integer#MIN_VALUE}. If the
	 * arguments have the same value, the result is that same value.
	 *
	 * @param a
	 *            an argument
	 * @param b
	 *            an other argument
	 * @param c
	 *            an other argument
	 * @param d
	 *            an other argument
	 * @return the smaller of {@code a}, {@code b} and {@code c}.
	 */
	public static int min(final int a, final int b, final int c, final int d) {
		return java.lang.Math.min(
				java.lang.Math.min(java.lang.Math.min(a, b), c), d);
	}
}
