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
 * @author Sam Chapman
 * @version 1.1
 */
public abstract class AbstractStringMetric implements InterfaceStringMetric {
	@Deprecated
	public String getShortDescriptionString() {
		return getClass().getSimpleName();
	};
	
	public String toString() {
		return getClass().getSimpleName();
	};

	@Deprecated
	public String getLongDescriptionString() {
		return null;
	}

	@Deprecated
	public String getSimilarityExplained(String string1, String string2) {
		return null;
	}

	@Deprecated
	public final long getSimilarityTimingActual(final String string1,
			final String string2) {
		// initialise timing
		final long timeBefore = System.currentTimeMillis();
		// perform measure
		getSimilarity(string1, string2);
		// get time after process
		final long timeAfter = System.currentTimeMillis();
		// output time taken
		return timeAfter - timeBefore;
	}

	/**
	 * does a batch comparison of the set of strings with the given comparator
	 * string returning an array of results equal in length to the size of the
	 * given set of strings to test.
	 *
	 * @param set
	 *            an array of strings to test against the comparator string
	 * @param comparator
	 *            the comparator string to test the array against
	 *
	 * @return an array of results equal in length to the size of the given set
	 *         of strings to test.
	 */
	public final float[] batchCompareSet(final String[] set,
			final String comparator) {
		final float[] results = new float[set.length];
		for (int strNum = 0; strNum < set.length; strNum++) {
			// perform similarity test
			results[strNum] = getSimilarity(set[strNum], comparator);
		}
		return results;
	}

	/**
	 * does a batch comparison of one set of strings against another set of
	 * strings returning an array of results equal in length to the minimum size
	 * of the given sets of strings to test.
	 *
	 * @param firstSet
	 *            an array of strings to test
	 * @param secondSet
	 *            an array of strings to test the first array against
	 *
	 * @return an array of results equal in length to the minimum size of the
	 *         given sets of strings to test.
	 */
	public final float[] batchCompareSets(final String[] firstSet,
			final String[] secondSet) {
		final float[] results;
		// sets the results to equal the shortest string length should they
		// differ.
		if (firstSet.length <= secondSet.length) {
			results = new float[firstSet.length];
		} else {
			results = new float[secondSet.length];
		}
		for (int strNum = 0; strNum < results.length; strNum++) {
			// perform similarity test
			results[strNum] = getSimilarity(firstSet[strNum], secondSet[strNum]);
		}
		return results;
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {
		return Float.POSITIVE_INFINITY;
	}

	/**
	 * Gets the un-normalised similarity measure of the metric for the given
	 * strings.
	 *
	 * @param string1
	 * @param string2
	 *
	 * @return returns the score of the similarity measure (un-normalised)
	 */
	public float getUnNormalisedSimilarity(String string1, String string2) {
		return getSimilarity(string1, string2);
	}
}
