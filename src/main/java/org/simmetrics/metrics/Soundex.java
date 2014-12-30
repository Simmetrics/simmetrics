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

import org.simmetrics.simplifier.AbstractSimplifier;
import org.simmetrics.simplifier.CaseSimplifier;
import org.simmetrics.simplifier.CompositeSimplifier;

import static org.simmetrics.utils.Math.clamp;

/**
 * Implements the Soundex algorithm providing a similarity measure between two
 * soundex codes.
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class Soundex extends JaroWinkler {

	public Soundex() {
		setSimplifier(new CompositeSimplifier() {
			{
				setSimplifiers(
						new CaseSimplifier.Lower(),
						new SoundexSimplifier());
			}

		});

	}

	private class SoundexSimplifier extends AbstractSimplifier {

		/**
		 * Defines the soundex length in characters e.g. S-2433 is 6 long.
		 */
		private final static int SOUNDEXLENGTH = 6;

		public SoundexSimplifier() {
			this(SOUNDEXLENGTH);
		}

		private int soundExLen;

		public SoundexSimplifier(int soundExLen) {
			// ensure soundexLen is in a valid range
			this.soundExLen = clamp(4, soundExLen, 10);
		}

		/**
		 * calculates a soundex code for a given string/name.
		 *
		 * @param wordString
		 * @param soundExLen
		 *            - the length of the soundex code to create
		 * @return a soundex code for a given string/name
		 */
		public String simplify(String wordString) {

			// check for empty input
			if (wordString.isEmpty()) {
				return "";
			}

			/*
			 * Clean and tidy
			 */
			String wordStr = wordString;
			wordStr = wordStr.replaceAll("[^a-z]", " "); // rpl non-chars
															// whitespace
			wordStr = wordStr.replaceAll("\\s+", ""); // remove spaces

			// check for empty input again the previous clean and tidy could of
			// shrunk it to zero.
			if (wordStr.isEmpty()) {
				return "";
			}

			/*
			 * The above improvements may change this first letter
			 */
			final char firstLetter = wordStr.charAt(0);

			// uses the assumption that enough valid characters are in the first
			// 4
			// times the soundex required length
			if (wordStr.length() > (SOUNDEXLENGTH * 4) + 1) {
				wordStr = "-" + wordStr.substring(1, SOUNDEXLENGTH * 4);
			} else {
				wordStr = "-" + wordStr.substring(1);
			}
			// Begin Classic SoundEx
			// 1 <- B,P,F,V
			// 2 <- C,S,K,G,J,Q,X,Z
			// 3 <- D,T
			// 4 <- L
			// 5 <- M,N
			// 6 <- R

			// Match one or more characters, repeating characters are reduced to
			// a single digit.
			wordStr = wordStr.replaceAll("[aeiouwh]+", "0");
			wordStr = wordStr.replaceAll("[bpfv]+", "1");
			wordStr = wordStr.replaceAll("[cskgjqxz]+", "2");
			wordStr = wordStr.replaceAll("[dt]+", "3");
			wordStr = wordStr.replaceAll("[l]+", "4");
			wordStr = wordStr.replaceAll("[mn]+", "5");
			wordStr = wordStr.replaceAll("[r]+", "6");

			wordStr = wordStr.substring(1); /* Drop first letter code */
			wordStr = wordStr.replaceAll("0", ""); /* remove zeros */
			wordStr += "000000000000000000"; /* pad with zeros on right */
			wordStr = firstLetter + "-" + wordStr; /* Add first letter of word */
			wordStr = wordStr.substring(0, soundExLen); /* size to taste */
			return (wordStr);
		}

	}
}
