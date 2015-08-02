
package org.simmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

import static java.lang.Math.sqrt;

/**
 * Cosine Similarity algorithm providing a similarity measure between two set
 * from the angular divergence within token based vector space.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Cosine_similarity">Wikipedia
 *      Cosine similarity</a>
 
 * @param <T>
 *            type of the token
 */
public class CosineSimilarity<T> implements SetMetric<T> {

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

		// Implementation note: Dot product of two binary vectors is the
		// intersection of two sets
		final int commonTerms = (a.size() + b.size()) - all.size();

		// Implementation note: Magnitude of a binary vectors is sqrt of its
		// size.
		return (float) (commonTerms / (sqrt(a.size()) * sqrt(b.size())));
	}

	@Override
	public String toString() {
		return "CosineSimilarity";
	}

}
