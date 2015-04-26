package org.simmetrics.simplifiers;

/**
 * Encodes a string into a Caverphone 2.0 value.
 *
 * This is an algorithm created by the Caversham Project at the University of
 * Otago. It implements the Caverphone 2.0 algorithm.
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
 * @see org.apache.commons.codec.language.Caverphone2
 *
 */
public class Caverphone2 implements Simplifier {

	private final org.apache.commons.codec.language.Caverphone2 simplifier = new org.apache.commons.codec.language.Caverphone2();

	@Override
	public String simplify(String input) {
		return simplifier.encode(input);
	}
	@Override
	public String toString() {
		return "Caverphone2";
	}
}
