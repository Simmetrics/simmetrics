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

/**
 * Encodes a string into a Daitch-Mokotoff Soundex value.
 * <p>
 * The Daitch-Mokotoff Soundex algorithm is a refinement of the Russel and
 * American Soundex algorithms, yielding greater accuracy in matching especially
 * Slavish and Yiddish surnames with similar pronunciation but differences in
 * spelling.
 * </p>
 * 
 * This class is immutable and thread-safe.
 * 
 * @see org.apache.commons.codec.language.DaitchMokotoffSoundex
 * @see <a href="http://en.wikipedia.org/wiki/Daitch%E2%80%93Mokotoff_Soundex">
 *      Wikipedia - Daitch-Mokotoff Soundex</a>
 * @see <a href="http://www.avotaynu.com/soundex.htm">Avotaynu - Soundexing and
 *      Genealogy</a>
 */
public class DaitchMokotoffSoundex implements Simplifier {

	private final org.apache.commons.codec.language.DaitchMokotoffSoundex simplifier;
	/**
	 * Creates a new instance with ASCII-folding enabled.
	 */
	public DaitchMokotoffSoundex() {
		this(true);
	}

	/**
	 * Creates a new DaitchMokotoffSoundex simplifier.
	 * <p>
	 * With ASCII-folding enabled, certain accented characters will be
	 * transformed to equivalent ASCII characters, e.g. è -&gt; e.
	 * </p>
	 *
	 * @param folding
	 *            if ASCII-folding shall be performed before encoding
	 */
	public DaitchMokotoffSoundex(boolean folding) {
		this.simplifier = new org.apache.commons.codec.language.DaitchMokotoffSoundex(
				folding);
	}

	@Override
	public String simplify(String input) {
		checkNotNull(input);

		return simplifier.encode(input);
	}

	@Override
	public String toString() {
		return "DaitchMokotoffSoundex";
	}
	
	

}
