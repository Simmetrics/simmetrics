package org.simmetrics.simplifiers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Encodes a string into a Caverphone 1.0 value.
 *
 * This is an algorithm created by the Caversham Project at the University of
 * Otago. It implements the Caverphone 1.0 algorithm.
 * 
 * 
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see <a href="http://en.wikipedia.org/wiki/Caverphone">Wikipedia -
 *      Caverphone</a>
 * @see <a
 *      href="http://caversham.otago.ac.nz/files/working/ctp150804.pdf">Caverphone
 *      2.0 specification</a>
 * @see org.apache.commons.codec.language.Caverphone1
 *
 */
public class Caverphone1 implements Simplifier {

	private final org.apache.commons.codec.language.Caverphone1 simplifier = new org.apache.commons.codec.language.Caverphone1();

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return simplifier.encode(input);
	}
	
	@Override
	public String toString() {
		return "Caverphone1";
	}

}
