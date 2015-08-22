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

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.codec.language.RefinedSoundex.US_ENGLISH;

/**
 * Encodes a string into a Refined Soundex value. A refined soundex code is
 * optimized for spell checking words. Soundex method originally developed by
 * <CITE>Margaret Odell</CITE> and <CITE>Robert Russell</CITE>.
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see org.apache.commons.codec.language.RefinedSoundex
 */
public class RefinedSoundex implements Simplifier {

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return US_ENGLISH.soundex(input);
	}

	@Override
	public String toString() {
		return "RefinedSoundex";
	}

}
