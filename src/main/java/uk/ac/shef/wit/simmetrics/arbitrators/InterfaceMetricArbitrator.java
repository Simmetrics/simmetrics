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

package uk.ac.shef.wit.simmetrics.arbitrators;

import uk.ac.shef.wit.simmetrics.similaritymetrics.InterfaceStringMetric;

import java.util.ArrayList;

/**
 * Package: uk.ac.shef.wit.simmetrics.api
 * Description: InterfaceMetricArbitrator provides an interface for a metric arbitrator.
 * Date: 29-Apr-2004
 * Time: 11:48:58
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public interface InterfaceMetricArbitrator {

    /**
     * gets the arbitration metrics used.
     * @return a vector of InterfaceStringMetric's used in the arbitration
     */
    public ArrayList<InterfaceStringMetric> getArbitrationMetrics();

    /**
     * set arbitration metrics with those given.
     * @param arbitrationMetrics a vector of InterfaceStringMetric's to be used in the arbitration
     */
    public void setArbitrationMetrics(ArrayList<InterfaceStringMetric> arbitrationMetrics);

    /**
     * adds an individual arbitration metric.
     * @param arbitrationMetric an InterfaceStringMetric to add to the arbitrated metrics.
     */
    public void addArbitrationMetric(InterfaceStringMetric arbitrationMetric);

    /**
     * adds a vector of InterfaceStringMetric's to those used for arbitration.
     * @param arbitrationMetrics a vectro of InterfaceStringMetric's to added to those used for arbitration.
     */
    public void addArbitrationMetrics(ArrayList<InterfaceStringMetric> arbitrationMetrics);

    /**
     * removes all arbitration metrics.
     */
    public void clearArbitrationMetrics();

    /**
     * returns a string of the MetricArbitrator name.
     *
     * @return a string of the MetricArbitrator name
     */
    public String getShortDescriptionString();

    /**
     * returns a long string of the MetricArbitrator description.
     *
     * @return a long string of the MetricArbitrator description
     */
    public String getLongDescriptionString();

    /**
     * gets the actual time in milliseconds it takes to perform an Arbitration.
     *
     * This call takes as long as the underlying metrics to perform so should not be performed in normal circumstances.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return the actual time in milliseconds taken to perform the MetricArbitrator
     */
    public long getArbitrationTimingActual(String string1, String string2);

    /**
     * gets the estimated time in milliseconds it takes to perform the arbitration.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return the estimated time in milliseconds taken to perform the similarity measure
     */
    public float getArbitrationTimingEstimated(String string1, String string2);

    /**
     * returns an arbitrated value of similarity.
     *
     * @param string1
     * @param string2
     *
     * @return a float between zero to one (zero = no similarity, one = matching strings)
     */
    public float getArbitrationScore(String string1, String string2);

    /**
     *
     */
    //todo add a method to update arbitration weightings or learn with input here
//    public void setArbitrationWeightings()

}
