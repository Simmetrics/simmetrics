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
 * Encodes a string into a Cologne Phonetic value.
 * <p>
 * Implements the <a
 * href="http://de.wikipedia.org/wiki/K%C3%B6lner_Phonetik">K&ouml;lner
 * Phonetik</a> (Cologne Phonetic) algorithm issued by Hans Joachim Postel in
 * 1969.
 * </p>
 * <p>
 * The <i>K&ouml;lner Phonetik</i> is a phonetic algorithm which is optimized
 * for the German language. It is related to the well-known soundex algorithm.
 * </p>
 * 
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 * 
 * @see org.apache.commons.codec.language.ColognePhonetic
 */
public class ColognePhonetic implements Simplifier {

	private final org.apache.commons.codec.language.ColognePhonetic simplifier = new org.apache.commons.codec.language.ColognePhonetic();

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return simplifier.encode(input);
	}

	@Override
	public String toString() {
		return "ColognePhonetic";
	}
	
	

}
