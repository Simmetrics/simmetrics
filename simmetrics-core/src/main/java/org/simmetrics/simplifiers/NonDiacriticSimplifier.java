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
 * @author M.P. Korstanje
 *
 */
public class NonDiacriticSimplifier implements Simplifier {

	@Override
	public String simplify(String input) {
		return DIACRITICS_AND_FRIENDS.matcher(
				Normalizer.normalize(input, Normalizer.Form.NFD))
				.replaceAll("");
	}

	private static final Pattern DIACRITICS_AND_FRIENDS = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

	@Override
	public String toString() {
		return "NonDiacriticSimplifier";
	}
	
	

}
