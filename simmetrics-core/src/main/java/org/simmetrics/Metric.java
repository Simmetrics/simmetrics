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
 * 
 * @param <T>
 *            type of the elements compared
 * 
 */

public interface Metric<T> {
	/**
	 * Measures the similarity between a and b. The measurement results in a
	 * value between 0 and 1 inclusive. A value of {@code 0.0} indicates that a
	 * and bare dissimilar, a value of {@code 1.0} indicates they are similar.
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