/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package org.simmetrics.metrics;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetrics;
import org.simmetrics.simplifier.CachingSimplifier;
import org.simmetrics.simplifier.Simplifier;

/**
 * Interface for classes that use a {@link Simplifier} to normalize strings.
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
