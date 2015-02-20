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
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.blockdistance
 * Description: blockdistance implements a

 * Date: 29-Mar-2004
 * Time: 14:00:40
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class BlockDistance extends AbstractStringMetric implements Serializable  {

	private static final long serialVersionUID = 3164431589505975923L;

	/**
     * a constant for calculating the estimated timing cost.
     */
    private static final float EST_TIM_COST = 6.4457142857142857142857142857146e-5f;

    /**
     * private tokeniser for tokenisation of the query strings.
     */
    private InterfaceTokeniser tokeniser;

    /**
     * constructor - default (empty).
     */
    public BlockDistance() {
    	super();
        tokeniser = new TokeniserWhitespace();
    }

    /**
     * constructor.
     *
     * @param tokeniserToUse - the tokeniser to use should a different tokeniser be required
     */
    public BlockDistance(final InterfaceTokeniser tokeniserToUse) {
    	super();
        tokeniser = tokeniserToUse;
    }

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "BlockDistance";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Block distance algorithm whereby vector space block distance is used to determine a similarity";
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
        //0	0.03	0.04	0.08	0.13	0.19	0.26	0.34	0.44	0.54	0.67	0.78	0.92	1.07	1.24	1.38	1.6	1.77	2.01	2.15	2.39	2.64	2.86	3.12	3.38	3.69	4.08	4.72	6.15	5.8	5.21	6.77	5.97	7.52	6.8	8.83	7.81	9.23	8.46	11.28	9.23	11.94	10.2	13.53	11.28	13.53	12.69	15.62	13.53	16.92	14.57	18.45	15.62	20.3	16.92	22.56	16.92	25.5	18.45	25.38
        final float str1Tokens = tokeniser.tokenizeToArrayList(string1).size();
        final float str2Tokens = tokeniser.tokenizeToArrayList(string2).size();
        return (((str1Tokens + str2Tokens) * str1Tokens) + ((str1Tokens + str2Tokens) * str2Tokens)) * EST_TIM_COST;
    }

    /**
     * gets the similarity of the two strings using BlockDistance.
     *
     * @param string1
     * @param string2
     *
     * @return a 0-1 similarity score
     */
    public float getSimilarity(final String string1, final String string2) {
        final ArrayList<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);

        final float totalPossible = (float)(str1Tokens.size() + str2Tokens.size());

        final float totalDistance = getUnNormalisedSimilarity(string1, string2);
        return (totalPossible - totalDistance) / totalPossible;
    }

    /**
     * gets the un-normalised similarity of the two strings using BlockDistance.
     *
     * @param string1
     * @param string2
     * @return a block distance similarity score
     */
    public float getUnNormalisedSimilarity(final String string1, final String string2) {
        final ArrayList<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);

        final Set<Object> allTokens = new HashSet<Object>();
        allTokens.addAll(str1Tokens);
        allTokens.addAll(str2Tokens);

        int totalDistance = 0;
        for (Object allToken : allTokens) {
            final String token = (String) allToken;
            int countInString1 = 0;
            int countInString2 = 0;
            for (Object str1Token : str1Tokens) {
                final String sToken = (String) str1Token;
                if (sToken.equals(token)) {
                    countInString1++;
                }
            }
            for (Object str2Token : str2Tokens) {
                final String sToken = (String) str2Token;
                if (sToken.equals(token)) {
                    countInString2++;
                }
            }
            if (countInString1 > countInString2) {
                totalDistance += (countInString1 - countInString2);
            } else {
                totalDistance += (countInString2 - countInString1);
            }
        }
        return totalDistance;
    }

	public InterfaceTokeniser getTokeniser() {
		
		return tokeniser;
		
	}

	public void setTokeniser(final InterfaceTokeniser tokeniser) {
		
		this.tokeniser = tokeniser;
		
	}

}

