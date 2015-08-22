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
 * Encodes a string into a double metaphone value. This Implementation is based
 * on the algorithm by <cite>Lawrence Philips</cite>.
 * 
 * This class is immutable and thread-safe.
 * 
 * @see org.apache.commons.codec.language.DoubleMetaphone
 */
public class DoubleMetaphone implements Simplifier {

	private static final int DEFAULT_CODE_LENGTH = 4;
	private static final boolean DEFAULT_USE_ALTERNATE = false;

	private final org.apache.commons.codec.language.DoubleMetaphone simplifier = new org.apache.commons.codec.language.DoubleMetaphone();
	private final boolean useAlternate;

	/**
	 * Creates an instance of this DoubleMetaphone encoder
	 */
	public DoubleMetaphone() {
		this(DEFAULT_CODE_LENGTH,DEFAULT_USE_ALTERNATE);
	}

	/**
	 * Creates an instance of the DoubleMetaphone simplifier with a
	 * {@code maxCodeLength}. All encodings will have at most
	 * {@code maxCodeLength} characters.
	 * 
	 * @param maxCodeLength
	 *            the maximum length of the code
	 * @param useAlternate
	 *            use alternate encode
	 */
	public DoubleMetaphone(int maxCodeLength, boolean useAlternate) {
		this.simplifier.setMaxCodeLen(maxCodeLength);
		this.useAlternate = useAlternate;
	}

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		
		final String simplified = simplifier.doubleMetaphone(input, useAlternate);
		if(simplified == null){
			return "";
		}
		return simplified;
	}

	@Override
	public String toString() {
		return "DoubleMetaphone [useAlternate=" + useAlternate + "]";
	}

}
