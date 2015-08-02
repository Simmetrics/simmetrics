

package org.simmetrics.utils;

import org.simmetrics.StringMetricBuilder;
import org.simmetrics.simplifiers.Simplifier;

/**
 * A simplifier that delegates the work to another simplifier.
 * 
 * @see StringMetricBuilder
 * 
 * 
 *
 */
public interface SimplifyingSimplifier extends Simplifier, Simplifying {

	// Empty: because composite interface

}
