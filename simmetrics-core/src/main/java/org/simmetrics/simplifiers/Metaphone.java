package org.simmetrics.simplifiers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Encodes a string into a Metaphone value.
 * <p>
 * All input is converted to upper case. Limitations: Input format is expected
 * to be a single ASCII word with only characters in the A - Z range, no
 * punctuation or numbers.
 * <p>
 * This class is thread-safe and immutable.
 * 
 * @see org.apache.commons.codec.language.Metaphone
 */
public class Metaphone implements Simplifier {
	private static final int DEFAULT_CODE_LENGTH = 4;
	private final org.apache.commons.codec.language.Metaphone simplifier;

	/**
	 * Creates an instance of the Metaphone simplifier
	 */
	public Metaphone() {
		this(DEFAULT_CODE_LENGTH);
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
		checkNotNull(input);
		return simplifier.metaphone(input);
	}

	@Override
	public String toString() {
		return "Metaphone [maxCodeLen=" + simplifier.getMaxCodeLen() + "]";
	}

}
