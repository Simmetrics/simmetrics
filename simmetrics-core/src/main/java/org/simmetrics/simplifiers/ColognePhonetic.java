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
 * Encodes a string into a Cologne Phonetic value.
 * <p>
 * Implements the <a
 * href="http://de.wikipedia.org/wiki/K%C3%B6lner_Phonetik">K&ouml;lner
 * Phonetik</a> (Cologne Phonetic) algorithm issued by Hans Joachim Postel in
 * 1969.
 * <p>
 * The <i>K&ouml;lner Phonetik</i> is a phonetic algorithm which is optimized
 * for the German language. It is related to the well-known soundex algorithm.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see org.apache.commons.codec.language.ColognePhonetic
 * 
 * @deprecated will be removed due to a lack of a good use case
 */
@Deprecated
public final class ColognePhonetic implements Simplifier {

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
