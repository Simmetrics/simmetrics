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
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserWhitespace;

import java.util.ArrayList;

/**
 * Implements the Monge Elkan algorithm providing an matching style similarity
 * measure between two strings
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class MongeElkan extends AbstractStringMetric   {

	private final float ESTIMATEDTIMINGCONST = 0.0344f;

	final InterfaceTokeniser tokeniser;

	private final AbstractStringMetric internalStringMetric;

	/**
	 * Constructs a MongeElkan metric with a {@link TokeniserWhitespace} and
	 * {@link SmithWatermanGotoh}.
	 */
	public MongeElkan() {
		this(new TokeniserWhitespace(), new SmithWatermanGotoh());
	}

	/**
	 * Constructs a MongeElkan metric with the given tokenizer and
	 * {@link SmithWatermanGotoh} metric.
	 *
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public MongeElkan(final InterfaceTokeniser tokenizer) {
		this(tokenizer, new SmithWatermanGotoh());
	}

	/**
	 * Constructs a MongeElkan metric with the given tokenizer and metric.
	 *
	 * @param tokenizer
	 *            tokenizer to use
	 * @param metric
	 *            metric to use
	 */
	public MongeElkan(final InterfaceTokeniser tokenizer,
			final AbstractStringMetric metric) {
		this.tokeniser = tokenizer;
		this.internalStringMetric = metric;
	}

	/**
	 * Constructs a MongeElkan metric with a {@link TokeniserWhitespace} and
	 * given metric.
	 * 
	 * @param metric
	 *            metric to use
	 */
	public MongeElkan(final AbstractStringMetric metric) {
		this(new TokeniserWhitespace(), metric);
	}
	@Deprecated

	public String getLongDescriptionString() {
		return "Implements the Monge Elkan algorithm providing an matching style similarity measure between two strings";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {
		final float str1Tokens = tokeniser.tokenizeToArrayList(string1).size();
		final float str2Tokens = tokeniser.tokenizeToArrayList(string2).size();
		return (((str1Tokens + str2Tokens) * str1Tokens) + ((str1Tokens + str2Tokens) * str2Tokens))
				* ESTIMATEDTIMINGCONST;
	}

	public final float getSimilarity(final String string1, final String string2) {
		// split the strings into tokens for comparison
		final ArrayList<String> str1Tokens = tokeniser
				.tokenizeToArrayList(string1);
		final ArrayList<String> str2Tokens = tokeniser
				.tokenizeToArrayList(string2);

		float sumMatches = 0.0f;
		float maxFound;
		for (Object str1Token : str1Tokens) {
			maxFound = 0.0f;
			for (Object str2Token : str2Tokens) {
				final float found = internalStringMetric.getSimilarity(
						(String) str1Token, (String) str2Token);
				if (found > maxFound) {
					maxFound = found;
				}
			}
			sumMatches += maxFound;
		}
		return sumMatches / (float) str1Tokens.size();
	}

}
