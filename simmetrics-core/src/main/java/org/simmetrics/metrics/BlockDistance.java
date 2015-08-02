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
package org.simmetrics.metrics;

import static java.util.Collections.frequency;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.ListDistance;
import org.simmetrics.ListMetric;

import static java.lang.Math.abs;

/**
 * Block distance algorithm whereby vector space block distance between tokens
 * is used to determine a similarity. Also known as L1 Distance or City block
 * distance.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Taxicab_geometry">Wikipedia -
 *      Taxicab geometry</a>
 * @param <T>
 *            type of token
 */
public class BlockDistance<T> implements ListMetric<T>, ListDistance<T> {

	@Override
	public float compare(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		return 1.0f - distance(a, b) / (a.size() + b.size());
	}

	@Override
	public float distance(final List<T> a, final List<T> b) {
		final Set<T> all = new HashSet<>(a.size() + b.size());
		all.addAll(a);
		all.addAll(b);

		int totalDistance = 0;
		for (T token : all) {
			int frequencyInA = frequency(a, token);
			int frequencyInB = frequency(b, token);

			totalDistance += abs(frequencyInA - frequencyInB);
		}
		return totalDistance;
	}

	@Override
	public String toString() {
		return "BlockDistance";
	}

}
