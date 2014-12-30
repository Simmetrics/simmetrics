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

package org.simmetrics.metrics.costfunctions;

/**
 * SubCost01 implements a substitution cost function where
 * 
 * d(i,j) = 1 if i does not equals j, 0 if i equals j.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
final public class SubCost01 extends AbstractSubstitutionCost {

	public final float getCost(final String str1, final int string1Index,
			final String str2, final int string2Index) {
		if (str1.charAt(string1Index) == str2.charAt(string2Index)) {
			return 0.0f;
		} else {
			return 1.0f;
		}
	}

	public final float getMaxCost() {
		return 1.0f;
	}

	public final float getMinCost() {
		return 0.0f;
	}
}
