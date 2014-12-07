/*
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

/**
 * 
 * Implements the Chapman Length Deviation algorithm whereby the length
 * deviation of the input strings is used to determine if the strings are
 * similar in size. This is the ratio of difference in string lengths.
 * 
 * This approach is not intended to be used single handedly but rather alongside
 * other approaches
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class ChapmanLengthDeviation extends AbstractStringMetric {

	/**
	 * Constructs a ChapmanLengthDeviation metric
	 */
	public ChapmanLengthDeviation() {
	}

	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Chapman Length Deviation algorithm whereby the length deviation of the input strings is used to determine if the strings are similar in size - This apporach is not intended to be used single handedly but rather alongside other approaches";
	}
	
	@Override
	public float getSimilarityTimingEstimated(String string1, String string2) {
		return 0;
	}

	public float getSimilarity(final String string1, final String string2) {
		if (string1.length() >= string2.length()) {
			return (float) string2.length() / (float) string1.length();
		} else {
			return (float) string1.length() / (float) string2.length();
		}
	}

}
