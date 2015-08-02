
package org.simmetrics.metrics;

import static java.lang.Math.min;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

/**
 * Overlap Coefficient algorithm providing a similarity measure between two sets
 * where it is determined to what degree a set is a subset of another.
 * <p>
 * <code>overlap_coefficient(q,r) = (|q & r|) / min{|q|, |r|}</code>
 * <p>
 * Related to the Jaccard index.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @param <T>
 *            type of the token
 * 
 * @see JaccardSimilarity
 * @see <a href="http://en.wikipedia.org/wiki/Overlap_coefficient">Wikipedia -
 *      Overlap Coefficient</a>
 */
public final class OverlapCoefficient<T> implements SetMetric<T> {

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

		final int intersection = a.size() + b.size() - all.size();
		return intersection / (float) min(a.size(), b.size());
	}

	@Override
	public String toString() {
		return "OverlapCoefficient";
	}

}
