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

package uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Package: costfunctions
 * Description: SubCost5_3_Minus3 implements a cost function as used in Monge Elkan where by an exact match
 * no match or an approximate match whereby a set of characters are in an approximate range.
 * for pairings in {dt} {gj} {lr} {mn} {bpv} {aeiou} {,.}

 * Date: 30-Mar-2004
 * Time: 09:45:19
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class SubCost5_3_Minus3 extends AbstractSubstitutionCost implements Serializable {

	private static final long serialVersionUID = -1266240300625050409L;

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
    static private final List<Set<Character>> approx;

    /**
     * approximate match = +3,
     * for pairings in {dt} {gj} {lr} {mn} {bpv} {aeiou} {,.}.
     */
    static {
        approx = new ArrayList<Set<Character>>();
        approx.add(0, new HashSet<Character>());
        approx.get(0).add('d');
        approx.get(0).add('t');
        approx.add(1, new HashSet<Character>());
        approx.get(1).add('g');
        approx.get(1).add('j');
        approx.add(2, new HashSet<Character>());
        approx.get(2).add('l');
        approx.get(2).add('r');
        approx.add(3, new HashSet<Character>());
        approx.get(3).add('m');
        approx.get(3).add('n');
        approx.add(4, new HashSet<Character>());
        approx.get(4).add('b');
        approx.get(4).add('p');
        approx.get(4).add('v');
        approx.add(5, new HashSet<Character>());
        approx.get(5).add('a');
        approx.get(5).add('e');
        approx.get(5).add('i');
        approx.get(5).add('o');
        approx.get(5).add('u');
        approx.add(6, new HashSet<Character>());
        approx.get(6).add(',');
        approx.get(6).add('.');
    }

    /**
     * returns the name of the cost function.
     *
     * @return the name of the cost function
     */
    public final String getShortDescriptionString() {
        return "SubCost5_3_Minus3";
    }

    /**
     * get cost between characters where
     * d(i,j) = CHAR_EXACT_MATCH_SCORE if i equals j,
     * CHAR_APPROX_MATCH_SCORE if i approximately equals j or
     * CHAR_MISMATCH_MATCH_SCORE if i does not equal j.
     *
     * @param str1         - the string1 to evaluate the cost
     * @param string1Index - the index within the string1 to test
     * @param str2         - the string2 to evaluate the cost
     * @param string2Index - the index within the string2 to test
     * @return the cost of a given subsitution d(i,j) as defined above
     */
    public final float getCost(final String str1, final int string1Index, final String str2, final int string2Index) {
        //check within range
        if (str1.length() <= string1Index || string1Index < 0) {
            return CHAR_MISMATCH_MATCH_SCORE;
        }
        if (str2.length() <= string2Index || string2Index < 0) {
            return CHAR_MISMATCH_MATCH_SCORE;
        }

        if (str1.charAt(string1Index) == str2.charAt(string2Index)) {
            return CHAR_EXACT_MATCH_SCORE;
        } else {
            //check for approximate match
            final Character si = Character.toLowerCase(str1.charAt(string1Index));
            final Character ti = Character.toLowerCase(str2.charAt(string2Index));
            for (Set<Character> aApprox : approx) {
                if (aApprox.contains(si) && aApprox.contains(ti))
                    return CHAR_APPROX_MATCH_SCORE;
            }
            return CHAR_MISMATCH_MATCH_SCORE;
        }
    }

    /**
     * returns the maximum possible cost.
     *
     * @return the maximum possible cost
     */
    public final float getMaxCost() {
        return CHAR_EXACT_MATCH_SCORE;
    }

    /**
     * returns the minimum possible cost.
     *
     * @return the minimum possible cost
     */
    public final float getMinCost() {
        return CHAR_MISMATCH_MATCH_SCORE;
    }
}


