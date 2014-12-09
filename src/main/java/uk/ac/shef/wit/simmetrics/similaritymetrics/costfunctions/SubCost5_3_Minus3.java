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

package uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SubCost5_3_Minus3 implements a cost function as used in Monge Elkan where by
 * an exact match no match or an approximate match whereby a set of characters
 * are in an approximate range for pairings in {dt} {gj} {lr} {mn} {bpv} {aeiou}
 * {,.}
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class SubCost5_3_Minus3 extends AbstractSubstitutionCost {

	/**
	 * return score.
	 */
	private static final int CHAR_EXACT_MATCH_SCORE = +5;

	/**
	 * return score.
	 */
	private static final int CHAR_APPROX_MATCH_SCORE = +3;

	/**
	 * return score.
	 */
	private static final int CHAR_MISMATCH_MATCH_SCORE = -3;

	/**
	 * approximate charcater set.
	 */

	/**
	 * approximate match = +3, for pairings in {dt} {gj} {lr} {mn} {bpv} {aeiou}
	 * {,.}.
	 */
	private static String[] approximates = new String[] { "dtDT", "gjGJ",
			"lrLR", "mnMN", "bpvBPV", "aeiouAEIOU", ",." };
	static private final Set<Set<Character>> approx = buildApprox(approximates);

	private static Set<Set<Character>> buildApprox(String[] approximates) {
		Set<Set<Character>> approx = new HashSet<Set<Character>>();
		for (String a : approximates) {
			List<Character> list = new ArrayList<Character>(a.length());
			for (char c : a.toCharArray()) {
				list.add(c);
			}
			approx.add(new HashSet<Character>(list));
		}

		return approx;
	}

	/**
	 * get cost between characters where d(i,j) = CHAR_EXACT_MATCH_SCORE if i
	 * equals j, CHAR_APPROX_MATCH_SCORE if i approximately equals j or
	 * CHAR_MISMATCH_MATCH_SCORE if i does not equal j.
	 *
	 * @param str1
	 *            - the string1 to evaluate the cost
	 * @param string1Index
	 *            - the index within the string1 to test
	 * @param str2
	 *            - the string2 to evaluate the cost
	 * @param string2Index
	 *            - the index within the string2 to test
	 * @return the cost of a given subsitution d(i,j) as defined above
	 */
	public final float getCost(final String str1, final int string1Index,
			final String str2, final int string2Index) {
		// check within range
		if (str1.length() <= string1Index || string1Index < 0) {
			return CHAR_MISMATCH_MATCH_SCORE;
		}
		if (str2.length() <= string2Index || string2Index < 0) {
			return CHAR_MISMATCH_MATCH_SCORE;
		}

		if (str1.charAt(string1Index) == str2.charAt(string2Index)) {
			return CHAR_EXACT_MATCH_SCORE;
		} else {
			// check for approximate match
			final Character si = str1.charAt(string1Index);
			final Character ti = str2.charAt(string2Index);
			for (Set<Character> aApprox : approx) {
				if (aApprox.contains(si) && aApprox.contains(ti))
					return CHAR_APPROX_MATCH_SCORE;
			}
			return CHAR_MISMATCH_MATCH_SCORE;
		}
	}

	public final float getMaxCost() {
		return CHAR_EXACT_MATCH_SCORE;
	}

	public final float getMinCost() {
		return CHAR_MISMATCH_MATCH_SCORE;
	}
}
