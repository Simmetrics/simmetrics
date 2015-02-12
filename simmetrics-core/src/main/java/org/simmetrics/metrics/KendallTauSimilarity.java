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

public class KendallTauSimilarity<T> implements ListMetric<T> {

	@Override
	public float compare(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		return 1 - distance(a, b) / ((a.size() * (b.size() - 1) * 0.5f));
	}

	private float distance(List<T> a, List<T> b) {
		Preconditions.checkArgument(a.size() == b.size(),
				"list must contain same elements");

		Map<T, Integer> aRank = buildRankMap(a);
		Map<T, Integer> bRank = buildRankMap(b);

		Preconditions.checkArgument(aRank.keySet().equals(bRank.keySet()),
				"list must contain same elements");

		int distance = 0;

		for (Iterator<Entry<T, T>> i = pairWiseIterator(a); i.hasNext();) {
			Entry<T, T> pair = i.next();

			T outerElement = pair.getKey();
			T innerElement = pair.getValue();

			System.out.println(pair);

			int aComparison = Integer.compare(aRank.get(outerElement),
					aRank.get(innerElement));
			int bComparison = Integer.compare(bRank.get(outerElement),
					bRank.get(innerElement));

			if (aComparison != 0 && bComparison != 0
					&& aComparison != bComparison) {
				distance++;
			}

		}

		System.out.println(distance);
		return distance;
	}

	private static <T> Iterator<Entry<T, T>> pairWiseIterator(final List<T> a) {
		return new Iterator<Entry<T, T>>() {

			private int iteration = 0;

			private final Iterator<T> outerItter = a.iterator();
			private Iterator<T> innerItter;

			private T outerElementT;

			@Override
			public boolean hasNext() {
				return (outerItter.hasNext() && iteration < a.size() - 1)
						|| innerItter.hasNext();
			}

			@Override
			public Entry<T, T> next() {

				if ((innerItter == null || !innerItter.hasNext())
						&& outerItter.hasNext()) {
					outerElementT = outerItter.next();
					innerItter = a.listIterator(++iteration);
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
		};

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
