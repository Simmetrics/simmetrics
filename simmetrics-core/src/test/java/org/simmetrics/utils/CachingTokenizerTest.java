/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.simmetrics.utils;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizerTest;
import org.simmetrics.utils.CachingTokenizer;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Mockito.*;

@SuppressWarnings("javadoc")
@Deprecated
public class CachingTokenizerTest extends TokenizerTest {

	private Tokenizer innerTokenizer;

	@Override
	protected final Tokenizer getTokenizer() {
		
		innerTokenizer = mock(Tokenizer.class);
		
		when(innerTokenizer.tokenizeToList("ABC")).thenReturn(newArrayList("ABC"));
		when(innerTokenizer.tokenizeToList("CCC")).thenReturn(newArrayList("CCC"));
		when(innerTokenizer.tokenizeToList("EEE")).thenReturn(newArrayList("EEE"));
		when(innerTokenizer.tokenizeToList("")).thenReturn(newArrayList(""));

		when(innerTokenizer.tokenizeToSet("ABC")).thenReturn(newHashSet("ABC"));
		when(innerTokenizer.tokenizeToSet("CCC")).thenReturn(newHashSet("CCC"));
		when(innerTokenizer.tokenizeToSet("EEE")).thenReturn(newHashSet("EEE"));
		when(innerTokenizer.tokenizeToSet("")).thenReturn(newHashSet(""));

		
		return new CachingTokenizer(2,2,innerTokenizer);
	}

	@Override
	protected final T[] getTests() {

		return new T[] { new T("ABC", "ABC")
				, new T("CCC", "CCC"),
				new T("ABC", "ABC"), new T("EEE", "EEE"), new T("ABC", "ABC"),
				new T("CCC", "CCC"),
				new T("","") 

		};
	}
	@Test
	public final void tokenizeToListShouldUseCache() {
		for (T t : tests) {
			tokenizer.tokenizeToList(t.string());
		}
				
		 verify(innerTokenizer, times(1)).tokenizeToList("ABC");
		 verify(innerTokenizer, times(2)).tokenizeToList("CCC");
	}


	@Test
	public final void tokenizeToSetShouldUseCache() {
		for (T t : tests) {
			tokenizer.tokenizeToSet(t.string());
		}

		 verify(innerTokenizer, times(1)).tokenizeToSet("ABC");
		 verify(innerTokenizer, times(2)).tokenizeToSet("CCC");
	}
	
}
