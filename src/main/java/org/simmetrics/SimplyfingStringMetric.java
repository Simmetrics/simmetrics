/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.simmetrics;

import uk.ac.shef.wit.simmetrics.simplifier.PassThroughSimplifier;
import uk.ac.shef.wit.simmetrics.simplifier.Simplifier;

/**
 * Abstract metric that handles simplification of strings before comparing them.
 * By default strings are not simplified.
 * 
 * @author mpkorstanje
 *
 */
public abstract class SimplyfingStringMetric implements StringMetric,
		Simplifying {

	/**
	 * Constructs a metric with the given simplifier. The simplifier is used on
	 * the strings before they are compared.
	 * 
	 * @param simplifier
	 *            simplifier to use to simplify strings before comparing them
	 */
	public SimplyfingStringMetric(Simplifier simplifier) {
		this.simplifier = simplifier;
	}

	/**
	 * Constructs a metric with.
	 */
	public SimplyfingStringMetric() {
		// Empty constructor
	}

	@Override
	public String toString() {
		if (simplifier instanceof PassThroughSimplifier) {
			return getClass().getSimpleName();
		}

		return getClass().getSimpleName() + " [" + simplifier + "]";
	}

	private Simplifier simplifier = new PassThroughSimplifier();

	public void setSimplifier(Simplifier simplifier) {
		this.simplifier = simplifier;
	}

	public Simplifier getSimplifier() {
		return simplifier;
	}

	public float compare(String a, String b) {
		return compareSimplified(simplifier.simplify(a), simplifier.simplify(b));
	}

	/**
	 * Measures the similarity between simplified strings a and b. The
	 * measurement results in a value between 0 and 1. A value of zero indicates
	 * that the strings are dissimilar, a a value of 1 indicates they are
	 * similar.
	 * 
	 * @param a
	 *            simplified string a to compare
	 * @param b
	 *            simplified string b to compare
	 * @return a value between 0 and 1 indicating similarity
	 */
	protected abstract float compareSimplified(String a, String b);

}
