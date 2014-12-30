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

import java.util.Objects;

import org.simmetrics.StringMetric;
import org.simmetrics.simplifier.PassThroughSimplifier;
import org.simmetrics.simplifier.Simplifier;

/**
 * Abstract metric that handles simplification of strings before comparing them.
 * By default strings are not simplified.
 * 
 * @author mpkorstanje
 *
 */
public abstract class SimplyfingStringMetric implements StringMetric,
		Simplifying {

	private Simplifier simplifier = new PassThroughSimplifier();

	/**
	 * Constructs a metric with.
	 */
	public SimplyfingStringMetric() {
		// Empty constructor
	}

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

	public Simplifier getSimplifier() {
		return simplifier;
	}

	public void setSimplifier(Simplifier simplifier) {
		this.simplifier = simplifier;
	}

	public void addSimplifier(SimplifyingSimplifier simplifier) {
		simplifier.setSimplifier(this.simplifier);
		this.simplifier = simplifier;
	}

	@Override
	public String toString() {
		if (simplifier instanceof PassThroughSimplifier) {
			return getClass().getName();
		}

		return getClass().getName() + " [" + Objects.toString(simplifier) + "]";
	}

}
