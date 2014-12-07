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

public class BlockDistanceTest extends InterfaceStringMetricTest {

	@Override
	public InterfaceStringMetric getMetric() {
		return new BlockDistance();
	}

	@Override
	public T[] getTests() {
		return new T[] {
				new T(0.5000f, "test string1", "test string2"),
				new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.7500f, "a b c d", "a b c e"),
				// Adding spaces to words because of whitespace tokenizer.
				new T(0.8333f, "H e a l e d", "S e a l e d"),
				new T(0.6153f, "H e a l e d", "H e a l t h y"),
				new T(0.7272f, "H e a l e d", "H e a r d"),
				new T(0.6666f, "H e a l e d", "H e r d e d"),
				new T(0.6000f, "H e a l e d", "H e l p"),
				new T(0.4000f, "H e a l e d", "S o l d"),
				new T(0.6000f, "H e a l e d", "H e l p")

		};
	}
}
