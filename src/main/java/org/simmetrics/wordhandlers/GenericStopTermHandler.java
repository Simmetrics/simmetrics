/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. SimMetrics is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * SimMetrics is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */

package org.simmetrics.wordhandlers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A generic stopword handler. Date: 19-Apr-2004 Time: 14:22:00
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class GenericStopTermHandler implements TermHandler {

	/**
	 * The word set the stop word handler uses.
	 */
	private final Set<String> wordSet = new HashSet<>();

	public int size() {
		return wordSet.size();
	}

	public boolean isEmpty() {
		return wordSet.isEmpty();
	}

	public boolean contains(Object o) {
		return wordSet.contains(o);
	}

	public Iterator<String> iterator() {
		return wordSet.iterator();
	}

	public Object[] toArray() {
		return wordSet.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return wordSet.toArray(a);
	}

	public boolean add(String e) {
		return wordSet.add(e);
	}

	public boolean remove(Object o) {
		return wordSet.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return wordSet.containsAll(c);
	}

	public boolean addAll(Collection<? extends String> c) {
		return wordSet.addAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return wordSet.retainAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return wordSet.removeAll(c);
	}

	public void clear() {
		wordSet.clear();
	}

	public boolean isWord(final String termToTest) {
		return wordSet.contains(termToTest);
	}


}
