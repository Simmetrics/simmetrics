
package org.simmetrics.simplifiers;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.codec.language.Soundex.US_ENGLISH;

/**
 * Encodes a string into a Soundex value. Soundex is an encoding used to relate
 * similar names, but can also be used as a general purpose scheme to find word
 * with similar phonemes.
 *
 * This class is thread-safe and immutable.
 * 
 * @see org.apache.commons.codec.language.Soundex
 *
 */
public class Soundex implements Simplifier {

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