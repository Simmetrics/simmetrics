/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @deprecated Use {@link Simplifiers#removeDiacritics()} instead.
 * 
 *
 */
@Deprecated
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
