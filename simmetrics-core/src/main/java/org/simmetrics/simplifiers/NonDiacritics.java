/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


package org.simmetrics.simplifiers;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Removes non-diacritics with a regular expression. Character ranges relevant
 * for simplification. See <a
 * href="http://docstore.mik.ua/orelly/perl/prog3/ch05_04.htm">Pattern Matching
 * - Character Classes</a>
 * <p>
 * <code>\p{InCombiningDiacriticalMarks}\p{IsLm}\p{IsSk}]+</code>
 * <p>
 * 
 * <ul>
 * <li>InCombiningDiacriticalMarks: special marks that are part of "normal" ä,
 * ö, î etc..
 * 
 * <li><a
 * href="http://www.fileformat.info/info/unicode/category/Sk/list.htm">IsSk</a>:
 * Symbol, Modifier
 * <li><a href="http://www.fileformat.info/info/unicode/category/Lm/list.htm"
 * >IsLm</a>: Letter, Modifier
 * 
 * </ul>
 *
 * <p>
 * This class is thread-safe and immutable.
 * 
 *
 */
public class NonDiacritics implements Simplifier {

	@Override
	public String simplify(String input) {
		return DIACRITICS_AND_FRIENDS.matcher(
				Normalizer.normalize(input, Normalizer.Form.NFD))
				.replaceAll("");
	}

	private static final Pattern DIACRITICS_AND_FRIENDS = Pattern
			.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

	@Override
	public String toString() {
		return "NonDiacritics";
	}

}
