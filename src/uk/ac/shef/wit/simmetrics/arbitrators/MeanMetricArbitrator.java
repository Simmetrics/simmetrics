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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Package: uk.ac.shef.wit.simmetrics.arbitrators
 * Description: MeanMetricArbitrator implements a simple arbitrator meaning the scores between metrics given.
 * Date: 29-Apr-2004
 * Time: 12:27:48
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class MeanMetricArbitrator implements InterfaceMetricArbitrator, Serializable  {


	private static final long serialVersionUID = 6121539490058904913L;
	/**
     * private vector of InterfaceStringMetric's used in the arbitration. 
     */
    private ArrayList<InterfaceStringMetric> metricsForArbitration = new ArrayList<InterfaceStringMetric>();

    /**
     * gets the arbitration metrics used.
     *
     * @return a vector of InterfaceStringMetric's used in the arbitration
     */
    public ArrayList<InterfaceStringMetric> getArbitrationMetrics() {
        return metricsForArbitration;
    }

    /**
     * set arbitration metrics with those given.
     *
     * @param arbitrationMetrics a vector of InterfaceStringMetric's to be used in the arbitration
     *
     * NB this does not perform any error checking so make sure the vector contains the correct type.
     */
    public void setArbitrationMetrics(final ArrayList<InterfaceStringMetric> arbitrationMetrics) {
        metricsForArbitration = arbitrationMetrics;
    }

    /**
     * adds an individual arbitration metric.
     *
     * @param arbitrationMetric an InterfaceStringMetric to add to the arbitrated metrics.
     */
    public void addArbitrationMetric(final InterfaceStringMetric arbitrationMetric) {
        metricsForArbitration.add(arbitrationMetric);
    }

    /**
     * adds a vector of InterfaceStringMetric's to those used for arbitration.
     *
     * @param arbitrationMetrics a vectro of InterfaceStringMetric's to added to those used for arbitration.
     *
     * NB this does not perform any error checking so make sure the vector contains the correct type.
     */
    public void addArbitrationMetrics(final ArrayList<InterfaceStringMetric> arbitrationMetrics) {
        metricsForArbitration.addAll(arbitrationMetrics);
    }

    /**
     * removes all arbitration metrics.
     */
    public void clearArbitrationMetrics() {
        metricsForArbitration.clear();
    }

    /**
     * returns a string of the MetricArbitrator name.
     *
     * @return a string of the MetricArbitrator name
     */
    public String getShortDescriptionString() {
        return "MeanMetricArbitrator";
    }

    /**
     * returns a long string of the MetricArbitrator description.
     *
     * @return a long string of the MetricArbitrator description
     */
    public String getLongDescriptionString() {
        return "MeanMetricArbitrator gives equal weightings too all metrics and returns an arbitrated score for all";
    }

    /**
     * gets the actual time in milliseconds it takes to perform an Arbitration.
     * <p/>
     * This call takes as long as the underlying metrics to perform so should not be performed in normal circumstances.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return the actual time in milliseconds taken to perform the MetricArbitrator
     */
    public long getArbitrationTimingActual(final String string1, final String string2) {
        //initialise timing
        final long timeBefore = System.currentTimeMillis();
        //perform measure
        getArbitrationScore(string1, string2);
        //get time after process
        final long timeAfter = System.currentTimeMillis();
        //output time taken
        return timeAfter - timeBefore;
    }

    /**
     * gets the estimated time in milliseconds it takes to perform the arbitration.
     *
     * @param string1 string 1
     * @param string2 string 2
     *
     * @return the estimated time in milliseconds taken to perform the similarity measure
     */
    public float getArbitrationTimingEstimated(final String string1, final String string2) {
        float estimatedTime = 0.0f;
        for (Object aMetricsForArbitration : metricsForArbitration) {
            estimatedTime += ((InterfaceStringMetric) aMetricsForArbitration).getSimilarityTimingEstimated(string1, string2);
        }
        return estimatedTime;
    }

    /**
     * returns an arbitrated value of similarity.
     *
     * @param string1
     * @param string2
     *
     * @return a float between zero to one (zero = no similarity, one = matching strings)
     */
    public float getArbitrationScore(final String string1, final String string2) {
        float score = 0.0f;
        if(metricsForArbitration.size() == 0) {
            return score;
        } else {
            for (Object aMetricsForArbitration : metricsForArbitration) {
                score += ((InterfaceStringMetric) aMetricsForArbitration).getSimilarity(string1, string2);
            }
            return score / metricsForArbitration.size();
        }
    }
}
