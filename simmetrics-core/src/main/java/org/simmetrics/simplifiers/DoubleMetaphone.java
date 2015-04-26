package org.simmetrics.simplifiers;

/**
 * Encodes a string into a double metaphone value. This Implementation is based
 * on the algorithm by <cite>Lawrence Philips</cite>.
 * 
 * This class is immutable and thread-safe.
 * 
 * @see org.apache.commons.codec.language.DoubleMetaphone
 */
public class DoubleMetaphone implements Simplifier {

	private final org.apache.commons.codec.language.DoubleMetaphone simplifier;
	private final boolean useAlternate;
	  /**
     * Creates an instance of this DoubleMetaphone encoder
     */
	public DoubleMetaphone() {
		this.simplifier = new org.apache.commons.codec.language.DoubleMetaphone();
		this.useAlternate = false;
	}

	/**
	 * Creates an instance of the DoubleMetaphone simplifier with a
	 * {@code maxCodeLength}. All encodings will have at most
	 * {@code maxCodeLength} characters.
	 * 
	 * @param maxCodeLength
	 *            the maximum length of the code
	 * @param useAlternate
	 *            use alternate encode
	 */
	public DoubleMetaphone(int maxCodeLength, boolean useAlternate) {
		this.simplifier = new org.apache.commons.codec.language.DoubleMetaphone();
		this.simplifier.setMaxCodeLen(maxCodeLength);
		this.useAlternate = useAlternate;
	}

	@Override
	public String simplify(String input) {
		return simplifier.doubleMetaphone(input, useAlternate);
	}

	@Override
	public String toString() {
		return "DoubleMetaphone [useAlternate=" + useAlternate + "]";
	}
	
	

}
