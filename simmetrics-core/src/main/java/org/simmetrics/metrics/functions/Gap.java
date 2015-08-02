

package org.simmetrics.metrics.functions;

/**
 * A gap function assigns penalty to the creation of a gap in a string when
 * matching against another string.
 * <p>
 * The penalty returned must be non-positive value to be consistent with the
 * results of the substitution function.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * 
 * @see Substitution
 * @see <a href="https://en.wikipedia.org/wiki/Gap_penalty">Wikipedia - Gap
 *      Penalty</a>
 *
 */
public interface Gap {

	/**
	 * Returns the penalty for creating a gap from <code>fromIndex</code> to
	 * <code>toIndex -1</code>. The value must be non-positive.
	 * 
	 * @param fromIndex
	 *            index at which the gap starts
	 * @param toIndex
	 *            index after the last gap entry
	 * @return a gap penalty
	 */
	public float value(int fromIndex, int toIndex);

	/**
	 * Returns the minimum value a gap can have
	 * 
	 * @return the minimum value a gap can have
	 */
	public float max();

	/**
	 * Returns the maximum value a gap can have
	 * 
	 * @return the maximum value a gap can have
	 */
	public float min();
}
