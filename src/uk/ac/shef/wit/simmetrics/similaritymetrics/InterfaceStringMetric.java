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

/**
 * Package: n/a Description: AbstractStringMetric implements an interface for the string metrics. Date: 24-Mar-2004
 * Time: 10:57:40
 *
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public interface InterfaceStringMetric {

    /**
     * returns a string of the string metric name.
     *
     * @return a string of the string metric name
     */
    String getShortDescriptionString();

    /**
     * returns a long string of the string metric description.
     *
     * @return a long string of the string metric description
     */
    String getLongDescriptionString();

    /**
     * gets the actual time in milliseconds it takes to perform a similarity timing.
     *
     * This call takes as long as the similarity metric to perform so should not be done in normal cercumstances.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return the actual time in milliseconds taken to perform the similarity measure
     */
    long getSimilarityTimingActual(String string1, String string2);

    /**
     * gets the estimated time in milliseconds it takes to perform a similarity timing.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return the estimated time in milliseconds taken to perform the similarity measure
     */
    float getSimilarityTimingEstimated(String string1, String string2);

    /**
     * returns a similarity measure of the string comparison.
     *
     * @param string1
     * @param string2
     *
     * @return a float between zero to one (zero = no similarity, one = matching strings)
     */
    float getSimilarity(String string1, String string2);

    /**
     * returns a similarity measure of the string comparison.
     *
     * @param string1
     * @param string2
     *
     * @return a float between zero to one (zero = no similarity, one = matching strings)
     */
    String getSimilarityExplained(String string1, String string2);
}
