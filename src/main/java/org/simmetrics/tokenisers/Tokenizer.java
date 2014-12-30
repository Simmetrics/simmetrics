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

import java.util.Set;
import java.util.ArrayList;

/**
 * A tokenizer divides an input string into tokens. 
 * 
 * @author Sam Chapman
 * @version 1.1
 */
public interface Tokenizer {

	/**
	 * Return tokenized version of a string as a list of tokens. This list can
	 * be safely modified.
	 *
	 * @param input
	 *
	 * @return ArrayList tokenized version of a string
	 */
	public ArrayList<String> tokenizeToList(String input);

	/**
	 * Return tokenized version of a string as a set of tokens. This set can be
	 * safely modified.
	 *
	 * @param input
	 *
	 * @return tokenized version of a string as a set
	 */
	public Set<String> tokenizeToSet(String input);
}
