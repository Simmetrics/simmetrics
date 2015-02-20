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
package org.simmetrics.metrics.costfunctions;

import org.simmetrics.metrics.costfunctions.SubCost01;
import org.simmetrics.metrics.costfunctions.SubstitutionCost;

public class SubCost01Test extends InterfaceSubstitutionCostTest {


	@Override
	public SubstitutionCost getCost() {
		return new SubCost01();
	}

	@Override
	public T[] getTests() {
		final String testString1 = "hello world AAAAAAA BBB ABCDEF this is a test";
		final String testString2 = "jello wrd AAAAAAA BBB ABCDEF this is a test";
		return new T[] {
				new T(1.0000f, testString1, 0, testString2, 0),
				new T(0.0000f, testString1, 2, testString2, 2),
				new T(1.0000f, testString1, 7, testString2, 7),
				new T(1.0000f, testString1, 10, testString2, 10),
				new T(1.0000f, testString1, 22, testString2, 3),
		};
	}
}