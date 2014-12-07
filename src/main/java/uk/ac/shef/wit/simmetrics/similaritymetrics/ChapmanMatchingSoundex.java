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

import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser;

/**
 * Implements the Chapman Matching Soundex algorithm whereby terms are matched
 * and tested against the standard soundex algorithm - this is intended to
 * provide a better rating for lists of proper names.
 * 
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class ChapmanMatchingSoundex extends MongeElkan {
	private final float ESTIMATEDTIMINGCONST = 0.026571428571428571428571428571429f;

	/**
	 * Constructs a ChapmanMatchingSoundex metric with a {@link Soundex} metric.
	 */
	public ChapmanMatchingSoundex() {
		super(new Soundex());
	}

	/**
	 * Constructs a ChapmanMatchingSoundex metric with a {@link Soundex} metric
	 * and custom tokenizer.
	 *
	 * @param tokenizer
	 *            the tokenizer to use
	 */
	public ChapmanMatchingSoundex(final InterfaceTokeniser tokenizer) {
		super(tokenizer, new Soundex());
	}

	public String getLongDescriptionString() {
		return "Implements the Chapman Matching Soundex algorithm whereby terms are matched and tested against the standard soundex algorithm - this is intended to provide a better rating for lists of proper names.";
	}
	@Deprecated

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {

		final float str1Tokens = tokeniser.tokenizeToArrayList(string1).size();
		final float str2Tokens = tokeniser.tokenizeToArrayList(string2).size();
		return (tokeniser.tokenizeToArrayList(string1).size() + tokeniser
				.tokenizeToArrayList(string2).size())
				* ((str1Tokens + str2Tokens) * ESTIMATEDTIMINGCONST);
	}
}
