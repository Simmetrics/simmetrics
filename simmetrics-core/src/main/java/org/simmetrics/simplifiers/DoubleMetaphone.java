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
 * Encodes a string into a double metaphone value. This Implementation is based
 * on the algorithm by <cite>Lawrence Philips</cite>.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see org.apache.commons.codec.language.DoubleMetaphone
 * 
 * @deprecated will be removed due to a lack of a good use case
 */
@Deprecated
public final class DoubleMetaphone implements Simplifier {

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
