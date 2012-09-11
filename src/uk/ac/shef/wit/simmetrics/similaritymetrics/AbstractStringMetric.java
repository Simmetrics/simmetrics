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

import java.util.Arrays;

/**
 * Package: uk.ac.shef.wit.simmetrics.api Description: AbstractStringMetric implements a abstract class for the string metrics. Date: 24-Mar-2004
 * Time: 12:07:10
 *
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public abstract class AbstractStringMetric implements InterfaceStringMetric {

    /**
     * reports the metric type.
     *
     * @return returns a short description of the metric
     */
    public abstract String getShortDescriptionString();

    /**
     * reports the metric type.
     *
     * @return returns a long description of the metric
     */
    public abstract String getLongDescriptionString();

    /**
     * gets a div class xhtml similarity explaining the operation of the metric.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return a div class html section detailing the metric operation.
     */
    public abstract String getSimilarityExplained(String string1, String string2);
    
    private transient float[] results;

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
    public final long getSimilarityTimingActual(final String string1, final String string2) {
        //initialise timing
        final long timeBefore = System.currentTimeMillis();
        //perform measure
        getSimilarity(string1, string2);
        //get time after process
        final long timeAfter = System.currentTimeMillis();
        //output time taken
        return timeAfter - timeBefore;
    }

    /**
     * does a batch comparison of the set of strings with the given
     * comparator string returning an array of results equal in length
     * to the size of the given set of strings to test.
     *
     * @param set an array of strings to test against the comparator string
     * @param comparator the comparator string to test the array against
     *
     * @return an array of results equal in length to the size of the
     * given set of strings to test.
     */
    public final float[] batchCompareSet(final String[] set, final String comparator) {
        this.results = new float[set.length];
        for(int strNum=0; strNum<set.length; strNum++) {
            //perform similarity test
            results[strNum] = getSimilarity(set[strNum],comparator);
        }
        return Arrays.copyOf(this.results, this.results.length);
    }

    /**
     * does a batch comparison of one set of strings against another set
     * of strings returning an array of results equal in length
     * to the minimum size of the given sets of strings to test.
     *
     * @param firstSet an array of strings to test
     * @param secondSet an array of strings to test the first array against
     *
     * @return an array of results equal in length to the minimum size of
     * the given sets of strings to test.
     */
    public final float[] batchCompareSets(final String[] firstSet, final String[] secondSet) {
        //sets the results to equal the shortest string length should they differ.
        if(firstSet.length <= secondSet.length) {
            this.results = new float[firstSet.length];
        } else {
        	this.results = new float[secondSet.length];
        }
        for(int strNum=0; strNum<results.length; strNum++) {
            //perform similarity test
        	this.results[strNum] = getSimilarity(firstSet[strNum],secondSet[strNum]);
        }
        return Arrays.copyOf(this.results, this.results.length);
    }

    /**
     * gets the estimated time in milliseconds it takes to perform a similarity timing.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return the estimated time in milliseconds taken to perform the similarity measure
     */
    public abstract float getSimilarityTimingEstimated(final String string1, final String string2);

    /**
     * gets the similarity measure of the metric for the given strings.
     *
     * @param string1
     * @param string2
     *
     * @return returns a value 0-1 of similarity 1 = similar 0 = not similar
     */
    public abstract float getSimilarity(String string1, String string2);

    /**
     * gets the un-normalised similarity measure of the metric for the given strings.
     *
     * @param string1
     * @param string2
     *
     * @return returns the score of the similarity measure (un-normalised)
     */
    public abstract float getUnNormalisedSimilarity(String string1, String string2);
}
