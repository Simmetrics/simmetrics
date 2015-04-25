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

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.dicesimilarity
 * Description: uk.ac.shef.wit.simmetrics.similaritymetrics.dicesimilarity implements a

 * Date: 05-Apr-2004
 * Time: 10:30:34
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class DiceSimilarity extends AbstractStringMetric implements Serializable {

	private static final long serialVersionUID = 7803592238715566213L;

	/**
     * a constant for calculating the estimated timing cost.
     */
    private static final float EST_TIM_CONST = 0.00000034457142857142857142857142857146f;

    /**
     * private tokeniser for tokenisation of the query strings.
     */
    private InterfaceTokeniser tokeniser;

    /**
     * constructor - default (empty).
     */
    public DiceSimilarity() {
    	super();
        tokeniser = new TokeniserWhitespace();
    }

    /**
     * constructor.
     *
     * @param tokeniserToUse - the tokeniser to use should a different tokeniser be required
     */
    public DiceSimilarity(final InterfaceTokeniser tokeniserToUse) {
    	super();
        tokeniser = tokeniserToUse;
    }

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "DiceSimilarity";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the DiceSimilarity algorithm providing a similarity measure between two strings using the vector space of present terms";
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
        //0	0.01	0.03	0.05	0.08	0.1	0.14	0.18	0.22	0.27	0.33	0.38	0.46	0.51	0.6	0.65	0.76	0.83	0.93	1	1.1	1.21	1.31	1.44	1.54	1.65	1.78	1.92	2.06	2.18	2.33	2.51	2.64	2.78	2.99	3.17	3.29	3.5	3.9	5.8	4.14	5.21	4.51	5.8	4.86	6.55	5.34	7	5.8	7	6.34	8.16	6.77	9.23	7	9.67	7.52	10.15	8.46	10.2
        final float str1Length = string1.length();
        final float str2Length = string2.length();
        return (str1Length + str2Length) * ((str1Length + str2Length) * EST_TIM_CONST);
    }

    /**
     * gets the similarity of the two strings using DiceSimilarity
     * <p/>
     * Dices coefficient = (2*Common Terms) / (Number of terms in String1 + Number of terms in String2).
     *
     * @param string1
     * @param string2
     * @return a value between 0-1 of the similarity
     */
    public float getSimilarity(final String string1, final String string2) {
        final ArrayList<String> str1Tokens = tokeniser.tokenizeToArrayList(string1);
        final ArrayList<String> str2Tokens = tokeniser.tokenizeToArrayList(string2);

        final Set<String> allTokens = new HashSet<String>();
        allTokens.addAll(str1Tokens);
        final int termsInString1 = allTokens.size();
        final Set<String> secStrToks = new HashSet<String>();
        secStrToks.addAll(str2Tokens);
        final int termsInString2 = secStrToks.size();

        //now combine the sets
        allTokens.addAll(secStrToks);
        final int commonTerms = (termsInString1 + termsInString2) - allTokens.size();

        //return Dices coefficient = (2*Common Terms) / (Number of terms in String1 + Number of terms in String2)
        return (2.0f * commonTerms) / (termsInString1 + termsInString2);
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

	public InterfaceTokeniser getTokeniser() {
		return tokeniser;
	}

	public void setTokeniser(final InterfaceTokeniser tokeniser) {
		this.tokeniser = tokeniser;
	}
	
}


