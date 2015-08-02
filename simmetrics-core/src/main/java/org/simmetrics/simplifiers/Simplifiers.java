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

package org.simmetrics.simplifiers;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.asList;

import java.util.ArrayList;
import java.util.List;

/**
 * Simplifier composed of multiple simplifiers.
 * <p>
 * This class is immutable and thread-safe if its components are.
 */
public final class Simplifiers {

	private static final class ChainSimplifier implements Simplifier {

		private final List<Simplifier> simplifiers;

		ChainSimplifier(List<Simplifier> simplifiers) {
			checkArgument(!simplifiers.contains(null));
			this.simplifiers = new ArrayList<>(simplifiers);
		}
		
		List<Simplifier> getSimplifiers() {
			return simplifiers;
		}

		@Override
		public String simplify(String input) {
			checkNotNull(input);
			for (Simplifier s : simplifiers) {
				input = s.simplify(input);
			}

			return input;

		}

		@Override
		public String toString() {
			return on(" -> ").join(simplifiers);
		}
	}

	/**
	 * Constructs a new chain of simplifiers. Applies the simplifiers in order.
	 * 
	 * @param simplifiers
	 *            a non-empty list of simplifiers
	 * @return a new composite simplifier
	 */
	public static Simplifier chain(List<Simplifier> simplifiers) {
		if (simplifiers.size() == 1) {
			return simplifiers.get(0);
		}
		return new ChainSimplifier(flatten(simplifiers));
	}

	/**
	 * Constructs a new chain of simplifiers. Applies the simplifiers in order.
	 * 
	 * @param simplifier
	 *            the first simplifier
	 * @param simplifiers
	 *            the others
	 * @return a new composite simplifier
	 */
	public static Simplifier chain(Simplifier simplifier,
			Simplifier... simplifiers) {
		checkArgument(simplifier != null);
		if(simplifiers.length == 0){
			return simplifier;
		}
		
		return chain(asList(simplifier, simplifiers));
	}

	private static List<Simplifier> flatten(List<Simplifier> simplifiers) {
		final List<Simplifier> flattend = new ArrayList<>(simplifiers.size());

		for (Simplifier s : simplifiers) {
			if (s instanceof ChainSimplifier) {
				final ChainSimplifier c = (ChainSimplifier) s;
				flattend.addAll(c.getSimplifiers());
			} else {
				flattend.add(s);
			}
		}

		return flattend;
	}

	private Simplifiers() {
		// Utility class
	}

}
