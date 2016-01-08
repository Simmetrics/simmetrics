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
 * Encodes a string into a Metaphone value.
 * <p>
 * All input is converted to upper case. Limitations: Input format is expected
 * to be a single ASCII word with only characters in the A - Z range, no
 * punctuation or numbers.
 * <p>
 * This class is thread-safe and immutable.
 * 
 * @see org.apache.commons.codec.language.Metaphone
 * 
 * @deprecated will be removed due to a lack of a good use case
 */
@Deprecated
public final class Metaphone implements Simplifier {
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
