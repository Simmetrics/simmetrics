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

package org.simmetrics;

import java.util.Set;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizerTest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Mockito.*;

@SuppressWarnings("javadoc")
public class CachingSetTokenizerTest extends TokenizerTest {

	private Tokenizer innerTokenizer;
	private Cache<String, Set<String>> cache;
	
	@Override
	protected final boolean supportsTokenizeToList() {
		return false;
	}

	@Override
	protected final Tokenizer getTokenizer() {
		
		innerTokenizer = mock(Tokenizer.class);
	
		when(innerTokenizer.tokenizeToSet("ABC")).thenReturn(newHashSet("ABC"));
		when(innerTokenizer.tokenizeToSet("CCC")).thenReturn(newHashSet("CCC"));
		when(innerTokenizer.tokenizeToSet("EEE")).thenReturn(newHashSet("EEE"));
		when(innerTokenizer.tokenizeToSet("")).thenReturn(newHashSet(""));
	
		cache = CacheBuilder.newBuilder().initialCapacity(2).maximumSize(2).build();

		
		return new StringMetricBuilder.CachingSetTokenizer(cache,innerTokenizer);
	}

	@Override
	protected final T[] getTests() {

		return new T[] { new T("ABC", "ABC")
				, new T("CCC", "CCC"),
				new T("ABC", "ABC"), 
				new T("EEE", "EEE"), 
				new T("ABC", "ABC"),
				new T("CCC", "CCC"),
				new T("","") 

		};
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
