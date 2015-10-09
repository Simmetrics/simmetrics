/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
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

import java.util.regex.Pattern;

/**
 * Replaces all consecutive non-word [^0-9a-zA-Z] characters with a space.
 * <p>
 * This class is immutable and thread-safe.
 *
 * @deprecated use {@link Simplifiers#replaceNonWord()} instead.
 */
@Deprecated
public class WordCharacters implements Simplifier {

	private static final Pattern pattern = Pattern.compile("\\W");

	@Override
	public String simplify(String input) {
		return pattern.matcher(input).replaceAll(" ");
	}

	@Override
	public String toString() {
		return "WordCharacter [" + pattern + "]";
	}

}
