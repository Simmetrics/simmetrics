

package org.simmetrics.simplifiers;

import java.util.regex.Pattern;

/**
 * Replaces all consecutive non-word [^0-9a-zA-Z] characters with a space.
 * <p>
 * This class is immutable and thread-safe.
 *
 */
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
