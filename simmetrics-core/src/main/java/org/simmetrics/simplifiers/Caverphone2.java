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
 * Encodes a string into a Caverphone 2.0 value.
 *
 * This is an algorithm created by the Caversham Project at the University of
 * Otago. It implements the Caverphone 2.0 algorithm.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Caverphone">Wikipedia -
 *      Caverphone</a>
 * @see <a
 *      href="http://caversham.otago.ac.nz/files/working/ctp150804.pdf">Caverphone
 *      2.0 specification</a>
 * @see org.apache.commons.codec.language.Caverphone2
 *
 */
public class Caverphone2 implements Simplifier {

	private final org.apache.commons.codec.language.Caverphone2 simplifier = new org.apache.commons.codec.language.Caverphone2();

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return simplifier.encode(input);
	}
	@Override
	public String toString() {
		return "Caverphone2";
	}
}
