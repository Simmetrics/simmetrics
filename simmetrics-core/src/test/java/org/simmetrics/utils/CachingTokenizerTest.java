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
package org.simmetrics.utils;

import java.util.Arrays;
import java.util.Collections;

import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizerTest;
import org.simmetrics.utils.CachingTokenizer;

import static org.mockito.Mockito.*;

public class CachingTokenizerTest extends TokenizerTest {

	private Tokenizer tokenizer;

	@Override
	protected Tokenizer getTokenizer() {
		
		tokenizer = mock(Tokenizer.class);
		
		when(tokenizer.tokenizeToList("ABC")).thenReturn(Arrays.asList("ABC"));
		when(tokenizer.tokenizeToList("CCC")).thenReturn(Arrays.asList("CCC"));
		when(tokenizer.tokenizeToList("EEE")).thenReturn(Arrays.asList("EEE"));
		
		when(tokenizer.tokenizeToSet("ABC")).thenReturn(Collections.singleton("ABC"));
		when(tokenizer.tokenizeToSet("CCC")).thenReturn(Collections.singleton("CCC"));
		when(tokenizer.tokenizeToSet("EEE")).thenReturn(Collections.singleton("EEE"));

		
		return new CachingTokenizer(2,2,tokenizer);
	}

	@Override
	public T[] getTests() {

		return new T[] { new T("ABC", "ABC")
				, new T("CCC", "CCC"),
				new T("ABC", "ABC"), new T("EEE", "EEE"), new T("ABC", "ABC"),
				new T("CCC", "CCC"),

		};
	}

	@Override
	public void testTokenizeToArrayList() {
		super.testTokenizeToArrayList();
		
		 verify(tokenizer, times(1)).tokenizeToList("ABC");
		 verify(tokenizer, times(2)).tokenizeToList("CCC");

	
	}

	@Override
	public void testTokenizeToSet() {
		super.testTokenizeToSet();

		 verify(tokenizer, times(1)).tokenizeToSet("ABC");
		 verify(tokenizer, times(2)).tokenizeToSet("CCC");

	}
}
