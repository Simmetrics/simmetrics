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
 * Encodes a string into a NYSIIS value. NYSIIS is an encoding used to relate
 * similar names, but can also be used as a general purpose scheme to find word
 * with similar phonemes.
 * 
 * This class is thread-safe and immutable.
 * 
 * 
 * @see org.apache.commons.codec.language.Nysiis
 */
public class Nysiis implements Simplifier {

	private final org.apache.commons.codec.language.Nysiis simplifier;
	
	/**
	 * Creates an instance of the Nysiis encoder with strict mode (original
	 * form), i.e. encoded strings have a maximum length of 6.
	 */
	public Nysiis() {
		this(true);
	}

	/**
	 * Create an instance of the Nysiis simplifier with the specified strict
	 * mode:
	 *
	 * <ul>
	 * <li><code>true</code>: encoded strings have a maximum length of 6</li>
	 * <li><code>false</code>: encoded strings may have arbitrary length</li>
	 * </ul>
	 *
	 * @param strict
	 *            the strict mode
	 */
	public Nysiis(boolean strict) {
		this.simplifier = new org.apache.commons.codec.language.Nysiis(strict);
	}

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return simplifier.nysiis(input);
	}

	@Override
	public String toString() {
		return "Nysiis";
	}

}
