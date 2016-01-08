/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
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
package org.simmetrics.builders;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizerTest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMultiset;

@SuppressWarnings("javadoc")
public abstract class CachingTokenizerTest<V> extends TokenizerTest {

	@Mock
	private Tokenizer innerTokenizer;
	
	@Mock
	private Cache<String, V> brokenCache;
	
	private Cache<String, V> cache = CacheBuilder.newBuilder()
			.initialCapacity(2)
			.maximumSize(2)
			.build();
	
	@Override
	protected final Tokenizer getTokenizer() {
		return getTokenizer(cache, innerTokenizer);
	}
	
	protected abstract Tokenizer getTokenizer(Cache<String,V> cache, Tokenizer tokenizer);
	
	@Before
	public void before() throws Exception{
				
		when(brokenCache.get(anyString(), any(Callable.class)))
		.thenThrow(new ExecutionException(new Exception()));

		when(innerTokenizer.tokenizeToList("ABC"))
		.thenReturn(newArrayList("ABC"));
		when(innerTokenizer.tokenizeToList("CCC"))
		.thenReturn(newArrayList("CCC"));
		when(innerTokenizer.tokenizeToList("EEE"))
		.thenReturn(newArrayList("EEE"));
		when(innerTokenizer.tokenizeToList(""))
		.thenReturn(newArrayList(""));
		
		when(innerTokenizer.tokenizeToSet("ABC"))
		.thenReturn(newHashSet("ABC"));
		when(innerTokenizer.tokenizeToSet("CCC"))
		.thenReturn(newHashSet("CCC"));
		when(innerTokenizer.tokenizeToSet("EEE"))
		.thenReturn(newHashSet("EEE"));
		when(innerTokenizer.tokenizeToSet(""))
		.thenReturn(newHashSet(""));

		when(innerTokenizer.tokenizeToMultiset("ABC"))
		.thenReturn(ImmutableMultiset.of("ABC"));
		when(innerTokenizer.tokenizeToMultiset("CCC"))
		.thenReturn(ImmutableMultiset.of("CCC"));
		when(innerTokenizer.tokenizeToMultiset("EEE"))
		.thenReturn(ImmutableMultiset.of("EEE"));
		when(innerTokenizer.tokenizeToMultiset(""))
		.thenReturn(ImmutableMultiset.of(""));
	}
	
	@Test
	public void tokenizeToSetShouldUseCache() {
		if(!supportsTokenizeToSet()){
			return;
		}
		
		for (T t : tests) {
			tokenizer.tokenizeToSet(t.string());
		}

		verify(innerTokenizer, times(1)).tokenizeToSet("ABC");
		verify(innerTokenizer, times(2)).tokenizeToSet("CCC");
	}
	
	@Test
	public void tokenizeToMultisetShouldUseCache() {
		if(!supportsTokenizeToMultiset()){
			return;
		}
		
		for (T t : tests) {
			tokenizer.tokenizeToMultiset(t.string());
		}

		verify(innerTokenizer, times(1)).tokenizeToMultiset("ABC");
		verify(innerTokenizer, times(2)).tokenizeToMultiset("CCC");
	}
	
	@Test
	public void tokenizeToListShouldUseCache() {
		if(!supportsTokenizeToList()){
			return;
		}
		
		for (T t : tests) {
			tokenizer.tokenizeToList(t.string());
		}

		verify(innerTokenizer, times(1)).tokenizeToList("ABC");
		verify(innerTokenizer, times(2)).tokenizeToList("CCC");
	}
	
	@Test
	public void tokenizeToListshouldThrowIllegalStateException() {
		if(!supportsTokenizeToList()){
			return;
		}
		
		thrown.expect(IllegalStateException.class);
		
		getTokenizer(brokenCache, innerTokenizer).tokenizeToList("Sheep");
	}
	
	@Test
	public void tokenizeToSetshouldThrowIllegalStateException() {
		if(!supportsTokenizeToSet()){
			return;
		}
		thrown.expect(IllegalStateException.class);

		getTokenizer(brokenCache, innerTokenizer).tokenizeToSet("Sheep");
	}
	
	@Test
	public void tokenizeToMultisetshouldThrowIllegalStateException() {
		if(!supportsTokenizeToMultiset()){
			return;
		}
		thrown.expect(IllegalStateException.class);

		getTokenizer(brokenCache, innerTokenizer).tokenizeToMultiset("Sheep");
	}
	
	@Override
	protected final T[] getTests() {

		return new T[] { 
				new T("ABC", "ABC"), 
				new T("CCC", "CCC"),
				new T("ABC", "ABC"), 
				new T("EEE", "EEE"), 
				new T("ABC", "ABC"),
				new T("CCC", "CCC"), 
				new T("", "")
		};
	}
	

	
}
