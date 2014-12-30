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
package org.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.simmetrics.tokenisers.AbstractTokenizer;
import org.simmetrics.tokenisers.CachingTokenizer;
import org.simmetrics.tokenisers.Tokenizer;

public class CachingTokeniserTest extends TokeniserTest {

	private class HitCountingTokenizer extends AbstractTokenizer {

		protected HitCountingTokenizer() {
			// Private test class
		}

		private Map<String, Integer> arrayHitCount = new HashMap<String, Integer>();
		private Map<String, Integer> setHitCount = new HashMap<String, Integer>();

		public Map<String, Integer> getArrayHitCount() {
			return arrayHitCount;
		}

		public Map<String, Integer> getSetHitCount() {
			return setHitCount;
		}

		@Override
		public Set<String> tokenizeToSet(String input) {

			if (!setHitCount.containsKey(input)) {
				setHitCount.put(input, 0);
			}

			setHitCount.put(input, setHitCount.get(input) + 1);

			Set<String> s = new HashSet<String>();
			s.add(input);
			return s;
		}

		public ArrayList<String> tokenizeToList(String input) {
			if (!arrayHitCount.containsKey(input)) {
				arrayHitCount.put(input, 0);
			}

			arrayHitCount.put(input, arrayHitCount.get(input) + 1);

			ArrayList<String> s = new ArrayList<String>();
			s.add(input);
			return s;
		}

	}

	private HitCountingTokenizer tokenizer;

	@Override
	protected Tokenizer getTokenizer() {
		tokenizer = new HitCountingTokenizer();
		return new CachingTokenizer(tokenizer);
	}

	@Override
	public T[] getTests() {

		return new T[] { new T("ABC", "ABC"), new T("CCC", "CCC"),
				new T("ABC", "ABC"), new T("EEE", "EEE"), new T("ABC", "ABC"),
				new T("CCC", "CCC"),

		};
	}

	@Override
	public void testTokenizeToArrayList() {
		super.testTokenizeToArrayList();
		Assert.assertEquals(new Integer(1),
				tokenizer.getArrayHitCount().get("ABC"));
		Assert.assertEquals(new Integer(2),
				tokenizer.getArrayHitCount().get("CCC"));
	}

	@Override
	public void testTokenizeToSet() {
		super.testTokenizeToSet();
		Assert.assertEquals(new Integer(1),
				tokenizer.getSetHitCount().get("ABC"));
		Assert.assertEquals(new Integer(2),
				tokenizer.getSetHitCount().get("CCC"));

	}
}
