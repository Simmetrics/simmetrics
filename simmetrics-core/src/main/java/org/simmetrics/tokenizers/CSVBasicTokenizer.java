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

import java.util.ArrayList;
import java.util.List;

/**
 * CSVBasicTokenizer implements a simple CSV tokenizer. NB(this doesn't consider
 * embedded escaped comma's within the fields)
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public final class CSVBasicTokenizer extends AbstractTokenizer {

	private final String delimiters = "[,\n]";

	@Override
	public List<String> tokenizeToList(final String input) {
		final List<String> returnArrayList = new ArrayList<>();

		for (String token : input.split(delimiters)) {
			returnArrayList.add(token);
		}

		return returnArrayList;
	}

	@Override
	public String toString() {
		return "CSVBasicTokenizer [delimiters=" + delimiters + "]";
	}

}
