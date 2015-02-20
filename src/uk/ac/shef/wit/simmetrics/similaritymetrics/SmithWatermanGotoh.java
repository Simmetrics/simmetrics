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

import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractAffineGapCost;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractSubstitutionCost;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AffineGap5_1;
import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.SubCost5_3_Minus3;

import java.io.Serializable;

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.smithwatermangotoh
 * Description: smithwatermangotoh implements the Gotoh extension of the smith waterman
 * method incorporating affine gaps in the strings

 * Date: 29-Mar-2004
 * Time: 14:49:12
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class SmithWatermanGotoh extends SmithWatermanGotohWindowedAffine implements Serializable {

	private static final long serialVersionUID = 4146093686489957649L;
	/**
     * a constant for calculating the estimated timing cost.
     */
    private static final float EST_TIME_CONST = 2.2e-5f;

    /**
     * constructor - default (empty).
     */
    public SmithWatermanGotoh() {
        //use the supers constructor
        super(new AffineGap5_1(), new SubCost5_3_Minus3(), Integer.MAX_VALUE);
    }

    /**
     * constructor.
     *
     * @param gapCostFunc - the gap cost function
     */
    public SmithWatermanGotoh(final AbstractAffineGapCost gapCostFunc) {
        //use the supers constructor
        super(gapCostFunc, new SubCost5_3_Minus3(), Integer.MAX_VALUE);
    }

    /**
     * constructor.
     *
     * @param gapCostFunc - the cost of a gap
     * @param costFunc    - the cost function to use
     */
    public SmithWatermanGotoh(final AbstractAffineGapCost gapCostFunc, final AbstractSubstitutionCost costFunc) {
        //use the supers constructor
        super(gapCostFunc, costFunc, Integer.MAX_VALUE);
    }

    /**
     * constructor.
     *
     * @param costFunc - the cost function to use
     */
    public SmithWatermanGotoh(final AbstractSubstitutionCost costFunc) {
        //use the supers constructor
        super(new AffineGap5_1(), costFunc, Integer.MAX_VALUE);
    }

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "SmithWatermanGotoh";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Smith-Waterman-Gotoh algorithm providing a similarity measure between two string";
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
        //0	11.28	62.5	203	453	875	1516	2468	3641	5203	7219	9516	12359	16000	19610	24187	29329	35203	42360	49531	57735	71376	77094	88516	105751	114875	130314	176516	203174	222626	209501	252440	340127	303002	378033	394768	454737	493534	543316	675364	693333	839599	888396	903865	1014194	1193148	1901926	1356620	1471435	1629765	1771628	1885786	2091056	2282059	2342764	2570659	2696911	2907181	3189467	3283458
        final float str1Length = string1.length();
        final float str2Length = string2.length();
        return ((str1Length * str2Length * str1Length) + (str1Length * str2Length * str2Length)) * EST_TIME_CONST;
    }
}


