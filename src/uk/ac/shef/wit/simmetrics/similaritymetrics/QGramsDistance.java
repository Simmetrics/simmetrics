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
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserQGram3Extended;

import java.io.Serializable;
import java.util.*;

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.qgrams
 * Description: uk.ac.shef.wit.simmetrics.similaritymetrics.qgrams implements a

 * Date: 05-Apr-2004
 * Time: 14:06:16
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class QGramsDistance extends AbstractStringMetric implements Serializable {

	private static final long serialVersionUID = 991983085343042434L;

	/**
     * a constant for calculating the estimated timing cost.
     */
    private static final float EST_TIM_CONST = 1.34e-4f;

    /**
     * private tokeniser for tokenisation of the query strings.
     */
    private InterfaceTokeniser tokeniser;

    /**
     * constructor - default (empty).
     */
    public QGramsDistance() {
    	super();
        tokeniser = new TokeniserQGram3Extended();
    }

    /**
     * constructor.
     *
     * @param tokeniserToUse - the tokeniser to use should a different tokeniser be required
     */
    public QGramsDistance(final InterfaceTokeniser tokeniserToUse) {
    	super();
        tokeniser = tokeniserToUse;
    }

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "QGramsDistance";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Q Grams Distance algorithm providing a similarity measure between two strings using the qGram approach check matching qGrams/possible matching qGrams";
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
        //0.01	0.51	1.48	3.08	5.51	7.81	11.94	15.62	20.3	25.38	31.29	36.5	43.8	50.75	62.5	67.67	78	88.67	93.67	117	125	133	148.5	172	171.5	204	218.5	219	219	296	282	281	344	312	360	375	390	422	438	453	484	500	516	562	563	594	625	656	656	703	719	766	765	813	828	875	891	937	953	985
        final float str1Length = string1.length();
        final float str2Length = string2.length();
        return (str1Length * str2Length) * EST_TIM_CONST;
    }

    /**
     * gets the similarity of the two strings using QGramsDistance.
     *
     * @param string1
     * @param string2
     * @return a value between 0-1 of the similarity
     */
    public float getSimilarity(final String string1, final String string2) {
    	
        final ArrayList<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);

        final int maxQGramsMatching = str1Tokens.size() + str2Tokens.size();
        
        float ret;

        //return
        if (maxQGramsMatching == 0) {
            ret = 0.0f;
        } else {
            ret = (maxQGramsMatching - getUnNormalisedSimilarity(string1, string2)) / (float) maxQGramsMatching;
        }
        
        return ret;
        
    }

    /**
     * gets the un-normalised similarity measure of the metric for the given strings.
     *
     * @param string1
     * @param string2
     *
     * @return returns the score of the similarity measure (un-normalised)
     */
    public float getUnNormalisedSimilarity(final String string1, final String string2) {
        final ArrayList<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);

        final Set<String> allTokens = new HashSet<String>();
        allTokens.addAll(str1Tokens);
        allTokens.addAll(str2Tokens);

        final Iterator<String> allTokensIt = allTokens.iterator();
        int difference = 0;
        while (allTokensIt.hasNext()) {
            final String token = allTokensIt.next();
            int matchingQGrams1 = 0;
            for (String str1Token : str1Tokens) {
                if (str1Token.equals(token)) {
                    matchingQGrams1++;
                }
            }
            int matchingQGrams2 = 0;
            for (String str2Token : str2Tokens) {
                if (str2Token.equals(token)) {
                    matchingQGrams2++;
                }
            }
            if (matchingQGrams1 > matchingQGrams2) {
                difference += (matchingQGrams1 - matchingQGrams2);
            } else {
                difference += (matchingQGrams2 - matchingQGrams1);
            }
        }

        //return
        return difference;
    }

	public InterfaceTokeniser getTokeniser() {
		return tokeniser;
	}

	public void setTokeniser(final InterfaceTokeniser tokeniser) {
		this.tokeniser = tokeniser;
	}
	
}


