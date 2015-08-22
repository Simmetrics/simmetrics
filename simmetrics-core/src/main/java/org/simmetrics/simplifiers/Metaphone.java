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
 * Encodes a string into a Metaphone value.
 * <p>
 * All input is converted to upper case. Limitations: Input format is expected
 * to be a single ASCII word with only characters in the A - Z range, no
 * punctuation or numbers.
 * <p>
 * This class is thread-safe and immutable.
 * 
 * @see org.apache.commons.codec.language.Metaphone
 */
public class Metaphone implements Simplifier {
	private static final int DEFAULT_CODE_LENGTH = 4;
	private final org.apache.commons.codec.language.Metaphone simplifier;

	/**
	 * Creates an instance of the Metaphone simplifier
	 */
	public Metaphone() {
		this(DEFAULT_CODE_LENGTH);
	}

	/**
	 * Creates an instance of the Metaphone simplifier with a
	 * {@code maxCodeLength}. All encodings will have at most
	 * {@code maxCodeLength} characters.
	 * 
	 * @param maxCodeLength
	 *            maximum length of the encoding
	 */
	public Metaphone(int maxCodeLength) {
		this.simplifier = new org.apache.commons.codec.language.Metaphone();
		this.simplifier.setMaxCodeLen(maxCodeLength);
	}

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return simplifier.metaphone(input);
	}

	@Override
	public String toString() {
		return "Metaphone [maxCodeLen=" + simplifier.getMaxCodeLen() + "]";
	}

}
