/*
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

/**
 * SimpleExample implements a simple example to demonstrate the ease to use a
 * similarity metric.
 * 
 * @author Sam Chapman
 */
public class SimpleExample {

	/**
	 * Runs a simple example.
	 *
	 * @param args
	 *            two strings required for comparison
	 */
	public static void main(final String[] args) {

		checkInput(args);

		// gets the two input given by the user
		final String str1 = args[0];
		final String str2 = args[1];

		// InterfaceStringMetric metric = new Levenshtein();
		InterfaceStringMetric metric = new CosineSimilarity();
		// InterfaceStringMetric metric = new EuclideanDistance();
		// InterfaceStringMetric metric = new MongeElkan();

		final float result = metric.getSimilarity(str1, str2);

		String message = "Using metric %s on strings \"%s\" & \"%s\" gives a similarity score of %.4f";
		message = String.format(message, metric.getClass().getSimpleName(),str1, str2, result);
		System.out.println(message);

	}

	private static void checkInput(final String[] args) {
		if (args.length != 2) {
			System.out.println("Performs a rudimentary string metric comparison from the arguments given.");
			System.out.println(String.format("Check the source of %s for more details.", SimpleExample.class.getName()));

			System.out.print("Usage <string> <string>");
			System.exit(1);
		}
	}

}
