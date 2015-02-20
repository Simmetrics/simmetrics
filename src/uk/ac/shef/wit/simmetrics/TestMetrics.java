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

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.metrichandlers.MetricHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: n/a
 * Description: uk.ac.shef.wit.simmetrics.TestMetrics implements a test of metrics available.

 * Date: 24-Mar-2004
 * Time: 11:09:08
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class TestMetrics {

    /**
     * the min time each test is performed for to get an accurate averaged timing of performance.
     */
    private static final int TESTTIMINGMILLISECONDSPERTEST = 200;

    /**
     * the max string length for a timing test.
     */
    private static final int TESTMAXLENGTHTIMINGTEST = 3000;

    /**
     * the step size for string length tests.
     */
    private static final int TESTMAXLENGTHTIMINGSTEPSIZE = 50;

    /**
     * the length of terms in the timing test.
     */
    private static final int TESTMAXLENGTHTIMINGTERMLENGTH = 10;

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
        final ArrayList<AbstractStringMetric> testMetricArrayList = new ArrayList<AbstractStringMetric>();
        for(String metricString : metricStrings) {
        	System.out.println(metricString);
            testMetricArrayList.add(MetricHandler.createMetric(metricString));
        }
        //test metrics
       // testMethod(testMetricArrayList, args);
    }

    /**
     * details the usage of the class from command line instatiation.
     */
    private static void Usage() {
        System.out.println("Usage: testMethod \"String1 to Test\" \"String2 to test\"");
        System.out.println("or");
        System.out.println("Usage: testMethod \"timing");
        System.out.println("AS NO INPUT - running defualt test cases\n");
    }

    /**
     * perform test on given array of metrics.
     *
     * @param metricVector
     * @param args         arguments vector
     */
    private static void testMethod(final ArrayList<AbstractStringMetric> metricVector, final String[] args) {

        boolean useCmdArgs = false;
        boolean testTimingComplexity = false;
        if (args.length == 1) {
            testTimingComplexity = true;
        } else if (args.length == 2) {
            useCmdArgs = true;
        } else {
            Usage();
        }

        //first detail tests being performed
        System.out.println("Performing Tests with Following Metrics:");
        for (int i = 0; i < metricVector.size(); i++) {
            System.out.println("m" + (i + 1) + " " + ((metricVector.get(i))).getShortDescriptionString());
        }
        System.out.println();

        //secondly detail test cases being performed
        if (!useCmdArgs) {
            System.out.println("Using the Following Test Cases:");
            for (int i = 0; i < testCases.length; i++) {
                System.out.println("t" + (i + 1) + " \"" + testCases[i][0] + "\" vs \"" + testCases[i][1] + "\"");
            }
            System.out.println();
        } else {
            System.out.println("Using the Input Test Case:");
            System.out.println("t1 \"" + args[0] + "\" vs \"" + args[1] + "\"");
            System.out.println();
        }

        //now perform tests
        System.out.print("  \t");
        if (!useCmdArgs) {
            for (int j = 0; j < testCases.length; j++) {
                if(j < 9) {
                    System.out.print("t" + (j + 1) + "=\t (t" + (j + 1) + "ms)\t");
                } else {
                    System.out.print("t" + (j + 1) + "= (t" + (j + 1) + "ms)");
                }
            }
        } else {
            System.out.print("t1");
        }
        System.out.print("\n");
        final DecimalFormat df = new DecimalFormat("0.00");
        int metricTests = 0;
        long totalTime = System.currentTimeMillis();
        for (int i = 0; i < metricVector.size(); i++) {
            final AbstractStringMetric metric = metricVector.get(i);
            System.out.print("m" + (i + 1) + "\t");
            if(testTimingComplexity) {
                //testing timing
                //generate random string to test
                final StringBuffer testString = new StringBuffer();
                int termLen = 0;
                for(int len=1; len<TESTMAXLENGTHTIMINGTEST; len++, termLen++) {
                    if(termLen < TESTMAXLENGTHTIMINGTERMLENGTH) {
                        testString.append((char)(((int)'a') +  (int)(Math.random() * (((float)'z')-((float)'a')))));
                    } else {
                        testString.append(' ');
                        termLen = 0;
                    }
                }
                //iterate through lengths of string to test
                for(int len=1; len<TESTMAXLENGTHTIMINGTEST; len += TESTMAXLENGTHTIMINGSTEPSIZE) {
                    long timeTaken = 0;
                    int iterations = 0;
                    final String input1 = testString.substring(0,len);
                    while(timeTaken < TESTTIMINGMILLISECONDSPERTEST) {
                        timeTaken += metric.getSimilarityTimingActual(input1, input1);
                        iterations++;
                        metricTests++;
                    }
                    System.out.print(df.format((float)timeTaken/(float)iterations) + "\t");
                }
            } else if (!useCmdArgs) {
                //testing default input test
                for (String[] testCase : testCases) {
                    final float result = metric.getSimilarity(testCase[0], testCase[1]);
                    metricTests++;
                    long timeTaken = 0;
                    int iterations = 0;
                    while (timeTaken < TESTTIMINGMILLISECONDSPERTEST) {
                        timeTaken += metric.getSimilarityTimingActual(testCase[0], testCase[1]);
                        iterations++;
                        metricTests++;
                    }
                    System.out.print(df.format(result) + " (" + df.format((float) timeTaken / (float) iterations) + ")\t");
                }
            } else {
                //testing input strings
                final float result = metric.getSimilarity(args[0], args[1]);
                metricTests++;
                long timeTaken = 0;
                int iterations = 0;
                while(timeTaken < 250) {
                    timeTaken += metric.getSimilarityTimingActual(args[0], args[1]);
                    iterations++;
                    metricTests++;
                }
                System.out.print(df.format(result) + " (" + df.format((float)timeTaken/(float)iterations) + ")\t");
            }
            System.out.print("\t(" + metric.getShortDescriptionString() + ") - testsSoFar = " + metricTests + "\n");
        }
        //output time taken
        totalTime = (System.currentTimeMillis() - totalTime);
        System.out.println("\nTotal Metrics Tests = " + metricTests + " in " + totalTime + "ms\t\t meaning " + df.format((float)metricTests/(float)totalTime) + " tests per millisecond");
    }
}
