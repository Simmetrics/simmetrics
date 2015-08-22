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
 * Measures the similarity between two strings. The measurement results in a
 * value between 0 and 1 (inclusive). A value of zero indicates that the strings
 * are dissimilar, a a value of 1 indicates they are similar.
 * <p>
 * The similarity measure should be consistent with equals such that
 * {@code a.equals(b) => compare(a,b) == 1.0}.
 * <p>
 * The similarity measure should be reflexive such that
 * {@code compare(a,a) == 1.0}.
 * <p>
 * The similarity measure should be symmetric such that
 * {@code compare(a,b) == compare(b,a)}.
 * 
 */

public interface StringMetric extends Metric<String> {
	/**
	 * Measures the similarity between strings a and b. The measurement results
	 * in a value between 0 and 1 (inclusive). A value of zero indicates that
	 * the strings are dissimilar, a value of 1 indicates they are similar.
	 * 
	 * @param a
	 *            string a to compare
	 * @param b
	 *            string b to compare
	 * @return a value between 0 and 1 inclusive indicating similarity
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	@Override
	public float compare(String a, String b);

}
