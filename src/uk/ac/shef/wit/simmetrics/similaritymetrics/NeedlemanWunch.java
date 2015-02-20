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
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.SubCost01;

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.needlemanwunch
 * Description: needlemanwunch implements a edit distance function

 * Date: 24-Mar-2004
 * Time: 12:30:47
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class NeedlemanWunch extends AbstractStringMetric implements Serializable {

	private static final long serialVersionUID = 1485534898184628730L;

	/**
     * a constant for calculating the estimated timing cost.
     */
    private static final float EST_TIM_CONST = 1.842e-4f;

    /**
     * the private cost function used in the levenstein distance.
     */
    private AbstractSubstitutionCost dCostFunc;

    /**
     * the cost of a gap.
     */
    private float gapCost;

    /**
     * constructor - default (empty).
     */
    public NeedlemanWunch() {
    	super();
        //set the gapCost to a default value
        gapCost = 2.0f;
        //set the default cost func
        dCostFunc = new SubCost01();
    }

    /**
     * constructor.
     *
     * @param costG - the cost of a gap
     */
    public NeedlemanWunch(final float costG) {
    	super();
        //set the gapCost to a given value
        gapCost = costG;
        //set the cost func to a default function
        dCostFunc = new SubCost01();
    }

    /**
     * constructor.
     *
     * @param costG    - the cost of a gap
     * @param costFunc - the cost function to use
     */
    public NeedlemanWunch(final float costG, final AbstractSubstitutionCost costFunc) {
    	super();
        //set the gapCost to the given value
        gapCost = costG;
        //set the cost func
        dCostFunc = costFunc;
    }

    /**
     * constructor.
     *
     * @param costFunc - the cost function to use
     */
    public NeedlemanWunch(final AbstractSubstitutionCost costFunc) {
    	super();
        //set the gapCost to a default value
        gapCost = 2.0f;
        //set the cost func
        dCostFunc = costFunc;
    }

    /**
     * gets the gap cost for the distance function.
     *
     * @return the gap cost for the distance function
     */
    public float getGapCost() {
        return gapCost;
    }

    /**
     * sets the gap cost for the distance function.
     *
     * @param gapCost the cost of a gap
     */
    public void setGapCost(final float gapCost) {
        this.gapCost = gapCost;
    }

    /**
     * get the d(i,j) cost function.
     *
     * @return AbstractSubstitutionCost cost function used
     */
    public AbstractSubstitutionCost getdCostFunc() {
        return dCostFunc;
    }

    /**
     * sets the d(i,j) cost function used .
     *
     * @param dCostFunc - the cost function to use
     */
    public void setdCostFunc(final AbstractSubstitutionCost dCostFunc) {
        this.dCostFunc = dCostFunc;
    }

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "NeedlemanWunch";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Needleman-Wunch algorithm providing an edit distance based similarity measure between two strings";
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
        //0	0.44	1.13	2.45	4.41	7	9.67	13.53	18.45	22.67	27.25	36.5	40.6	47	54.5	62.5	73	83.33	88.67	99	109.5	140.5	148.5	172	172	179.5	219	218	297	250	282	328	297	375	343	375	422	453	453	500	500	547	578	547	579	625	640	656	719	844	781	828	844	875	906	938	969	1000	1250	1078
        final float str1Length = string1.length();
        final float str2Length = string2.length();
        return (str1Length * str2Length) * EST_TIM_CONST;
    }

    /**
     * gets the similarity of the two strings using Needleman Wunch distance.
     *
     * @param string1
     * @param string2
     * @return a value between 0-1 of the similarity
     */
    public float getSimilarity(final String string1, final String string2) {
        float needlemanWunch = getUnNormalisedSimilarity(string1, string2);

        //normalise into zero to one region from min max possible
        float maxValue = Math.max(string1.length(), string2.length());
        float minValue = maxValue;
        if (dCostFunc.getMaxCost() > gapCost) {
            maxValue *= dCostFunc.getMaxCost();
        } else {
            maxValue *= gapCost;
        }
        if (dCostFunc.getMinCost() < gapCost) {
            minValue *= dCostFunc.getMinCost();
        } else {
            minValue *= gapCost;
        }
        if (minValue < 0.0f) {
            maxValue -= minValue;
            needlemanWunch -= minValue;
        }

        //check for 0 maxLen
        if (maxValue == 0) {
            return 1.0f; //as both strings identically zero length
        } else {
            //return actual / possible NeedlemanWunch distance to get 0-1 range
            return 1.0f - (needlemanWunch / maxValue);
        }

    }

    /**
     * implements the NeedlemanWunch distance function.
     *
     * @param s
     * @param t
     * @return the NeedlemanWunch distance for the given strings
     */
    public float getUnNormalisedSimilarity(final String s, final String t) {
        float[][] d; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        float cost; // cost

        // check for zero length input
        n = s.length();
        m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }

        //create matrix (n+1)x(m+1)
        d = new float[n + 1][m + 1];

        //put row and column numbers in place
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // cycle through rest of table filling values from the lowest cost value of the three part cost function
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= m; j++) {
                // get the substution cost
                cost = dCostFunc.getCost(s, i - 1, t, j - 1);

                // find lowest cost at point from three possible
                d[i][j] = MathFuncs.min3(d[i - 1][j] + gapCost, d[i][j - 1] + gapCost, d[i - 1][j - 1] + cost);
            }
        }

        // return bottom right of matrix as holds the maximum edit score
        return d[n][m];
    }
}
