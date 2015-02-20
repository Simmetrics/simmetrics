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
import uk.ac.shef.wit.simmetrics.similaritymetrics.ChapmanLengthDeviation;
import uk.ac.shef.wit.simmetrics.similaritymetrics.ChapmanMatchingSoundex;
import uk.ac.shef.wit.simmetrics.similaritymetrics.ChapmanMeanLength;
import uk.ac.shef.wit.simmetrics.similaritymetrics.EuclideanDistance;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;
import uk.ac.shef.wit.simmetrics.similaritymetrics.MongeElkan;
import uk.ac.shef.wit.simmetrics.similaritymetrics.NeedlemanWunch;
import uk.ac.shef.wit.simmetrics.similaritymetrics.QGramsDistance;
import uk.ac.shef.wit.simmetrics.similaritymetrics.SmithWaterman;
import uk.ac.shef.wit.simmetrics.similaritymetrics.SmithWatermanGotoh;
import uk.ac.shef.wit.simmetrics.similaritymetrics.SmithWatermanGotohWindowedAffine;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Soundex;

/**
 * Package: uk.ac.shef.wit.simmetrics
 * Description: SimpleExample implements a simple example to demonstrate the ease to use a similarity metric.
 * Date: 19-Apr-2004
 * Time: 14:25:11
 *
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 */
public final class SimpleExample {
	
	private SimpleExample() {
		/**
		 * All methods static...
		 */
	}

    /**
     * runs a simple example.
     *
     * @param args two strings required for comparison
     *
     * @see this.usage() in the same source file
     * for more details on the usage instructions
     */
    public static void main(final String[] args) {

        //checks for the expected user input
   //     if(args.length != 2) {
            //provides usage information to inform the user of the correct way
            // to work
            //usage();
     //   } else {
            //gets the two input given by the user
            final String str1 = "johnathan";
            final String str2 = "  'g,    -      -,~'''~_,,`,,'.-'I.D.N0,.nahtanhoj                          ";

            //creates the single metric to use - in this case the simple
            // Levenshtein is used, this is far from recomended as much better
            // metrics can be employed in most cases, please see the sourceforge
            // SimMetric forums for advice on the best metric to employ in
            // differing situations.
            System.out.println(getMatchLikelyhood(str1, str2));
            AbstractStringMetric metric = new ChapmanLengthDeviation();
            float result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new ChapmanMatchingSoundex();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new ChapmanMeanLength(); 
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new EuclideanDistance();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new Levenshtein();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new MongeElkan();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new NeedlemanWunch();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new QGramsDistance();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new SmithWatermanGotoh();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new SmithWatermanGotohWindowedAffine();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new SmithWaterman();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);
            metric = new Soundex();
            result = metric.getSimilarity(str1, str2);
            outputResult(result, metric);

      //  }
    }
    
	private static float getMatchLikelyhood(final String str1, final String str2) {
		AbstractStringMetric metric;
		float avg = 0F, result = 0F;
		metric = new SmithWaterman();
		result = metric.getSimilarity(str1, str2);
		avg += result;
		metric = new SmithWatermanGotoh();
		result = metric.getSimilarity(str1, str2);
		avg += result;
		metric = new SmithWatermanGotohWindowedAffine();
		result = metric.getSimilarity(str1, str2);
		avg += result;
		metric = new MongeElkan();
		result = metric.getSimilarity(str1, str2);
		avg += result;
		return (avg / 4.0F) * 100.0F;
	}

    /**
     * outputs the result of the metric test.
     *
     * @param result the float result of the metric test
     * @param metric the metric itself to provide its description in the output
     * @param str1 the first string with which to compare
     * @param str2 the second string to compare with the first
     */
    private static void outputResult(final float result, final AbstractStringMetric metric) {
        System.out.println("Using Metric " + metric.getShortDescriptionString() + " gives a similarity score of " + (result * 100 / 1) + "%");

    }
    
}
