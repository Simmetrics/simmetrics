/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.simmetrics;

/**
 * Measures the unnormalized similarity (distance) between two Strings. The
 * measurement results in a non-negative value. A value of zero indicates that
 * the objects are similar.
 * <p>
 * The distance measure should be consistent with equals such that
 * {@code a.equals(b) =>
 * distance(a,b) == 0.0}.
 * <p>
 * The distance measure should be reflexive such that
 * {@code distance(a,a) == 0.0}.
 * <p>
 * The distance measure should be symmetric such that
 * {@code distance(a,b) == distance(b,a)}.
 * <p>
 * The distance measure may be transitive such that
 * {@code distance(a, c) ≤ distance(a, b) +
 * distance(b, c)}
 * <p>
 * The distance measure may satisfy the coincidence axiom such that
 * {@code compare(a, b) = 0 if and only if a.equals(b)}
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Metric_(mathematics)">Wikipedia -
 *      Metric</a>
 */
public interface StringDistance extends Distance<String> {
	/**
	 * Measures the distance between string a and b. The measurement results in a
	 * non-negative value. A value of {@code 0.0} indicates that {@code a} and
	 * {@code b} are similar.
	 * 
	 * @param a
	 *            string a to compare
	 * @param b
	 *            string b to compare
	 * @return a non-negative value
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	@Override
	float distance(String a, String b);
}
