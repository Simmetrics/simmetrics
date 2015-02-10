/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.utils;

import org.simmetrics.simplifiers.Simplifier;

/**
 * Interface for classes that delegate to a {@link Simplifier}.
 * 
 * @author mpkorstanje
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
