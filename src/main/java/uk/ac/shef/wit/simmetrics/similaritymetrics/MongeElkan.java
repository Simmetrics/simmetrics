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
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserWhitespace;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.mongeelkan
 * Description: uk.ac.shef.wit.simmetrics.similaritymetrics.mongeelkan implements a

 * Date: 31-Mar-2004
 * Time: 17:19:55
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public class MongeElkan extends AbstractStringMetric implements Serializable {

    /**
     * a constant for calculating the estimated timing cost.
     */
    private final float ESTIMATEDTIMINGCONST = 0.0344f;

    /**
     * private tokeniser for tokenisation of the query strings.
     */
    final InterfaceTokeniser tokeniser;

    /**
     * private string metric allowing internal metric to be composed.
     */
    private final AbstractStringMetric internalStringMetric;

    /**
     * gets a div class xhtml similarity explaining the operation of the metric.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return a div class html section detailing the metric operation.
     */
    public String getSimilarityExplained(String string1, String string2) {
        //todo this should explain the operation of a given comparison
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * constructor - default (empty).
     */
    public MongeElkan() {
        tokeniser = new TokeniserWhitespace();
        internalStringMetric = new SmithWatermanGotoh();
    }

    /**
     * constructor.
     *
     * @param tokeniserToUse - the tokeniser to use should a different tokeniser be required
     */
    public MongeElkan(final InterfaceTokeniser tokeniserToUse) {
        tokeniser = tokeniserToUse;
        internalStringMetric = new SmithWatermanGotoh();
    }

    /**
     * constructor.
     *
     * @param tokeniserToUse - the tokeniser to use should a different tokeniser be required
     * @param metricToUse    - the string metric to use
     */
    public MongeElkan(final InterfaceTokeniser tokeniserToUse, final AbstractStringMetric metricToUse) {
        tokeniser = tokeniserToUse;
        internalStringMetric = metricToUse;
    }

    /**
     * constructor.
     *
     * @param metricToUse - the string metric to use
     */
    public MongeElkan(final AbstractStringMetric metricToUse) {
        tokeniser = new TokeniserWhitespace();
        internalStringMetric = metricToUse;
    }

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "MongeElkan";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Monge Elkan algorithm providing an matching style similarity measure between two strings";
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
        //0	5.97	11.94	27.38	50.75	73	109.5	148	195.5	250	297	375	437	500	594	672	781	875	969	1079	1218	1360	1469	1609	1750	1906	2063	2203	2375	2563	2734	2906	3110	3312	3500	3688	3906	4141	4375	4594	4844	5094	5328	5609	5860	6156	6422	6688	6984	7235	7547	7859	8157	8500	8813	9172	9484	9766	10125	10516
        final float str1Tokens = tokeniser.tokenizeToArrayList(string1).size();
        final float str2Tokens = tokeniser.tokenizeToArrayList(string2).size();
        return (((str1Tokens + str2Tokens) * str1Tokens) + ((str1Tokens + str2Tokens) * str2Tokens)) * ESTIMATEDTIMINGCONST;
    }

    /**
     * gets the similarity of the two strings using Monge Elkan.
     *
     * @param string1
     * @param string2
     * @return a value between 0-1 of the similarity
     */
    public final float getSimilarity(final String string1, final String string2) {
        //split the strings into tokens for comparison
        final ArrayList<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);

        float sumMatches = 0.0f;
        float maxFound;
        for (Object str1Token : str1Tokens) {
            maxFound = 0.0f;
            for (Object str2Token : str2Tokens) {
                final float found = internalStringMetric.getSimilarity((String) str1Token, (String) str2Token);
                if (found > maxFound) {
                    maxFound = found;
                }
            }
            sumMatches += maxFound;
        }
        return sumMatches / (float) str1Tokens.size();
    }

    /**
     * gets the un-normalised similarity measure of the metric for the given strings.
     *
     * @param string1
     * @param string2
     * @return returns the score of the similarity measure (un-normalised)
     */
    public float getUnNormalisedSimilarity(String string1, String string2) {
        //todo check this is valid before use mail sam@dcs.shef.ac.uk if problematic
        return getSimilarity(string1, string2);
    }
}

