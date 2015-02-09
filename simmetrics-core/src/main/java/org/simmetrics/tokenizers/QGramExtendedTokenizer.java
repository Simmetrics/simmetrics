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
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.tokenizers;

import java.util.ArrayList;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Basic Q-Gram tokenizer for a variable Q.The Q-Gram is extended beyond the
 * length of the string with padding.
 * 
 * @author mpkorstanje
 *
 */
public class QGramExtendedTokenizer extends AbstractTokenizer {

	public static final QGramExtendedTokenizer Q2 = new QGramExtendedTokenizer(2);
	public static final QGramExtendedTokenizer Q3 = new QGramExtendedTokenizer(3);

	private final static String DEFAULT_START_PADDING = "#";
	private final static String DEFAULT_END_PADDING = "#";

	private final String endPadding;
	private final String startPadding;
	
	private final QGramTokenizer tokenizer;

	public QGramExtendedTokenizer(int q, String startPadding, String endPadding) {
		Preconditions.checkArgument(!startPadding.isEmpty(),
				"startPadding may not be empty");
		Preconditions.checkArgument(!endPadding.isEmpty(),
				"endPadding may not be empty");

		this.tokenizer = new QGramTokenizer(q);
		this.startPadding = Strings.repeat(startPadding, q - 1);
		this.endPadding = Strings.repeat(endPadding, q - 1);
	}
	
	public String getStartPadding() {
		return startPadding;
	}
	
	public String getEndPadding() {
		return endPadding;
	}

	public QGramExtendedTokenizer(int q) {
		this(q, DEFAULT_START_PADDING, DEFAULT_END_PADDING);
	}

	@Override
	public ArrayList<String> tokenizeToList(String input) {
		return tokenizer.tokenizeToList(startPadding + input + endPadding);
	}

	@Override
	public String toString() {
		return "QGramExtendedTokenizer [endPadding=" + endPadding
				+ ", startPadding=" + startPadding + ", q=" + tokenizer.getQ() + "]";
	}

}
