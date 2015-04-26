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

package org.simmetrics.metrics.functions;

/**
 * A gap function assigns penalty to the creation of a gap in a string when
 * matching against another string.
 * <p>
 * The penalty returned must be non-positive value to be consistent with the
 * results of the substitution function.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * 
 * @see Substitution
 * @see <a href="https://en.wikipedia.org/wiki/Gap_penalty">Wikipedia - Gap
 *      Penalty</a>
 *
 */
public interface Gap {

	/**
	 * Returns the penalty for creating a gap from <code>fromIndex</code> to
	 * <code>toIndex -1</code>. The value must be non-positive.
	 * 
	 * @param fromIndex
	 *            index at which the gap starts
	 * @param toIndex
	 *            index after the last gap entry
	 * @return a gap penalty
	 */
	public float value(int fromIndex, int toIndex);

	/**
	 * Returns the minimum value a gap can have
	 * 
	 * @return the minimum value a gap can have
	 */
	public float max();

	/**
	 * Returns the maximum value a gap can have
	 * 
	 * @return the maximum value a gap can have
	 */
	public float min();
}
