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
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.euclideandistance
 * Description: uk.ac.shef.wit.simmetrics.similaritymetrics.euclideandistance implements a

 * Date: 05-Apr-2004
 * Time: 11:12:01
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.2
 */
public final class EuclideanDistance extends AbstractStringMetric implements Serializable {

	private static final long serialVersionUID = 2349593877333376417L;

	/**
     * a constant for calculating the estimated timing cost.
     */
    private static final float EST_TIM_CONST = 7.4457142857142857142857142857146e-5f;

    /**
     * private tokeniser for tokenisation of the query strings.
     */
    private InterfaceTokeniser tokeniser;

    /**
     * constructor - default (empty).
     */
    public EuclideanDistance() {
    	super();
        tokeniser = new TokeniserWhitespace();
    }

    /**
     * constructor.
     *
     * @param tokeniserToUse - the tokeniser to use should a different tokeniser be required
     */
    public EuclideanDistance(final InterfaceTokeniser tokeniserToUse) {
    	super();
        tokeniser = tokeniserToUse;
    }

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "EuclideanDistance";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Euclidean Distancey algorithm providing a similarity measure between two stringsusing the vector space of combined terms as the dimensions";
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
        //0	0.02	0.04	0.08	0.13	0.19	0.26	0.33	0.43	0.53	0.64	0.77	0.9	1.09	1.27	1.39	1.57	1.83	1.93	2.27	2.42	2.82	2.9	3.56	3.44	4.32	3.9	5.51	4.51	6.15	5.34	6.77	5.97	7.81	6.8	8.83	7.52	9.67	8.46	10.68	9.23	11.94	10.2	13.53	11.28	14.5	11.94	15.62	13.53	16.92	14.57	16.92	15.62	20.3	16.92	22.56	16.92	24.33	18.45	24.33
        final float str1Tokens = tokeniser.tokenizeToArrayList(string1).size();
        final float str2Tokens = tokeniser.tokenizeToArrayList(string2).size();
        return (((str1Tokens + str2Tokens) * str1Tokens) + ((str1Tokens + str2Tokens) * str2Tokens)) * EST_TIM_CONST;
    }

    /**
     * gets the similarity of the two strings using EuclideanDistance
     *
     * the 0-1 return is calcualted from the maximum possible Euclidean
     * distance between the strings from the number of terms within them.
     *
     * @param string1
     * @param string2
     * @return a value between 0-1 of the similarity 1.0 identical
     */
    public float getSimilarity(final String string1, final String string2) {
        final ArrayList<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);
        final float totalPossible = (float) Math.sqrt((str1Tokens.size()*str1Tokens.size()) + (str2Tokens.size()*str2Tokens.size()));
        final float totalDistance = getUnNormalisedSimilarity(string1, string2);
        return (totalPossible - totalDistance) / totalPossible;
    }

    /**
     * gets the un-normalised similarity measure of the metric for the given strings.
     *
     * @param string1
     * @param string2
     * @return returns the score of the similarity measure (un-normalised)
     */
    public float getUnNormalisedSimilarity(final String string1, final String string2) {
        final ArrayList<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);

        final Set<String> allTokens = new HashSet<String>();
        allTokens.addAll(str1Tokens);
        allTokens.addAll(str2Tokens);

        float totalDistance = 0.0f;
        for (final String token : allTokens) {
            int countInString1 = 0;
            int countInString2 = 0;
            for (final String sToken : str1Tokens) {
                if (sToken.equals(token)) {
                    countInString1++;
                }
            }
            for (final String sToken : str2Tokens) {
                if (sToken.equals(token)) {
                    countInString2++;
                }
            }

            totalDistance += ((countInString1 - countInString2) * (countInString1 - countInString2));
        }

        totalDistance = (float) Math.sqrt(totalDistance);
        return totalDistance;
    }

    /**
     * gets the actual euclidean distance ie not the value between 0-1.
     *
     * @param string1
     * @param string2
     * @return the actual euclidean distance
     */
    public float getEuclidDistance(final String string1, final String string2) {
        final ArrayList<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);

        final Set<String> allTokens = new HashSet<String>();
        allTokens.addAll(str1Tokens);
        allTokens.addAll(str2Tokens);

        float totalDistance = 0.0f;
        for (final String token : allTokens) {
            int countInString1 = 0;
            int countInString2 = 0;
            for (final String sToken : str1Tokens) {
                if (sToken.equals(token)) {
                    countInString1++;
                }
            }
            for (final String sToken : str2Tokens) {
                if (sToken.equals(token)) {
                    countInString2++;
                }
            }

            totalDistance += ((countInString1 - countInString2) * (countInString1 - countInString2));
        }

        return (float) Math.sqrt(totalDistance);
    }

	public InterfaceTokeniser getTokeniser() {
		return tokeniser;
	}

	public void setTokeniser(final InterfaceTokeniser tokeniser) {
		this.tokeniser = tokeniser;
	}
    
}




