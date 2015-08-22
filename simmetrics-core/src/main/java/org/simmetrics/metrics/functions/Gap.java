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
	 * Returns the minimum value a gap can have.
	 * 
	 * @return the minimum value a gap can have
	 */
	public float max();

	/**
	 * Returns the maximum value a gap can have.
	 * 
	 * @return the maximum value a gap can have
	 */
	public float min();
}
