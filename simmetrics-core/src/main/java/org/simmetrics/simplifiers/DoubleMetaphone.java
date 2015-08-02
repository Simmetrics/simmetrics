package org.simmetrics.simplifiers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Encodes a string into a double metaphone value. This Implementation is based
 * on the algorithm by <cite>Lawrence Philips</cite>.
 * 
 * This class is immutable and thread-safe.
 * 
 * @see org.apache.commons.codec.language.DoubleMetaphone
 */
public class DoubleMetaphone implements Simplifier {

	private static final int DEFAULT_CODE_LENGTH = 4;
	private static final boolean DEFAULT_USE_ALTERNATE = false;

	private final org.apache.commons.codec.language.DoubleMetaphone simplifier = new org.apache.commons.codec.language.DoubleMetaphone();
	private final boolean useAlternate;

	/**
	 * Creates an instance of this DoubleMetaphone encoder
	 */
	public DoubleMetaphone() {
		this(DEFAULT_CODE_LENGTH,DEFAULT_USE_ALTERNATE);
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
		this.simplifier.setMaxCodeLen(maxCodeLength);
		this.useAlternate = useAlternate;
	}

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		
		final String simplified = simplifier.doubleMetaphone(input, useAlternate);
		if(simplified == null){
			return "";
		}
		return simplified;
	}

	@Override
	public String toString() {
		return "DoubleMetaphone [useAlternate=" + useAlternate + "]";
	}

}
