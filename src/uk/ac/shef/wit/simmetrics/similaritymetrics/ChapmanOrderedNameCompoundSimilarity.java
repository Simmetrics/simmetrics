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
import java.util.List;

import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser;
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserWhitespace;

/**
 * Description: ChapmanOrderedNameCompoundSimilarity tests similarity upon the most similar in terms of token based
 * names where the later names are valued higher than earlier names. Surnames are less flexible than
 *
 * @author Sam Chapman, NLP Group, Sheffield Uni, UK
 *         (<a href="mailto:sam@dcs.shef.ac.uk">email</a>, <a href="http://www.dcs.shef.ac.uk/~sam/">website</a>)
 *         <p/>
 *         Date: 08-Dec-2005
 *         Time: 16:50:55
 */
public final class ChapmanOrderedNameCompoundSimilarity extends AbstractStringMetric implements Serializable {

	private static final long serialVersionUID = -8984615838296540798L;

	/**
     * a constant for calculating the estimated timing cost.
     */
    private static final float EST_TIM_CONST = 0.026571428571428571428571428571429f;
    
    private static final float SKEW_AMMOUNT = 1.0f;

    /**
     * private tokeniser for tokenisation of the query strings.
     */
    private InterfaceTokeniser tokeniser;

    /**
     * private string metric allowing internal metric to be composed.
     */
    private static final AbstractStringMetric METRIC_1 = new Soundex();

    /**
     * private string metric allowing internal metric to be composed.
     */
    private static final AbstractStringMetric METRIC_2 = new SmithWaterman();

    /**
     * constructor - default (empty).
     */
    public ChapmanOrderedNameCompoundSimilarity() {
    	super();
        tokeniser = new TokeniserWhitespace();
    }

    /**
     * constructor.
     *
     * @param tokeniserToUse - the tokeniser to use should a different tokeniser be required
     */
    public ChapmanOrderedNameCompoundSimilarity(final InterfaceTokeniser tokeniserToUse) {
    	super();
        tokeniser = tokeniserToUse;
    }

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "ChapmanOrderedNameCompoundSimilarity";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Chapman Ordered Name Compound Similarity algorithm whereby terms are matched and tested against the standard soundex algorithm - this is intended to provide a better rating for lists of proper names.";
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
        final float str1Tokens = tokeniser.tokenizeToArrayList(string1).size();
        final float str2Tokens = tokeniser.tokenizeToArrayList(string2).size();
        return (tokeniser.tokenizeToArrayList(string1).size() + tokeniser.tokenizeToArrayList(string2).size()) * ((str1Tokens+str2Tokens) * EST_TIM_CONST);
    }

    /**
     * gets the similarity of the two strings using a shifted weighting where the
     * latter tokens compared are more important than earlier ones.
     *
     * @param string1
     * @param string2
     * @return a value between 0-1 of the similarity
     */
    public float getSimilarity(final String string1, final String string2) {
    	
        //split the strings into tokens for comparison
        final List<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final List<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);
        final int str1TokenNum = str1Tokens.size();
        final int str2TokenNum = str2Tokens.size();
        final int minTokens = Math.min(str1TokenNum, str2TokenNum);

        float sumMatches = 0.0f;
        for (int i = 1; i <= minTokens; i++) {
            final float strWeightAdj = ((1.0f/minTokens)+(((((minTokens-i)+0.5f)-(minTokens/2.0f))/minTokens)*SKEW_AMMOUNT*(1.0f/minTokens)));
            final String sToken = (String) str1Tokens.get(str1TokenNum-i);
            final String tToken = (String) str2Tokens.get(str2TokenNum-i);

            final float found1 = METRIC_1.getSimilarity(sToken, tToken);
            final float found2 = METRIC_2.getSimilarity(sToken, tToken);
            sumMatches += ((0.5f * (found1+found2)) * strWeightAdj);
        }
        return sumMatches;
    }

    /**
     * gets the un-normalised similarity measure of the metric for the given strings.
     *
     * @param string1
     * @param string2
     * @return returns the score of the similarity measure (un-normalised)
     */
    public float getUnNormalisedSimilarity(final String string1, final String string2) {
        //todo check this is valid before use mail sam@dcs.shef.ac.uk if problematic
        return getSimilarity(string1, string2);
    }

	public InterfaceTokeniser getTokeniser() {
		return tokeniser;
	}

	public void setTokeniser(final InterfaceTokeniser tokeniser) {
		this.tokeniser = tokeniser;
	}

}



