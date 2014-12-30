/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package org.simmetrics.metrics;

import java.util.ArrayList;

import org.simmetrics.tokenisers.Tokenizer;
import org.simmetrics.tokenisers.WhitespaceTokenizer;

/**
 * Implements the Monge Elkan algorithm providing an matching style similarity
 * measure between two strings
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public class MongeElkan extends TokenizingStringMetric {

	private final SimplyfingStringMetric metric;

	/**
	 * Constructs a MongeElkan metric with a {@link WhitespaceTokenizer} and
	 * {@link SmithWatermanGotoh}.
	 */
	public MongeElkan() {
		this(new WhitespaceTokenizer(), new SmithWatermanGotoh());
	}

	/**
	 * Constructs a MongeElkan metric with the given tokenizer and
	 * {@link SmithWatermanGotoh} metric.
	 *
	 * @param tokenizer
	 *            tokenizer to use
	 */
	public MongeElkan(final Tokenizer tokenizer) {
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
	public MongeElkan(final Tokenizer tokenizer,
			final SimplyfingStringMetric metric) {
		super(tokenizer);
		this.metric = metric;
	}

	/**
	 * Constructs a MongeElkan metric with a {@link WhitespaceTokenizer} and
	 * given metric.
	 * 
	 * @param metric
	 *            metric to use
	 */
	public MongeElkan(final SimplyfingStringMetric metric) {
		this(new WhitespaceTokenizer(), metric);
	}


	protected float compareSimplified(final String string1, final String string2) {
		// split the strings into tokens for comparison
		final ArrayList<String> str1Tokens = tokenizeToList(string1);
		final ArrayList<String> str2Tokens = tokenizeToList(string2);

		float sumMatches = 0.0f;
		float maxFound;
		for (String str1Token : str1Tokens) {
			maxFound = 0.0f;
			for (String str2Token : str2Tokens) {
				final float found = metric.compare(str1Token, str2Token);
				if (found > maxFound) {
					maxFound = found;
				}
			}
			sumMatches += maxFound;
		}
		return sumMatches / str1Tokens.size();
	}

}
