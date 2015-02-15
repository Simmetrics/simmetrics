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
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.simplifiers;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.regex.Pattern;

/**
 * Simplifies a string by computing its Soundex code.
 * 
 * @author M.P. Korstanje
 *
 */
public class SoundexSimplifier implements Simplifier {

	private static final char IGNORE = '0';
	private static final char OFFSET = 'A';
	private static final char[] CHAR_CODE = {
			// A B C D E F G H I J K L M
			'0', '1', '2', '3', '0', '1', '2', '0', '0', '2', '2', '4', '5',
			// N O P W R S T U V W X Y Z
			'5', '0', '1', '2', '6', '2', '3', '0', '1', '0', '2', '0', '2' };

	private static final int DEFAULT_LENGTH = 5;

	private final Pattern nonAz = Pattern.compile("[^A-Z]+");

	private int length;

	/**
	 * Creates a new Soundex simplifier with a length of 5. The Soundex string
	 * S2433 is 5 long.
	 */
	public SoundexSimplifier() {
		this(DEFAULT_LENGTH);
	}

	/**
	 * 
	 * Creates a new Soundex simplifier with the given <code>length</code>. The
	 * Soundex string S2433 is 5 long.
	 *
	 * @param length
	 *            the length of the soundex string
	 */
	public SoundexSimplifier(int length) {
		checkArgument(length >= 1, "minimum length is 1");
		this.length = length;
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

		// Clean and tidy
		// to lower case remove non-chars whitespaces
		wordStr = wordStr.toUpperCase();
		wordStr = nonAz.matcher(wordStr).replaceAll("");

		// check for empty input again the previous clean and tidy could of
		// shrunk it to zero.
		if (wordStr.isEmpty()) {
			return "";
		}

		StringBuilder builder = new StringBuilder(length);
		builder.append(wordStr.charAt(0));

		char previousCharacter = wordStr.charAt(0);
		for (int i = 1; i < wordStr.length() && builder.length() < length; i++) {
			char character = wordStr.charAt(i);
			char characterCode = CHAR_CODE[character - OFFSET];
			char previousCharacterCode = CHAR_CODE[previousCharacter - OFFSET];

			if (
			// Don't add ignored codes
			characterCode != IGNORE &&
			// Don't add repeated codes
					characterCode != previousCharacterCode) {
				builder.append(characterCode);

			}

			// A code is also considered repeated when separated by a W or H.
			if (character != 'W' && character != 'H') {
				previousCharacter = character;
			}
		}

		if (builder.length() < length) {
			for (int i = builder.length(); i < length; i++) {
				builder.append('0');
			}
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		return "SoundexSimplifier [length=" + length + "]";
	}
}