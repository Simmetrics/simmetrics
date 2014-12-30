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
import java.util.HashSet;
import java.util.Set;

import org.simmetrics.wordhandlers.GenericStopTermHandler;
import org.simmetrics.wordhandlers.TermHandler;

import com.google.common.base.Predicate;

import static com.google.common.collect.Collections2.filter;

public class TermFilterTokenizer extends AbstractTokenizer implements TokenizingTokenizer{

	private Tokenizer tokenizer = new WhitespaceTokenizer();

	private TermHandler termHandler = new GenericStopTermHandler();

	public TermHandler getTermHandler() {
		return termHandler;
	}

	public void setTermHandler(TermHandler termHandler) {
		this.termHandler = termHandler;
	}

	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	private final Predicate<String> isWord = new Predicate<String>() {

		public boolean apply(String input) {
			return termHandler.isWord(input);
		}
	};

	public ArrayList<String> tokenizeToList(String input) {
		return new ArrayList<String>(filter(tokenizer.tokenizeToList(input),
				isWord));
	}

	@Override
	public Set<String> tokenizeToSet(String input) {
		return new HashSet<String>(filter(tokenizer.tokenizeToSet(input),
				isWord));
	}

}
