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

package uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions;

import uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions.AbstractAffineGapCost;

import java.io.Serializable;

/**
 * Package: costfunctions
 * Description: AffineGap1_1Over3 implements a Affine Gap cost function.

 * Date: 29-Mar-2004
 * Time: 17:03:22
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
final public class AffineGap1_1Over3 extends AbstractAffineGapCost implements Serializable {

	private static final long serialVersionUID = 4307068127839803370L;

	/**
     * returns the name of the cost function.
     *
     * @return the name of the cost function
     */
    public final String getShortDescriptionString() {
        return "SubCost01";
    }

    /**
     * get cost between characters.
     *
     * @param stringToGap         - the string to get the cost of a gap
     * @param stringIndexStartGap - the index within the string to test a start gap from
     * @param stringIndexEndGap   - the index within the string to test a end gap to
     * @return the cost of a Gap G
     */
    public final float getCost(final String stringToGap, final int stringIndexStartGap, final int stringIndexEndGap) {
        if (stringIndexStartGap >= stringIndexEndGap) {
            return 0.0f;
        } else {
            return 1.0f + (((stringIndexEndGap - 1) - stringIndexStartGap) * (1.0f / 3.0f));
        }
    }

    /**
     * returns the maximum possible cost.
     *
     * @return the maximum possible cost
     */
    public final float getMaxCost() {
        return 1.0f;
    }

    /**
     * returns the minimum possible cost.
     *
     * @return the minimum possible cost
     */
    public final float getMinCost() {
        return 0.0f;
    }
}

