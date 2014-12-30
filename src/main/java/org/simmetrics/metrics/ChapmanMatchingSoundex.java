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

import org.simmetrics.tokenisers.Tokenizer;

/**
 * Implements the Chapman Matching Soundex algorithm whereby terms are matched
 * and tested against the standard soundex algorithm - this is intended to
 * provide a better rating for lists of proper names.
 * 
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public  class ChapmanMatchingSoundex extends MongeElkan {
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
	public ChapmanMatchingSoundex(final Tokenizer tokenizer) {
		super(tokenizer, new Soundex());
	}


}
