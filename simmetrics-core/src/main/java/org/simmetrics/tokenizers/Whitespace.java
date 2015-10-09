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

package org.simmetrics.tokenizers;

import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Splits a string into tokens around white space. Does not return leading or
 * trailing empty tokens.
 * <p>
 * To create tokenizer that returns leading and trailing empty tokens use
 * {@code Tokenizers.pattern("\\s+")}
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @deprecated use {@link Tokenizers#whitespace()} instead
 */
@Deprecated
public final class Whitespace extends AbstractTokenizer {

	@Override
	public String toString() {
		return "WhitespaceTokenizer [" + pattern + "]";
	}

	private static final Pattern pattern = Pattern.compile("\\s+");

	@Override
	public List<String> tokenizeToList(final String input) {
		if (input.isEmpty()) {
			return emptyList();
		}

		String[] tokens = pattern.split(input);

		// Remove leading empty token if any
		if (tokens.length > 0 && tokens[0].isEmpty()) {
			tokens = copyOfRange(tokens, 1, tokens.length);
		}

		return asList(tokens);
	}

}
