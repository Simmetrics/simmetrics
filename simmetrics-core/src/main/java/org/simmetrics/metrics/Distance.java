package org.simmetrics.metrics;
/**
 * Measures the distance between two arbitrary objects of the same type . The
 * measurement results in a positive value. A value of zero
 * indicates that the sets are similar.
 * <p>
 * The distance measure should be consistent with equals such that
 * {@code a.equals(b) => compare(a,b) == 0.0}.
 * <p>
 * The distance measure should be reflexive such that
 * {@code compare(a,a) == 0.0}.
 * <p>
 * The distance measure should be symmetric such that
 * {@code compare(a,b) == compare(b,a)}.
 * 
 * @param <T>
 *            type of the elements compared
 * 
 */
interface Distance<T> {
	/**
	 * Measures the distance between a and b. The measurement results in a
	 * positive value. A value of {@code 0.0} indicates that {@code a} and
	 * {@code b} are similar.
	 * 
	 * @param a
	 *            object a to compare
	 * @param b
	 *            object b to compare
	 * @return a positive value
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	float distance(T a, T b);

}
