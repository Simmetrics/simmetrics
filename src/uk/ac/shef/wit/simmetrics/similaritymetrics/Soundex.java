/**
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package uk.ac.shef.wit.simmetrics.similaritymetrics;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.soundex Description:
 * uk.ac.shef.wit.simmetrics.similaritymetrics.soundex implements a
 * 
 * Date: 02-Apr-2004 Time: 12:47:34
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a
 *         href="mailto:sam@dcs.shef.ac.uk">Email</a>.<br>
 *         Ferociously amended by Johnathan Botha
 * @version 1.2
 */
public final class Soundex extends AbstractStringMetric implements Serializable {

	private static final long serialVersionUID = -6641964357262540914L;

	private static final char[] US_EN_MAP = "01230120022455012623010202".toCharArray();

	/**
	 * a constant for calculating the estimated timing cost.
	 */
	private static final float EST_TIMING = 0.00052f;

	/**
	 * private string metric allowing internal metric to be composed.
	 */
	private AbstractStringMetric intStrMetr;

	/**
	 * defines the soundex length in characters e.g. S-2433 is 6 long.
	 */
	private static final int SOUNDEXLENGTH = 6;

	private static final String TEST_STR = "abcdefghijklmnopq";

	/**
	 * constructor - default (empty).
	 */
	public Soundex() {
		super();
		intStrMetr = new JaroWinkler();
	}

	/**
	 * constructor.
	 * 
	 * @param intCompMetr
	 *            - the metric used to compare two soundex strings
	 */
	public Soundex(final AbstractStringMetric intCompMetr) {
		super();
		intStrMetr = intCompMetr;
	}

	/**
	 * returns the string identifier for the metric.
	 * 
	 * @return the string identifier for the metric
	 */
	public String getShortDescriptionString() {
		return "Soundex";
	}

	/**
	 * returns the long string identifier for the metric.
	 * 
	 * @return the long string identifier for the metric
	 */
	public String getLongDescriptionString() {
		return "Implements the Soundex algorithm providing a similarity measure between two soundex codes";
	}

	/**
	 * gets a div class xhtml similarity explaining the operation of the metric.
	 * 
	 * @param string1
	 *            string 1
	 * @param string2
	 *            string 2
	 * 
	 * @return a div class html section detailing the metric operation.
	 */
	public String getSimilarityExplained(final String string1,
			final String string2) {
		// todo this should explain the operation of a given comparison
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	/**
	 * gets the estimated time in milliseconds it takes to perform a similarity
	 * timing.
	 * 
	 * @param string1
	 *            string 1
	 * @param string2
	 *            string 2
	 * 
	 * @return the estimated time in milliseconds taken to perform the
	 *         similarity measure
	 */
	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {
		// timed millisecond times with string lengths from 1 + 50 each
		// increment
		// 0.11 0.43 0.44 0.7 0.94 1.25 1.56 1.85 2.36 2.6 3.24 3.5 4.06 4.61
		// 5.07 5.64 6.34 6.55 7.85 7.81 8.83 9.67 10.68 11.28 14.5 12.75 15.62
		// 14.5 18.45 15.62 22.56 18.25 22.56 20.3 22.56 24.33 25.38 25.38 31.29
		// 27.38 33.83 29 33.83 33.83 40.6 34 43.6 39.17 46.8 39 51 43.6 54.75
		// 43.8 62.5 46.8 58.75 54.5 62.5 54.75
		final float str1Length = string1.length();
		final float str2Length = string2.length();

		return ((str1Length + str2Length) * EST_TIMING)
				+ intStrMetr.getSimilarityTimingEstimated(
						TEST_STR.substring(0, SOUNDEXLENGTH),
						TEST_STR.substring(0, SOUNDEXLENGTH));
	}

	/**
	 * gets the similarity of the two soundex strings using internal string
	 * similarity distance.
	 * 
	 * @param string1
	 * @param string2
	 * @return a value between 0-1 of the similarity
	 */
	public float getSimilarity(final String string1, final String string2) {

		final String soundex1 = soundex(string1);
		final String soundex2 = soundex(string2);
		// convert into zero to one return using attached string metric to score
		// comparison
		return intStrMetr.getSimilarity(soundex1, soundex2);
	}

	/**
	 * gets the un-normalised similarity measure of the metric for the given
	 * strings.
	 * 
	 * @param string1
	 * @param string2
	 * 
	 * @return returns the score of the similarity measure (un-normalised)
	 */
	public float getUnNormalisedSimilarity(final String string1,
			final String string2) {
		return intStrMetr.getUnNormalisedSimilarity(string1, string2);
	}
	
	public AbstractStringMetric getStringMetric() {
		
		return intStrMetr;
		
	}
	
	public void setStringMetric(final AbstractStringMetric _metr) {
		
		this.intStrMetr = _metr;
		
	}

	private static String clean(final String str) {
		
		String ret;
		
		if (str == null || str.length() == 0) {
			
			ret = str;
			
		} else {

			final int len = str.length();
			final char[] chars = new char[len];
			int count = 0;
			
			for (int i = 0; i < len; i++) {
				
				if (Character.isLetter(str.charAt(i))) {
					
					chars[count++] = str.charAt(i);
					
				}
				
			}
			
			if (count == len) {
	
				ret = str.toUpperCase(java.util.Locale.ENGLISH);
	
			} else {
	
				ret = new String(chars, 0, count)
						.toUpperCase(java.util.Locale.ENGLISH);
	
			}
		
		}
		
		return ret;

	}

	private char[] getSoundexMapping() {
		
		return Arrays.copyOf(US_EN_MAP, US_EN_MAP.length);
		
	}

	private char map(final char _ch) {
		
		final int index = _ch - 'A';
		
		if (index < 0 || index >= this.getSoundexMapping().length) {
			
			throw new IllegalArgumentException("The character is not mapped: " + _ch);
			
		}
		
		return this.getSoundexMapping()[index];
		
	}

	private char getMappingCode(final String str, final int index) {
		
		// map() throws IllegalArgumentException
		char mappedChar = this.map(str.charAt(index));
		
		// HW rule check
		if (index > 1 && mappedChar != '0') {
			
			final char hwChar = str.charAt(index - 1);
			
			if ('H' == hwChar || 'W' == hwChar) {
				
				final char preHWChar = str.charAt(index - 2);
				final char firstCode = this.map(preHWChar);
				
				if ((firstCode == mappedChar) || ('H' == preHWChar)	|| ('W' == preHWChar)) {
					
					mappedChar = 0;
					
				}
				
			}
			
		}
		
		return mappedChar;
		
	}

	private String soundex(final String _str) {

		String str = _str;

		if (str == null) {
			return null;
		}

		str = clean(str);

		if (str.length() == 0) {
			return str;
		}

		char out[] = { '0', '0', '0', '0' };
		char last, mapped;
		int incount = 1, count = 1;

		out[0] = str.charAt(0);
		// getMappingCode() throws IllegalArgumentException
		last = getMappingCode(str, 0);

		while (incount < str.length() && count < out.length) {

			mapped = getMappingCode(str, incount++);

			if (mapped != 0) {

				if (mapped != '0' && mapped != last) {

					out[count++] = mapped;

				}

				last = mapped;

			}

		}

		return String.copyValueOf(out);
	}

	public AbstractStringMetric getIntStrMetr() {
		return intStrMetr;
	}

	public void setIntStrMetr(final AbstractStringMetric intStrMetr) {
		this.intStrMetr = intStrMetr;
	}
	
}