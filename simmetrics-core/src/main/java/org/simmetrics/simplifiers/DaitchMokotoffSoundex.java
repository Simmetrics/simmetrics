package org.simmetrics.simplifiers;

/**
 * Encodes a string into a Daitch-Mokotoff Soundex value.
 * <p>
 * The Daitch-Mokotoff Soundex algorithm is a refinement of the Russel and
 * American Soundex algorithms, yielding greater accuracy in matching especially
 * Slavish and Yiddish surnames with similar pronunciation but differences in
 * spelling.
 * </p>
 * 
 * This class is immutable and thread-safe.
 * 
 * @see org.apache.commons.codec.language.DaitchMokotoffSoundex
 * @see <a href="http://en.wikipedia.org/wiki/Daitch%E2%80%93Mokotoff_Soundex">
 *      Wikipedia - Daitch-Mokotoff Soundex</a>
 * @see <a href="http://www.avotaynu.com/soundex.htm">Avotaynu - Soundexing and
 *      Genealogy</a>
 */
public class DaitchMokotoffSoundex implements Simplifier {

	private final org.apache.commons.codec.language.DaitchMokotoffSoundex simplifier;

	/**
	 * Creates a new instance with ASCII-folding enabled.
	 */
	public DaitchMokotoffSoundex() {
		this.simplifier = new org.apache.commons.codec.language.DaitchMokotoffSoundex();
	}

	/**
	 * Creates a new DaitchMokotoffSoundex simplifier.
	 * <p>
	 * With ASCII-folding enabled, certain accented characters will be
	 * transformed to equivalent ASCII characters, e.g. è -&gt; e.
	 * </p>
	 *
	 * @param folding
	 *            if ASCII-folding shall be performed before encoding
	 */
	public DaitchMokotoffSoundex(boolean folding) {
		this.simplifier = new org.apache.commons.codec.language.DaitchMokotoffSoundex(
				folding);
	}

	@Override
	public String simplify(String input) {
		return simplifier.encode(input);
	}

	@Override
	public String toString() {
		return "DaitchMokotoffSoundex";
	}
	
	

}
