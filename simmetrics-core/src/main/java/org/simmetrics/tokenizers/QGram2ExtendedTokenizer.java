/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */

package org.simmetrics.tokenizers;

/**
 * Basic Q-Gram tokenizer for a Q of 2.The Q-Gram is extended beyond the length
 * of the string with padding.
 * 
 * @author mpkorstanje
 */
public final class QGram2ExtendedTokenizer extends QGramExtendedTokenizer {

	private static final int Q = 2;

	public QGram2ExtendedTokenizer() {
		super(Q);
	}

}
