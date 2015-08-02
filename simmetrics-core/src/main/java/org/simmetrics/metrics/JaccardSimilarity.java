
package org.simmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

/**
 * Jaccard similarity algorithm providing a similarity measure between two sets
 * using the vector space of presented tokens.
 * 
 * <p>
 * <code>jaccard index(a,b) = ( |a & b| ) / ( | a or b | )</code>
 * <p>
 * 
 * This metric is identical to the matching coefficient which operates on lists.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Jaccard_index">Wikipedia - Jaccard
 *      index</a>
 * 
 * 
 * @param <T>
 *            type of the token
 * 
 */
public final class JaccardSimilarity<T> implements SetMetric<T> {

	@Override
	public float compare(Set<T> a, Set<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		final Set<T> all = new HashSet<>(a.size() + b.size());
		all.addAll(a);
		all.addAll(b);

		final int intersection = (a.size() + b.size()) - all.size();

		// return JaccardSimilarity
		return intersection / (float) all.size();
	}

	@Override
	public String toString() {
		return "JaccardSimilarity";
	}

}
