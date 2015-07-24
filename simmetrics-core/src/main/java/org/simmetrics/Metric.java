package org.simmetrics;

/**
 * Measures the similarity between two arbitrary objects of the same type . The
 * measurement results in a value between 0 and 1 inclusive. A value of zero
 * indicates that the sets are dissimilar, a value of 1 indicates they are
 * similar.
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
 * The similarity measure may be transitive such that
 * {@code 1.0 - compare(a, c) ≤ 1.0 - compare(a, b) + 1.0 - compare(b, c)}
 * <p>
 * The similarity measure may satisfy the coincidence axiom such that
 * {@code compare(a, b) = 0 if and only if a.equals(b)}
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Metric_(mathematics)">Wikipedia -
 *      Metric</a>
 * 
 * @param <T>
 *            type of the elements compared
 * 
 */

public interface Metric<T> {
	/**
	 * Measures the similarity between a and b. The measurement results in a
	 * value between 0 and 1 inclusive. A value of {@code 0.0} indicates that
	 * {@code a} and {@code b} are dissimilar, a value of {@code 1.0} indicates
	 * they are similar.
	 * 
	 * @param a
	 *            object a to compare
	 * @param b
	 *            object b to compare
	 * @return a value between 0 and 1 inclusive indicating similarity
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	public float compare(T a, T b);

}