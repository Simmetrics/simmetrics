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

package uk.ac.shef.wit.simmetrics;

import uk.ac.shef.wit.simmetrics.arbitrators.InterfaceMetricArbitrator;
import uk.ac.shef.wit.simmetrics.arbitrators.MeanMetricArbitrator;
import uk.ac.shef.wit.simmetrics.similaritymetrics.*;
import uk.ac.shef.wit.simmetrics.metrichandlers.MetricHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Description: uk.ac.shef.wit.simmetrics.TestArbitrators implements a test class for uk.ac.shef.wit.simmetrics.arbitrators.
 * Date: 29-Apr-2004
 * Time: 12:45:12
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class TestArbitrators {

    /** string to perform tests with.*/
    private static final String string1 = "Sam J Chapman";
    /** string to perform tests with.*/
    private static final String string2 = "Samuel Chapman";
    /** string to perform tests with.*/
    private static final String string3 = "S Chapman";
    /** string to perform tests with.*/
    private static final String string4 = "Samuel John Chapman";
    /** string to perform tests with.*/
    private static final String string5 = "John Smith";
    /** string to perform tests with.*/
    private static final String string6 = "Richard Smith";
    /** string to perform tests with.*/
    private static final String string7 = "aaaa mnop zzzz";
    /** string to perform tests with.*/
    private static final String string8 = "bbbb mnop yyyy";
    /** string to perform tests with.*/
    private static final String string9 = "aa mnop zzzzzz";
    /** string to perform tests with.*/
    private static final String string10 = "a";
    /** string to perform tests with.*/
    private static final String string11 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    /** string to perform tests with.*/
    private static final String string12 = "aaaaa bcdefgh mmmmmmmm stuvwx zzzzzz";
    /** string to perform tests with.*/
    private static final String string13 = "jjjjj bcdefgh qqqqqqqq stuvwx yyyyyy";
    /** string to perform tests with.*/
    /** string to perform tests with.*/
    private static final String string14 = "aaaaa bcdefgh stuvwx zzzzzz";
    /** string to perform tests with.*/
    private static final String string15 = "aaaaa aaaaa aaaaa zzzzzz";
    /** string to perform tests with.*/
    private static final String string16 = "aaaaa aaaaa";

    /**
     * test cases to perform.
     */
    private static final String[][] testCases = {
        {string1,string2},
        {string1, string3},
        {string2, string3},
        {string1, string1},
        {string4,string5},
        {string5,string6},
        {string5, string1},
        {string1, string6},
        {string1,string4},
        {string2,string4},
        {string7, string8},
        {string7, string9},
        {string8, string9},
        {string10, string10},
        {string11, string11},
        {string10, string11},
        {string12, string13},
        {string12, string14},
        {string14, string15},
        {string16, string16}};

    /**
     * tests the metrics.
     *
     * @param args std arguments vector
     */
    public static void main(final String[] args) {

        List<String> metricStrings = MetricHandler.GetMetricsAvailable();

        //now create each metric in an ArrayList
        final ArrayList<InterfaceStringMetric> testMetricArrayList = new ArrayList<InterfaceStringMetric>();
        for(String metricString : metricStrings) {
            testMetricArrayList.add(MetricHandler.createMetric(metricString));
        }

        final InterfaceMetricArbitrator arbitrator = new MeanMetricArbitrator();
        arbitrator.setArbitrationMetrics(testMetricArrayList);

        //test metrics
        testMethod(arbitrator);
    }

    /**
     * perform test on given array of metrics.
     *
     * @param arbitrator the InterfaceMetricArbitrator to test
     */
    private static void testMethod(final InterfaceMetricArbitrator arbitrator) {

        //first detail test being performed
        System.out.println("Performing Arbitrartion with: " + arbitrator.getShortDescriptionString());

        System.out.println("Using the Following Test Cases:");
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("t" + (i + 1) + " \"" + testCases[i][0] + "\" vs \"" + testCases[i][1] + "\"");
        }
        System.out.println();

        //now perform tests
        final DecimalFormat df = new DecimalFormat("0.00");
        for (String[] testCase : testCases) {
            final float result = arbitrator.getArbitrationScore(testCase[0], testCase[1]);
            System.out.println(df.format(result) + " for \"" + testCase[0] + "\" vs \"" + testCase[1] + "\"");
        }
    }
}

