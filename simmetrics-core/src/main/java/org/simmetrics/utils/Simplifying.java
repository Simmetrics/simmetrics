
package org.simmetrics.utils;

import org.simmetrics.simplifiers.Simplifier;

/**
 * Interface for classes that delegate to a {@link Simplifier}.
 * 
 * 
 *
 */
public interface Simplifying {

	/**
	 * Sets the simplifier. May not be null.
	 * 
	 * @param simplifier
	 *            a simplifier to set
	 */
	public void setSimplifier(Simplifier simplifier);

	/**
	 * Gets the simplifier. When null no simplifier was set.
	 * 
	 * @return the simplifier or null when not set
	 */
	public Simplifier getSimplifier();

}
