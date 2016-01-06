/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Encodes a string into a Daitch-Mokotoff Soundex value.
 * <p>
 * The Daitch-Mokotoff Soundex algorithm is a refinement of the Russel and
 * American Soundex algorithms, yielding greater accuracy in matching especially
 * Slavish and Yiddish surnames with similar pronunciation but differences in
 * spelling.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see org.apache.commons.codec.language.DaitchMokotoffSoundex
 * @see <a href="http://en.wikipedia.org/wiki/Daitch%E2%80%93Mokotoff_Soundex">
 *      Wikipedia - Daitch-Mokotoff Soundex</a>
 * @see <a href="http://www.avotaynu.com/soundex.htm">Avotaynu - Soundexing and
 *      Genealogy</a>
 *      
 * @deprecated will be removed due to a lack of a good use case
 */
@Deprecated
public final class DaitchMokotoffSoundex implements Simplifier {

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
