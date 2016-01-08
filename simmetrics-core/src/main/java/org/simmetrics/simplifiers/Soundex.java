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
import static org.apache.commons.codec.language.Soundex.US_ENGLISH;

/**
 * Encodes a string into a Soundex value. Soundex is an encoding used to relate
 * similar names, but can also be used as a general purpose scheme to find word
 * with similar phonemes.
 * <p>
 * This class is thread-safe and immutable.
 * 
 * @see org.apache.commons.codec.language.Soundex
 * 
 * @deprecated will be removed due to a lack of a good use case
 */
@Deprecated
public final class Soundex implements Simplifier {

	@Override
	public String toString() {
		return "Soundex";
	}

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return US_ENGLISH.soundex(input);
	}
	
}
