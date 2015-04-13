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

import uk.ac.shef.wit.simmetrics.math.MathFuncs;

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.jarowinkler
 * Description: uk.ac.shef.wit.simmetrics.similaritymetrics.jarowinkler implements a String Metric.

 * Date: 02-Apr-2004
 * Time: 11:49:16
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class JaroWinkler extends AbstractStringMetric implements Serializable {

	private static final long serialVersionUID = -3664104657050204051L;

	/**
     * a constant for calculating the estimated timing cost.
     */
    private static final float EST_TIM_CONST = 4.342e-5f;

    /**
     * private string metric allowing internal metric to be composed.
     */
    private static final AbstractStringMetric STR_METRIC = new Jaro();

    /**
     * maximum prefix length to use.
     */
    private static final int MIN_PREF_TEST_LEN = 6;

    /**
     * prefix adjustment scale.
     */
    private static final float PREF_ADJ_SCALE = 0.1f;

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "JaroWinkler";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Jaro-Winkler algorithm providing a similarity measure between two strings allowing character transpositions to a degree adjusting the weighting for common prefixes";
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
        //0	0.15	0.36	0.76	1.34	2.03	2.94	3.9	5.34	6.34	8.12	9.71	11.28	13.53	15.62	16.92	20.3	22.56	25.38	33.57	31.29	33.83	40.6	40.6	54.75	50	58.5	58.75	72.67	67.67	83.33	102	101.5	83.33	109	94	109	109.5	133	117	125	133	132.5	141	148.5	156	156.5	171.5	203	250	235	219	218	250	219	281	250	266	250	297
        final float str1Length = string1.length();
        final float str2Length = string2.length();
        return (str1Length * str2Length) * EST_TIM_CONST;
    }

    /**
     * gets the similarity measure of the JaroWinkler metric for the given strings.
     *
     * @param string1
     * @param string2
     * @return 0-1 similarity measure of the JaroWinkler metric
     */
    public float getSimilarity(final String string1, final String string2) {
        //gets normal Jaro Score
        final float dist = STR_METRIC.getSimilarity(string1, string2);

        // This extension modifies the weights of poorly matching pairs string1, string2 which share a common prefix
        final int prefixLength = getPrefixLength(string1, string2);
        return dist + ((float) prefixLength * PREF_ADJ_SCALE * (1.0f - dist));
    }

    /**
     * gets the un-normalised similarity measure of the metric for the given strings.
     *
     * @param string1
     * @param string2
     * @return returns the score of the similarity measure (un-normalised)
     */
    public float getUnNormalisedSimilarity(final String string1, final String string2) {
        return getSimilarity(string1, string2);
    }

    /**
     * gets the prefix length found of common characters at the begining of the strings.
     *
     * @param string1
     * @param string2
     * @return the prefix length found of common characters at the begining of the strings
     */
    private static int getPrefixLength(final String string1, final String string2) {
        int ret = MathFuncs.min3(MIN_PREF_TEST_LEN, string1.length(), string2.length());
        //check for prefix similarity of length n
        for (int i = 0; i < ret; i++) {
        	//check the prefix is the same so far
            if (string1.charAt(i) != string2.charAt(i)) {
            	//not the same so return as far as got
            	ret = i;
            	break;
            }
        }
        return ret; // first n characters are the same
    }
}

