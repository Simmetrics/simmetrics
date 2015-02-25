package org.simmetrics.simplifiers;

import static org.apache.commons.codec.language.RefinedSoundex.US_ENGLISH;

/**
 * Encodes a string into a Refined Soundex value. A refined soundex code is
 * optimized for spell checking words. Soundex method originally developed by
 * <CITE>Margaret Odell</CITE> and <CITE>Robert Russell</CITE>.
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see org.apache.commons.codec.language.RefinedSoundex
 */
public class RefinedSoundex implements Simplifier {

	@Override
	public String simplify(String input) {
		return US_ENGLISH.soundex(input);
	}

	@Override
	public String toString() {
		return "RefinedSoundex";
	}

}
