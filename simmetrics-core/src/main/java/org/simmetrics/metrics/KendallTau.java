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
package org.simmetrics.metrics;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import org.simmetrics.ListMetric;

import com.google.common.base.Preconditions;

/**
 * Kendall tau rank distance is a metric that counts the number of pairwise
 * disagreements between two ranking lists. The larger the distance, the more
 * dissimilar the two lists are.
 * <p>
 * Kendall tau distance is also called bubble-sort distance since it is
 * equivalent to the number of swaps that the bubble sort algorithm would make
 * to place one list in the same order as the other list.
 * <p>
 * The Kendall tau distance and similarity is transitive. 
 * <p>
 * The elements in the lists have to implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
 * <p>
 * 
 * Note that this is not a {@link ListMetric} because it does not accept
 * arbitrary lists. The compared lists have to contain the same elements and a
 * list may not contain duplicate elements.
 * <p>
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Kendall_tau_distance">Wikipedia -
 *      Kendall tau distance</a>
 * 
 * @author mpkorstanje
 *
 * @param <T>
 *            the type of elements contained in the lists
 */
public final class KendallTau<T> {

	private final class UniquePairIterator<S> implements Iterator<Entry<S, S>> {
		private final List<S> a;
		private int iteration = 0;
		private final Iterator<S> outerItter;
		private Iterator<S> innerItter;
		private S outerElementT;

		UniquePairIterator(List<S> a) {
			this.a = a;
			outerItter = a.iterator();
		}

		@Override
		public boolean hasNext() {
			return outerItter.hasNext() || innerItter.hasNext();
		}

		@Override
		public Entry<S, S> next() {

			if ((innerItter == null || !innerItter.hasNext())
					&& outerItter.hasNext()) {
				outerElementT = outerItter.next();
				innerItter = a.listIterator(iteration++);
			}

			if (innerItter.hasNext()) {
				return new AbstractMap.SimpleEntry<>(outerElementT,
						innerItter.next());
			}

			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}
	}

	/**
	 * Measures the similarity between two different orderings of the same list.
	 * <p>
	 * The measurement results in a value between 0 and 1 inclusive. A value of
	 * zero indicates that the lists are dissimilar, a value of 1 indicates they
	 * are similar.
	 * <p>
	 * The input lists have to be of the same size and contain the same
	 * elements. The lists may also not contain duplicate elements.
	 * 
	 * @param a
	 *            list a with an ordering of elements
	 * @param b
	 *            list b with an ordering of elements
	 * @return a value between 0 and 1 inclusive indicating similarity between
	 *         orderings
	 * @throws IllegalArgumentException
	 *             when the lists have different sizes, when they do not contain
	 *             the same elements, or a list contains duplicate elements
	 */
	public float compare(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		return 1 - distance(a, b) / ((a.size() * (b.size() - 1) * 0.5f));
	}

	/**
	 * Measures the Kendall tau rank distance between two different orderings of
	 * the same lists.
	 * <p>
	 * The measurement results in a value between 0 and 1 inclusive. A value of
	 * zero indicates that the lists are dissimilar, a value of 1 indicates they
	 * are similar.
	 * <p>
	 * The input lists have to be of the same size and contain the same
	 * elements.
	 * 
	 * @param a
	 *            list a with an ordering of elements
	 * @param b
	 *            list b with an ordering of elements
	 * @return a positive value indicating the distance between both orderings
	 * @throws IllegalArgumentException
	 *             when the lists have different sizes, when they do not contain
	 *             the same elements, or a list contains duplicate elements
	 */
	public float distance(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 0.0f;
		}

		Preconditions.checkArgument(a.size() == b.size(),
				"list must contain same elements");

		Map<T, Integer> aRank = buildRankMap(a);
		Map<T, Integer> bRank = buildRankMap(b);

		Preconditions.checkArgument(aRank.keySet().equals(bRank.keySet()),
				"list must contain same elements");

		int distance = 0;

		// Using iterator here because List.get() may not be efficient
		for (Iterator<Entry<T, T>> i = new UniquePairIterator<>(a); i.hasNext();) {
			Entry<T, T> pair = i.next();

			T outerElement = pair.getKey();
			T innerElement = pair.getValue();

			int aComparison = Integer.compare(aRank.get(outerElement),
					aRank.get(innerElement));
			int bComparison = Integer.compare(bRank.get(outerElement),
					bRank.get(innerElement));

			if (aComparison != 0 && bComparison != 0
					&& aComparison != bComparison) {
				distance++;
			}

		}

		return distance;
	}

	private Map<T, Integer> buildRankMap(List<T> a) {
		Map<T, Integer> rankA = new HashMap<>();
		int aRank = 0;
		for (T aElement : a) {
			rankA.put(aElement, aRank++);
		}
		return rankA;
	}

}
