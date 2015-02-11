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
package org.simmetrics.simplifiers;

import static org.simmetrics.utils.Math.clamp;

/**
 * Simplifies a string by computing its Soundex code.
 * 
 * @author M.P. Korstanje
 *
 */
public class SoundexSimplifier implements Simplifier {

	/**
	 * Defines the soundex length in characters e.g. S-2433 is 6 long.
	 */
	private final static int SOUNDEXLENGTH = 6;

	public SoundexSimplifier() {
		this(SOUNDEXLENGTH);
	}

	private int length;

	public SoundexSimplifier(int soundExLen) {
		// ensure soundexLen is in a valid range
		this.length = clamp(4, soundExLen, 10);
	}

	/**
	 * Calculates a soundex code for a given string/name.
	 *
	 * @return a soundex code for a given string/name
	 */
	@Override
	public String simplify(String wordStr) {

		// check for empty input
		if (wordStr.isEmpty()) {
			return "";
		}

		wordStr = wordStr.toLowerCase();

		// The next changes improvements may change this first letter
		final char firstLetter = wordStr.charAt(0);

		// Clean and tidy
		// remove non-chars whitespace and spaces
		// FIXME: Slow code
		wordStr = wordStr.replaceAll("[^a-z]", "");
		wordStr = wordStr.replaceAll("\\s+", ""); //

		// check for empty input again the previous clean and tidy could of
		// shrunk it to zero.
		if (wordStr.isEmpty()) {
			return "";
		}

		// uses the assumption that enough valid characters are in the first
		// 4 times the soundex required length
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
		// FIXME: Slow code
		wordStr = wordStr.replaceAll("[aeiouwh]+", "0");
		wordStr = wordStr.replaceAll("[bpfv]+", "1");
		wordStr = wordStr.replaceAll("[cskgjqxz]+", "2");
		wordStr = wordStr.replaceAll("[dt]+", "3");
		wordStr = wordStr.replaceAll("[l]+", "4");
		wordStr = wordStr.replaceAll("[mn]+", "5");
		wordStr = wordStr.replaceAll("[r]+", "6");

		// Drop first letter code and remove zeros
		wordStr = wordStr.substring(1).replaceAll("0", "");
		// FIXME: This will not work for all soundex lenghts
		wordStr += "000000000000000000"; /* pad with zeros on right */
		// Add first letter of word and size to taste
		wordStr = firstLetter + "-" + wordStr.substring(0, length - 2);
		return wordStr;
	}

	@Override
	public String toString() {
		return "SoundexSimplifier [length=" + length + "]";
	}

}