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
package org.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.Set;

import com.google.common.base.Strings;

/**
 * Basic Q-Gram tokenizer for a variable Q.The Q-Gram is extended beyond the
 * length of the string with padding.
 * 
 * @author mpkorstanje
 *
 */
public class QGramExtendedTokenizer extends QGramTokenizer {

	private final String Q_GRAM_START_PADDING = "#";
	private final String Q_GRAM_END_PADDING = "#";

	private final String endPadding;
	private final String startPadding;

	public QGramExtendedTokenizer(int q) {
		super(q);
		
		this.startPadding = Strings.repeat(Q_GRAM_START_PADDING, q - 1);
		this.endPadding = Strings.repeat(Q_GRAM_END_PADDING, q - 1);

	}

	public ArrayList<String> tokenizeToList(String input) {
		return super.tokenizeToList(startPadding + input + endPadding);
	}

	@Override
	public String toString() {
		return "QGramExtendedTokenizer [q=" + getQ() + "]";
	}

}
