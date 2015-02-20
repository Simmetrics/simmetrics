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

/**
 * Package: uk.ac.shef.wit.simmetrics.similaritymetrics.chapmanmeanlength
 * Description: chapmanmeanlength implements a simple mean length metric, i.e. strings of the similar lengths are more similar regardless of the content.

 * Date: 26-Mar-2004
 * Time: 14:15:54
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.2
 */
public final class ChapmanMeanLength extends AbstractStringMetric implements Serializable {

	private static final long serialVersionUID = 5762784646476243942L;

    /**
     * defines the internal max string length beyond which 1.0 is always returned.
     */
    final private static int MEAN_MAX = 500;

    /**
     * returns the string identifier for the metric.
     *
     * @return the string identifier for the metric
     */
    public String getShortDescriptionString() {
        return "ChapmanMeanLength";
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return "Implements the Chapman Mean Length algorithm provides a similarity measure between two strings from size of the mean length of the vectors - this approach is suppossed to be used to determine which metrics may be best to apply rather than giveing a valid responce itself";
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
        //return 0 as this similarity metric is near to zero milliseconds and is independent
        return 0;
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
     * gets the similarity of the two strings using ChapmanMeanLength
     *
     * this is simply an average of the string lengths between those compared then scaled.
     *
     * @param string1
     * @param string2
     *
     * @return a value between 0-1 of the similarity
     */
    public float getSimilarity(final String string1, final String string2) {
        final float bLength = string2.length() + string1.length();
        float res;
        if (bLength > MEAN_MAX) {
            res = 1.0f;
        } else {
            final float oneMinbLength = (MEAN_MAX - bLength) / MEAN_MAX;
            res = 1.0f - (oneMinbLength * oneMinbLength * oneMinbLength * oneMinbLength);
        }
        return res;
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
        return getSimilarity(string1, string2);
    }
}
