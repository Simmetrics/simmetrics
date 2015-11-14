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

import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizerTest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;

@SuppressWarnings("javadoc")
public class CachingMultisetTokenizerTest extends TokenizerTest {

	private Tokenizer innerTokenizer;
	private Cache<String, Multiset<String>> cache;
	
	@Override
	protected final boolean supportsTokenizeToList() {
		return false;
	}
	
	@Override
	protected boolean supportsTokenizeToSet() {
		return false;
	}

	@Override
	protected final Tokenizer getTokenizer() {
		
		innerTokenizer = mock(Tokenizer.class);
	
		when(innerTokenizer.tokenizeToMultiset("ABC")).thenReturn(ImmutableMultiset.of("ABC"));
		when(innerTokenizer.tokenizeToMultiset("CCC")).thenReturn(ImmutableMultiset.of("CCC"));
		when(innerTokenizer.tokenizeToMultiset("EEE")).thenReturn(ImmutableMultiset.of("EEE"));
		when(innerTokenizer.tokenizeToMultiset("")).thenReturn(ImmutableMultiset.of(""));
	
		cache = CacheBuilder.newBuilder().initialCapacity(2).maximumSize(2).build();

		
		return new StringMetricBuilder.CachingMultisetTokenizer(cache,innerTokenizer);
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
	public final void tokenizeToMultisetShouldUseCache() {
		for (T t : tests) {
			tokenizer.tokenizeToMultiset(t.string());
		}

		 verify(innerTokenizer, times(1)).tokenizeToMultiset("ABC");
		 verify(innerTokenizer, times(2)).tokenizeToMultiset("CCC");
	}
	
}
