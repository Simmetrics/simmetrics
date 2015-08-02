package org.simmetrics.simplifiers;

import org.simmetrics.StringMetricBuilder;

/**
 * Transforms a string into a simpler form.
 * 
 * <p>
 * Simplification increases the effectiveness of a metric by removing noise and
 * reducing the dimensionality of the problem. The process maps a a complex
 * string such as <code>Chilpéric II son of Childeric II</code> to a simpler
 * format <code>chilperic ii son of childeric ii</code>. This allows string from
 * different sources to be compared in the same normal form.
 * 
 * <p>
 * A simplifier can be added onto a metric through the
 * {@link StringMetricBuilder}.
 * 
 * 
 *
 */
public interface Simplifier {

	/**
	 * Simplifiers the input string.
	 * 
	 * @param input
	 *            string to simplify
	 * @return a simplified string
	 */
	public String simplify(String input);

}
