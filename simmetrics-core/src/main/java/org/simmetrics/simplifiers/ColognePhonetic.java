package org.simmetrics.simplifiers;

/**
 * Encodes a string into a Cologne Phonetic value.
 * <p>
 * Implements the <a
 * href="http://de.wikipedia.org/wiki/K%C3%B6lner_Phonetik">K&ouml;lner
 * Phonetik</a> (Cologne Phonetic) algorithm issued by Hans Joachim Postel in
 * 1969.
 * </p>
 * <p>
 * The <i>K&ouml;lner Phonetik</i> is a phonetic algorithm which is optimized
 * for the German language. It is related to the well-known soundex algorithm.
 * </p>
 * 
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 * 
 * @see org.apache.commons.codec.language.ColognePhonetic
 */
public class ColognePhonetic implements Simplifier {

	private final org.apache.commons.codec.language.ColognePhonetic simplifier = new org.apache.commons.codec.language.ColognePhonetic();

	@Override
	public String simplify(String input) {
		return simplifier.encode(input);
	}

	@Override
	public String toString() {
		return "ColognePhonetic";
	}
	
	

}
