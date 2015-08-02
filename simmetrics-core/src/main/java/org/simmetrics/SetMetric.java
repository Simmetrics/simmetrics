

package org.simmetrics;

import java.util.Collections;
import java.util.Set;

/**
 * Measures the similarity between two arbitrary sets containing elements of the
 * same type. The measurement results in a value between 0 and 1 inclusive. A
 * value of zero indicates that the sets are dissimilar, a value of 1 indicates
 * they are similar.
 * 
 * <p>
 * The elements in the set have to implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)}.
 * <p>
 * The similarity measure should be consistent with equals such that
 * {@code a.equals(b) => compare(a,b) == 1.0}. 
 * <p>
 * The similarity measure should be reflexive such that
 * {@code compare(a,a) == 1.0}.
 * <p>
 * The similarity measure should be symmetric such that
 * {@code compare(a,b) == compare(b,a)}.
 * <p>
 * Implementations may not modify the contents of the set. Sets should be
 * treated as if wrapped by {@link Collections#unmodifiableSet(Set)}.
 * 
 * @param <T>
 *            the type of elements contained in the sets
 *
 */
public interface SetMetric<T> extends Metric<Set<T>> {
	/**
	 * Measures the similarity between sets a and b. The measurement results in
	 * a value between 0 and 1 inclusive. A value of zero indicates that the
	 * sets are dissimilar, a value of 1 indicates they are similar.
	 * 
	 * @param a
	 *            set a to compare
	 * @param b
	 *            set b to compare
	 * @return a value between 0 and 1 inclusive indicating similarity
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	@Override
	public float compare(Set<T> a, Set<T> b);

}
