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

import java.util.Collections;
import java.util.List;

/**
 * Measures the distance between two arbitrary lists containing elements of the
 * same type. The measurement results in a value between 0 and 1 inclusive. A
 * value of zero indicates that the lists are dissimilar, a value of 1 indicates
 * they are similar.
 * <p>
 * The elements in the lists have to implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
 * <p>
 * The distance measure should be consistent with equals such that
 * {@code a.equals(b) => distance(a,b) == 0.0}.
 * <p>
 * The similarity measure should be reflexive such that
 * {@code distance(a,a) == 0.0}.
 * <p>
 * The similarity measure should be symmetric such that
 * {@code distance(a,b) == distance(b,a)}.
 * <p>
 * Implementations may not modify the contents of the list. List should be
 * treated as if wrapped by {@link Collections#unmodifiableList(List)}.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Metric_(mathematics)">Wikipedia -
 *      Metric</a>
 * 
 * @param <E>
 *            the type of elements contained in the lists
 */
public interface ListDistance<E> extends Distance<List<E>> {

	/**
	 * Measures the distance between lists a and b. The measurement results in a
	 * non-negative value. A value of {@code 0.0} indicates that {@code a} and
	 * {@code b} are similar.
	 * 
	 * @param a
	 *            list a to compare
	 * @param b
	 *            list b to compare
	 * @return a non-negative value
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	@Override
	public float distance(List<E> a, List<E> b);

}
