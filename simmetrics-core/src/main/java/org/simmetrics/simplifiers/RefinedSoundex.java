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
import static org.apache.commons.codec.language.RefinedSoundex.US_ENGLISH;

/**
 * Encodes a string into a Refined Soundex value. A refined soundex code is
 * optimized for spell checking words. Soundex method originally developed by
 * <cite>Margaret Odell</cite> and <cite>Robert Russell</cite>.
 * <p>
 * This class is immutable and thread-safe.
 *
 * @see org.apache.commons.codec.language.RefinedSoundex
 * 
 * @deprecated will be removed due to a lack of a good use case
 */
@Deprecated
public final class RefinedSoundex implements Simplifier {

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return US_ENGLISH.soundex(input);
	}

	@Override
	public String toString() {
		return "RefinedSoundex";
	}

}
