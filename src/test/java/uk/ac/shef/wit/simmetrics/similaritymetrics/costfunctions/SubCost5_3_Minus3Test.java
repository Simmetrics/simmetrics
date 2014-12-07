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

package uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions;


public class SubCost5_3_Minus3Test extends InterfaceSubstitutionCostTest {

	@Override
	public InterfaceSubstitutionCost getCost() {
		return new SubCost5_3_Minus3();
	}

	@Override
	public T[] getTests() {
		final String testString1 = "hello world AAAAAAA BBB ABCDEF this is a test";
		final String testString2 = "jello wrd AAAAAAA BBB ABCDEF this is a test";

		return new T[] { 
				new T(-3.0000f, testString1, 0, testString2, 0),
				new T(5.0000f, testString1, 2, testString2, 2),
				new T(-3.0000f, testString1, 7, testString2, 7),
				new T(-3.0000f, testString1, 10, testString2, 10),
				new T(-3.0000f, testString1, 22, testString2, 3),
 };
	}
}