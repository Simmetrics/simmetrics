package org.simmetrics;

import org.simmetrics.simplifier.CachingSimplifier;
import org.simmetrics.simplifier.Simplifier;

/**
 * Interface for metrics that use a {@link Simplifier} to normalize strings.
 * These methods can be called by batching-processing systems to inject a
 * {@link CachingSimplifier}s.
 * 
 * For the benefit of these caching systems, implementations should take care to
 * call the simplifier exactly once for each argument.
 * 
 * <pre>
 * <code>
 * 
 * 	public float compare(String a, String b) {
 * 		return compareSimplified(simplifier.simplify(a), simplifier.simplify(b));
 * 	}
 * 
 * </code>
 * </pre>
 * 
 * 
 * @see StringMetrics#compare(StringMetric, String, String...)
 * @see StringMetrics#compare(StringMetric, String, java.util.List)
 * 
 * @author mpkorstanje
 *
 */
public interface Simplifying {

	/**
	 * Sets the simplifier. The simplifier may not be null.
	 * 
	 * @param simplifier
	 *            a non null simplifier to set
	 */
	public void setSimplifier(Simplifier simplifier);

	/**
	 * Gets the simplifier. The simplifier may not be null.
	 * 
	 * @return the a non-null simplifier
	 */
	public Simplifier getSimplifier();

}
