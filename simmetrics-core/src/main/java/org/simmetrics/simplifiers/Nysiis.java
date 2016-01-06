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
 * Encodes a string into a NYSIIS value. NYSIIS is an encoding used to relate
 * similar names, but can also be used as a general purpose scheme to find word
 * with similar phonemes.
 * <p>
 * This class is thread-safe and immutable.
 * 
 * @see org.apache.commons.codec.language.Nysiis
 * 
 * 
 * @deprecated will be removed due to a lack of a good use case
 */
@Deprecated
public final class Nysiis implements Simplifier {

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
