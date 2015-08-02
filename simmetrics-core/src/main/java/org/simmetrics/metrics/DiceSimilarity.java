
package org.simmetrics.metrics;

import java.util.HashSet;
import java.util.Set;

import org.simmetrics.SetMetric;

/**
 * Dice similarity algorithm providing a similarity measure between two sets
 * using the vector space of presented tokens.
 * <p>
 * <code>dices coefficient (a,b) = (2 * |a & b |) / (|a|  + |b|)</code>
 * <p>
 * This metric is identical to Simon White which operates on lists.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see SimonWhite
 * @see <a
 *      href="http://en.wikipedia.org/wiki/S%C3%B8rensen%E2%80%93Dice_coefficient">Wikipedia
 *      - Sørensen–Dice coefficient</a>
 * 
 * 
 * @param <T>
 *            type of the token
 */
public class DiceSimilarity<T> implements SetMetric<T> {

	@Override
	public float compare(Set<T> a, Set<T> b) {

		if (a.isEmpty() && b.isEmpty()) {
			return 1.0f;
		}

		if (a.isEmpty() || b.isEmpty()) {
			return 0.0f;
		}

		final int totalSize = a.size() + b.size();
		final Set<T> all = new HashSet<>(totalSize);
		all.addAll(a);
		all.addAll(b);

		final int commonTerms = totalSize - all.size();

		// return Dices coefficient = (2*Common Terms) / (Number of distinct
		// terms in String1 + Number of distinct terms in String2)
		return (2.0f * commonTerms) / totalSize;
	}

	@Override
	public String toString() {
		return "DiceSimilarity";
	}

}
