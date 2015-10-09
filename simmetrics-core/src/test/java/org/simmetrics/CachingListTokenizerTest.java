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

import java.util.List;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizerTest;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.*;

@SuppressWarnings("javadoc")
public class CachingListTokenizerTest extends TokenizerTest {

	private Tokenizer innerTokenizer;
	private Cache<String, List<String>> cache;

	@Override
	protected final boolean supportsTokenizeToSet() {
		return false;
	}

	@Override
	protected final Tokenizer getTokenizer() {

		innerTokenizer = mock(Tokenizer.class);

		when(innerTokenizer.tokenizeToList("ABC")).thenReturn(
				newArrayList("ABC"));
		when(innerTokenizer.tokenizeToList("CCC")).thenReturn(
				newArrayList("CCC"));
		when(innerTokenizer.tokenizeToList("EEE")).thenReturn(
				newArrayList("EEE"));
		when(innerTokenizer.tokenizeToList("")).thenReturn(newArrayList(""));

		cache = CacheBuilder.newBuilder().initialCapacity(2).maximumSize(2)
				.build();

		return new StringMetricBuilder.CachingListTokenizer(cache,
				innerTokenizer);
	}

	@Override
	protected final T[] getTests() {

		return new T[] { new T("ABC", "ABC"), new T("CCC", "CCC"),
				new T("ABC", "ABC"), new T("EEE", "EEE"), new T("ABC", "ABC"),
				new T("CCC", "CCC"), new T("", "")

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
}
