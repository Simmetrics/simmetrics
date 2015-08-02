package org.simmetrics;

/**
 * Measures the unnormalized similarity (distance) between two arbitrary
 * objects of the same type . The measurement results in a non-negative value. A
 * value of zero indicates that the objects are similar.
 * <p>
 * The distance measure should be consistent with equals such that
 * {@code a.equals(b) => distance(a,b) == 0.0}.
 * <p>
 * The distance measure should be reflexive such that
 * {@code distance(a,a) == 0.0}.
 * <p>
 * The distance measure should be symmetric such that
 * {@code distance(a,b) == distance(b,a)}.
 * <p>
 * The distance measure may be transitive such that
 * {@code distance(a, c) ≤ distance(a, b) + distance(b, c)}
 * <p>
 * The distance measure may satisfy the coincidence axiom such that
 * {@code compare(a, b) = 0 if and only if a.equals(b)}
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Metric_(mathematics)">Wikipedia -
 *      Metric</a>
 * 
 * @param <T>
 *            type of the elements compared
 * 
 */
public interface Distance<T> {
	/**
	 * Measures the distance between a and b. The measurement results in a
	 * non-negative value. A value of {@code 0.0} indicates that {@code a} and
	 * {@code b} are similar.
	 * 
	 * @param a
	 *            object a to compare
	 * @param b
	 *            object b to compare
	 * @return a non-negative value
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	float distance(T a, T b);

}
