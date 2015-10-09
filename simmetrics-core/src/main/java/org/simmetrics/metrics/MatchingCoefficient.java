/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.simmetrics.metrics;

import static com.google.common.collect.HashMultiset.create;

import java.util.List;

import org.simmetrics.ListMetric;

import com.google.common.collect.Multiset;

/**
 * Matching coefficient algorithm providing a similarity measure between two
 * lists.
 * <p>
 * The matching coefficient between two lists is defined as ratio of elements
 * that occur in both lists and elements that exclusively occur in either list.
 * This metric is identical to Jaccard similarity. However repeated elements are
 * considered as distinct occurrences.
 * 
 * <p>
 * <code>
 * similarity(a,b) = (a A b)|  / (|a or b|)
 * </code>
 * 
 * <p>
 * The A operation takes the list intersection of <code>a</code> and
 * <code>b</code>. This is a list <code>c</code> such that each element in has a
 * 1-to-1 relation to an element in both <code>a</code> and <code>b</code>. E.g.
 * the list intersection of <code>[ab,ab,ab,ac]</code> and
 * <code>[ab,ab,ad]</code> is <code>[ab,ab]</code>. *
 * <p>
 * This metric is identical to Jaccard but is insensitive to repeated tokens.
 * The list <code>["a","a","b"]</code> is identical to
 * <code>["a","b","b"]</code>. 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see JaccardSimilarity
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Simple_matching_coefficient">Wikipedia
 *      - Simple Matching Coefficient</a>
 * 
 * 
 * 
 * @param <T>
 *            type of the token
 * 
 */
public class MatchingCoefficient<T> implements ListMetric<T> {
	@Override
	public float compare(List<T> a, List<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		// Count elements in the list intersection.
		// Elements are counted only once in both lists.
		// E.g. the intersection of [ab,ab,ab] and [ab,ab,ac,ad] is [ab,ab].
		// Note: this is not the same as b.retainAll(a).size()
		int intersection = 0;
		// Copy for destructive list difference
		Multiset<T> bCopy = create(b);
		for (T token : a) {
			if (bCopy.remove(token)) {
				intersection++;
			}
		}
		// Implementation note: The size of the union of two sets is equal to
		// the size of both lists minus the duplicate elements.
		return intersection / (float) (a.size() + b.size() - intersection);
	}

	@Override
	public String toString() {
		return "MatchingCoefficient";
	}

}
