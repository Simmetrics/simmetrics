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

import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

import java.text.DecimalFormat;
import java.util.Vector;

/**
 *
 * Description: uk.ac.shef.wit.simmetrics.TestEstimatedTimes tests the estimated times of string metrics.
 * Date: 26-Apr-2004
 * Time: 16:26:53
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class TestEstimatedTimes {

    /**
     * the min time each test is performed for to get an accurate averaged timing of performance.
     */
    private static final int TESTTIMINGMILLISECONDSPERTEST = 200;

    /**
     * the max string length for a timing test.
     */
    private static final int TESTMAXLENGTHTIMINGTEST = 2000;

    /**
     * the step size for string length tests.
     */
    private static final int TESTMAXLENGTHTIMINGSTEPSIZE = 50;

    /**
     * the length of terms in the timing test.
     */
    private static final int TESTMAXLENGTHTIMINGTERMLENGTH = 10;

    /**
     * tests the metrics.
     *
     * @param args std arguments vector
     */
    public static void main(final String[] args) {

        //now do metric tests
        final Vector<InterfaceStringMetric> testMetricVector = new Vector<InterfaceStringMetric>();
        testMetricVector.add(new Levenshtein());
        testMetricVector.add(new NeedlemanWunch());
        testMetricVector.add(new SmithWaterman());
        testMetricVector.add(new ChapmanLengthDeviation());
        testMetricVector.add(new ChapmanMeanLength());
        testMetricVector.add(new SmithWatermanGotoh());
        testMetricVector.add(new SmithWatermanGotohWindowedAffine());
        testMetricVector.add(new BlockDistance());
        testMetricVector.add(new MongeElkan());
        testMetricVector.add(new Jaro());
        testMetricVector.add(new JaroWinkler());
        testMetricVector.add(new Soundex());
        testMetricVector.add(new ChapmanMatchingSoundex());
        testMetricVector.add(new MatchingCoefficient());
        testMetricVector.add(new DiceSimilarity());
        testMetricVector.add(new JaccardSimilarity());
        testMetricVector.add(new OverlapCoefficient());
        testMetricVector.add(new EuclideanDistance());
        testMetricVector.add(new CosineSimilarity());
        testMetricVector.add(new QGramsDistance());

        //test metrics
        testMethod(testMetricVector, args);
    }

    /**
     * perform test on given array of metrics.
     *
     * @param metricVector
     * @param args         arguments vector
     */
    private static void testMethod(final Vector<InterfaceStringMetric> metricVector, final String[] args) {

        System.out.println("Usage: testMethod ");
        System.out.println("AS NO INPUT - running defualt test\n");

        //first detail tests being performed
        System.out.println("Performing Tests with Following Metrics:");
        for (int i = 0; i < metricVector.size(); i++) {
            System.out.println("m" + (i + 1) + " " + ((AbstractStringMetric) (metricVector.get(i))).getShortDescriptionString());
        }
        System.out.println();

        //now perform tests
        System.out.print("\n");
        final DecimalFormat df = new DecimalFormat("0.00");
        int metricTests = 0;
        long totalTime = System.currentTimeMillis();
        for (int i = 0; i < metricVector.size(); i++) {
            final AbstractStringMetric metric = (AbstractStringMetric) metricVector.get(i);
            System.out.print("m" + (i + 1) + "\t");
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
                System.out.print(df.format((float)timeTaken/(float)iterations) + "(" + metric.getSimilarityTimingEstimated(input1, input1)+ ")\t");
            }
            System.out.print("\t(" + metric.getShortDescriptionString() + ") - testsSoFar = " + metricTests + "\n");
        }
        //output time taken
        totalTime = (System.currentTimeMillis() - totalTime);
        System.out.println("\nTotal Metrics Tests = " + metricTests + " in " + totalTime + "ms\t\t meaning " + df.format((float)metricTests/(float)totalTime) + " tests per millisecond");
    }
}

