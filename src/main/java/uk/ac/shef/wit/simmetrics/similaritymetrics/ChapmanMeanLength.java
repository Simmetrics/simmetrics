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

package uk.ac.shef.wit.simmetrics.similaritymetrics;

import static java.lang.Math.pow;

/**
 * Implements the Chapman Mean Length algorithm provides a similarity measure
 * between two strings from size of the mean length of the vectors - this
 * approach is supposed to be used to determine which metrics may be best to
 * apply rather than giving a valid response itself
 * 
 * @author Sam Chapman
 * @version 1.2
 */

public final class ChapmanMeanLength extends AbstractStringMetric {

	/**
	 * defines the internal max string length beyond which 1.0 is always
	 * returned.
	 */
	final private static int CHAPMANMEANLENGTHMAXSTRING = 500;
	
	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Chapman Mean Length algorithm provides a similarity measure between two strings from size of the mean length of the vectors - this approach is suppossed to be used to determine which metrics may be best to apply rather than giveing a valid responce itself";
	}
	
	@Override
	public float getSimilarityTimingEstimated(String string1, String string2) {
		return 0;
	}

	public float getSimilarity(final String string1, final String string2) {
		final float bothLengths = string2.length() + string1.length();
		if (bothLengths > CHAPMANMEANLENGTHMAXSTRING) {
			return 1.0f;
		} else {
			// FIXME: Integer division? Was this intended?
			final float oneMinusBothScaled = (CHAPMANMEANLENGTHMAXSTRING - bothLengths)
					/ CHAPMANMEANLENGTHMAXSTRING;
			return (float) (1.0 - pow(oneMinusBothScaled, 4));
		}
	}

}
