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

import java.util.HashSet;
import java.util.Set;

/**
 * Implements the Overlap Coefficient algorithm providing a similarity measure
 * between two string where it is determined to what degree a string is a subset
 * of another.
 * 
 * overlap_coefficient(q,r) = (|q & r|) / min{|q|, |r|}.
 * 
 * @author Sam Chapman * @version 1.1
 */
public final class OverlapCoefficient extends AbstractStringMetric 
		 {

	private final float ESTIMATEDTIMINGCONST = 1.4e-4f;

	private final InterfaceTokeniser tokenizer;

	/**
	 * Constructs a OverlapCoefficient metric with a {@link TokeniserWhitespace}.
	 */
	public OverlapCoefficient() {
		this.tokenizer = new TokeniserWhitespace();
	}

	/**
	 * Constructs a OverlapCoefficient metric with the given tokenizer.
	 *
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public OverlapCoefficient(final InterfaceTokeniser tokenizer) {
		this.tokenizer = tokenizer;
	}
	@Deprecated
	public String getLongDescriptionString() {
		return "Implements the Overlap Coefficient algorithm providing a similarity measure between two string where it is determined to what degree a string is a subset of another";
	}

	public float getSimilarityTimingEstimated(final String string1,
			final String string2) {
		final float str1Tokens = tokenizer.tokenizeToArrayList(string1).size();
		final float str2Tokens = tokenizer.tokenizeToArrayList(string2).size();
		return (str1Tokens * str2Tokens) * ESTIMATEDTIMINGCONST;
	}

	public float getSimilarity(final String string1, final String string2) {
		final Set<String> str1Tokens = tokenizer.tokenizeToSet(string1);
		final Set<String> str2Tokens = tokenizer.tokenizeToSet(string2);

		final Set<String> allTokens = new HashSet<String>();
		allTokens.addAll(str1Tokens);
		allTokens.addAll(str2Tokens);

		// overlap_coefficient(q,r) = ( | q & r | ) / min{ | q | , | r | }.
		final int commonTerms = (str1Tokens.size() + str2Tokens.size())
				- allTokens.size();
		return (float) (commonTerms)
				/ (float) Math.min(str1Tokens.size(), str2Tokens.size());
	}

}
