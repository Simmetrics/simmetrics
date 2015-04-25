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

import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser;

import java.io.Serializable;

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.chapmanmatchingsoundex
 * Description: uk.ac.shef.wit.simmetrics.similaritymetrics.chapmanmatchingsoundex implements a

 * Date: 02-Apr-2004
 * Time: 14:04:59
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class ChapmanMatchingSoundex extends MongeElkan implements Serializable {

	private static final long serialVersionUID = 3158675321996177762L;
	/**
     * a constant for calculating the estimated timing cost.
     */
    private static final float EST_TIM_CONST = 0.026571428571428571428571428571429f;

    /**
     * constructor - default (empty).
     */
    public ChapmanMatchingSoundex() {
        super(new Soundex());
    }

    /**
     * constructor.
     *
     * @param tokeniserToUse - the tokeniser to use should a different tokeniser be required
     */
    public ChapmanMatchingSoundex(final InterfaceTokeniser tokeniserToUse) {
        super(tokeniserToUse, new Soundex());
    }

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "ChapmanMatchingSoundex";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Chapman Matching Soundex algorithm whereby terms are matched and tested against the standard soundex algorithm - this is intended to provide a better rating for lists of proper names.";
    }

    /**
     * gets a div class xhtml similarity explaining the operation of the metric.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return a div class html section detailing the metric operation.
     */
    public String getSimilarityExplained(final String string1, final String string2) {
        //todo this should explain the operation of a given comparison
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * gets the estimated time in milliseconds it takes to perform a similarity timing.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return the estimated time in milliseconds taken to perform the similarity measure
     */
    public float getSimilarityTimingEstimated(final String string1, final String string2) {
        //timed millisecond times with string lengths from 1 + 50 each increment
        //0.08	2.26	8.16	16.92	29	51	67.67	93.67	117	156.5	187.5	234	266	312	375	422	485	547	609	656	766	828	906	1000	1078	1157	1265	1360	1453	1562	1688	1781	1891	2031	2094	2219	2422	2532	2656	2812	2938	3109	3250	3407	3562	3750	3907	4062	4250	4422	4625	4797	4985	5188	5390	5578	5782	5984	6204	6437
        final float str1Tokens = getTokeniser().tokenizeToArrayList(string1).size();
        final float str2Tokens = getTokeniser().tokenizeToArrayList(string2).size();
        return (getTokeniser().tokenizeToArrayList(string1).size() + getTokeniser().tokenizeToArrayList(string2).size()) * ((str1Tokens+str2Tokens) * EST_TIM_CONST);
    }
}


