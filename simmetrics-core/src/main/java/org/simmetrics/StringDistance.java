package org.simmetrics;

/**
 * Measures the unnormalized similarity (distance) between two Strings. The
 * measurement results in a non-negative value. A value of zero indicates that
 * the objects are similar.
 * <p>
 * The distance measure should be consistent with equals such that
 * {@code a.equals(b) =>
 * distance(a,b) == 0.0}.
 * <p>
 * The distance measure should be reflexive such that
 * {@code distance(a,a) == 0.0}.
 * <p>
 * The distance measure should be symmetric such that
 * {@code distance(a,b) == distance(b,a)}.
 * <p>
 * The distance measure may be transitive such that
 * {@code distance(a, c) ≤ distance(a, b) +
 * distance(b, c)}
 * <p>
 * The distance measure may satisfy the coincidence axiom such that
 * {@code compare(a, b) = 0 if and only if a.equals(b)}
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Metric_(mathematics)">Wikipedia -
 *      Metric</a>
 */
public interface StringDistance extends Distance<String> {
	/**
	 * Measures the distance between string a and b. The measurement results in a
	 * non-negative value. A value of {@code 0.0} indicates that {@code a} and
	 * {@code b} are similar.
	 * 
	 * @param a
	 *            string a to compare
	 * @param b
	 *            string b to compare
	 * @return a non-negative value
	 * @throws NullPointerException
	 *             when either a or b is null
	 */
	@Override
	float distance(String a, String b);
}
