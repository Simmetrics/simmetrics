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

package uk.ac.shef.wit.simmetrics.similaritymetrics;


public class ChapmanMeanLengthTest extends InterfaceStringMetricTest {

  
	@Override
	public InterfaceStringMetric getMetric() {
		return new ChapmanMeanLength();
	}

	@Override
	public T[] getTests() {
		return new T[] {
				new T(0.1786f, "test string1","test string2"),
				new T(0.2193f, "aaa bbb ccc ddd","aaa bbb ccc eee"),
				new T(0.1074f, "a b c d","a b c e"),
				new T(0.0926f, "Healed","Sealed"),
				new T(0.1000f, "Healed","Healthy"),
				new T(0.0851f, "Healed","Heard"),
				new T(0.0926f, "Healed","Herded"),
				new T(0.0776f, "Healed","Help"),
				new T(0.0776f, "Healed","Sold"),
				new T(0.0776f, "Healed","Help"),
				new T(0.2325f, "Sam J Chapman","Samuel John Chapman"),
				new T(0.1507f, "Sam Chapman","S Chapman"),
				new T(0.2126f, "John Smith","Samuel John Chapman"),
				new T(0.1577f, "John Smith","Sam Chapman"),
				new T(0.1717f, "John Smith","Sam J Chapman"),
				new T(0.1436f, "John Smith","S Chapman"),
		};
	}
}

