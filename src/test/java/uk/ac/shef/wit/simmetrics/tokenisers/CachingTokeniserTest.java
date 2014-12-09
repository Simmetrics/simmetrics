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

package uk.ac.shef.wit.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;

import uk.ac.shef.wit.simmetrics.wordhandlers.DummyStopTermHandler;
import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;

public class CachingTokeniserTest extends InterfaceTokeniserTest {

	private class HitCountingTokenizer implements InterfaceTokeniser {

		private Map<String, Integer> arrayHitCount = new HashMap<String, Integer>();
		private Map<String, Integer> setHitCount = new HashMap<String, Integer>();

		public Set<String> tokenizeToSet(String input) {

			if (!setHitCount.containsKey(input)) {
				setHitCount.put(input, 0);
			}

			setHitCount.put(input, setHitCount.get(input) + 1);

			Set<String> s = new HashSet<String>();
			s.add(input);
			return s;
		}

		public ArrayList<String> tokenizeToArrayList(String input) {
			if (!arrayHitCount.containsKey(input)) {
				arrayHitCount.put(input, 0);
			}

			arrayHitCount.put(input, arrayHitCount.get(input) + 1);

			ArrayList<String> s = new ArrayList<String>();
			s.add(input);
			return s;
		}

		public void setStopWordHandler(InterfaceTermHandler stopWordHandler) {

		}

		public InterfaceTermHandler getStopWordHandler() {
			return new DummyStopTermHandler();
		}

		public String getShortDescriptionString() {
			return getClass().getSimpleName();
		}

		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}

	private HitCountingTokenizer tokenizer;

	@Override
	protected InterfaceTokeniser getTokenizer() {
		tokenizer = new HitCountingTokenizer();
		return new CachingTokenizer(tokenizer);
	}

	@Override
	public T[] getTests() {

		return new T[] { 
				new T("ABC", "ABC"), 
				new T("CCC", "CCC"),
				new T("ABC", "ABC"),
				new T("EEE", "EEE"),
				new T("ABC", "ABC"),
				new T("CCC", "CCC"),


		};
	}

	@Override
	public void testTokenizeToArrayList() {
		super.testTokenizeToArrayList();
		Assert.assertEquals(new Integer(1), tokenizer.arrayHitCount.get("ABC"));
		Assert.assertEquals(new Integer(2), tokenizer.arrayHitCount.get("CCC"));
	}

	@Override
	public void testTokenizeToSet() {
		super.testTokenizeToSet();
		Assert.assertEquals(new Integer(1), tokenizer.setHitCount.get("ABC"));
		Assert.assertEquals(new Integer(2), tokenizer.setHitCount.get("CCC"));


	}
}
