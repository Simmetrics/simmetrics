

package org.simmetrics.tokenizers;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Splits a string into tokens around white space.
 * <p>
 * This class is immutable and thread-safe.
 */
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

		return asList(pattern.split(input));
	}

}
