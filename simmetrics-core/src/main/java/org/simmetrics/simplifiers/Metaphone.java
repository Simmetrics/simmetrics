package org.simmetrics.simplifiers;

/**
 * Encodes a string into a Metaphone value.
 * 
 * This class is thread-safe and immutable.
 * 
 * @see org.apache.commons.codec.language.Metaphone
 */
public class Metaphone implements Simplifier {

	private final org.apache.commons.codec.language.Metaphone simplifier;

	/**
	 * Creates an instance of the Metaphone simplifier
	 */
	public Metaphone() {
		this.simplifier = new org.apache.commons.codec.language.Metaphone();
	}

	/**
	 * Creates an instance of the Metaphone simplifier with a
	 * {@code maxCodeLength}. All encodings will have at most
	 * {@code maxCodeLength} characters.
	 * 
	 * @param maxCodeLength
	 *            maximum length of the encoding
	 */
	public Metaphone(int maxCodeLength) {
		this.simplifier = new org.apache.commons.codec.language.Metaphone();
		this.simplifier.setMaxCodeLen(maxCodeLength);
	}

	@Override
	public String simplify(String input) {
		return simplifier.metaphone(input);
	}

	@Override
	public String toString() {
		return "Metaphone [maxCodeLen=" + simplifier.getMaxCodeLen() + "]";
	}

}
