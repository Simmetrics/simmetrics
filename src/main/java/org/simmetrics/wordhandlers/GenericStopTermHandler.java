/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
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
	private final Set<String> wordSet = new HashSet<String>();

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
