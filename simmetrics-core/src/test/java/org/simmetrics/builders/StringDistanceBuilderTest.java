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

import static org.simmetrics.builders.StringDistanceBuilder.with;

import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.simmetrics.ListDistance;
import org.simmetrics.MultisetDistance;
import org.simmetrics.SetDistance;
import org.simmetrics.StringDistance;
import org.simmetrics.builders.StringDistanceBuilder.CachingSimplifier;
import org.simmetrics.builders.StringDistanceBuilder.CachingListTokenizer;
import org.simmetrics.builders.StringDistanceBuilder.CachingMultisetTokenizer;
import org.simmetrics.builders.StringDistanceBuilder.CachingSetTokenizer;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.collect.Multiset;

@SuppressWarnings({"javadoc"})
@RunWith(Enclosed.class)
public class StringDistanceBuilderTest {
	
	public static class StringDistanceBuilderChainTest {
	
		@Rule
		public final MockitoRule mockitoRule = MockitoJUnit.rule();
	
		@Mock
		private StringDistance stringDistance;
		@Mock
		private ListDistance<String> listDistance;
		@Mock
		private SetDistance<String> setDistance;
		@Mock
		private MultisetDistance<String> multisetDistance;	
		@Mock
		private Simplifier simplifier;
		@Mock
		private Tokenizer tokenizer;
	
		@Mock
		private Predicate<String> predicate;
		@Mock
		private Function<String,String> function;
		@Mock
		private Cache<String, String> stringCache;
		@Mock
		private Cache<String, List<String>> listCache;
		@Mock
		private Cache<String, Set<String>> setCache;
		@Mock
		private Cache<String, Multiset<String>> multisetCache;
		
		@Test
		public void testStringDistance01() {
			with(stringDistance)
					.build();
		}
	
		@Test
		public void testStringDistanceWithSimplifier01() {
			with(stringDistance)
					.simplify(simplifier)
					.build();
		}
		
		@Test
		public void testStringDistanceWithSimplifier02() {
			with(stringDistance)
					.simplify(simplifier)
					.simplify(simplifier)
					.build();
		}
		@Test
		public void testStringDistanceWithSimplifier01WithCache() {
			with(stringDistance)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.build();
		}
		@Test
		public void testStringDistanceWithSimplifer02WithCache() {
			with(stringDistance)
					.simplify(simplifier)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.build();
		}
		
		@Test
		public void testListDistance() {
			with(listDistance)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testListDistanceWithFilter() {
			with(listDistance)
					.tokenize(tokenizer)
					.filter(predicate)
					.build();
		}
		@Test
		public void testListDistanceWithTransform() {
			with(listDistance)
					.tokenize(tokenizer)
					.transform(function)
					.build();
		}
		
		
		@Test
		public void testListDistanceWithFilterAndTransform01() {
			with(listDistance)
					.tokenize(tokenizer)
					.filter(predicate)
					.transform(function)
					.build();
		}
	
		@Test
		public void testListDistanceWithFilterAndTransform02() {
			with(listDistance)
					.tokenize(tokenizer)
					.transform(function)
					.filter(predicate)
					.build();
		}
	
		@Test
		public void testListDistanceWithSimplifier01() {
			with(listDistance)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testListDistanceWithSimplifier02() {
			with(listDistance)
					.simplify(simplifier)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
		
		@Test
		public void testListDistanceWithSimplifier01WithCache() {
			with(listDistance)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testListDistanceWithSimplifier02WithCache() {
			with(listDistance)
					.simplify(simplifier)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
		
		@Test
		public void testListDistance02() {
			with(listDistance)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.build();
		}
		
		@Test
		public void testListDistance01WithCache() {
			with(listDistance)
					.tokenize(tokenizer)
					.cacheTokens(listCache)
					.build();
		}
		
		@Test
		public void testListDistance02WithCache() {
			with(listDistance)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.cacheTokens(listCache)
					.build();
		}
		
		public void testSetDistance() {
			with(setDistance)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetDistanceWithFilter() {
			with(setDistance)
					.tokenize(tokenizer)
					.filter(predicate)
					.build();
		}
		
		@Test
		public void testSetDistanceWithTransform() {
			with(setDistance)
					.tokenize(tokenizer)
					.transform(function)
					.build();
		}
		
		
		@Test
		public void testSetDistanceWithFilterAndTransform01() {
			with(setDistance)
					.tokenize(tokenizer)
					.filter(predicate)
					.transform(function)
					.build();
		}
	
		@Test
		public void testSetDistanceWithFilterAndTransform02() {
			with(setDistance)
					.tokenize(tokenizer)
					.transform(function)
					.filter(predicate)
					.build();
		}
	
		@Test
		public void testSetDistanceWithSimplifier01() {
			with(setDistance)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetDistanceWithSimplifier02() {
			with(setDistance)
					.simplify(simplifier)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetDistanceWithSimplifier01WithCache() {
			with(setDistance)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetDistanceWithSimplifier02WithCache() {
			with(setDistance)
					.simplify(simplifier)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
		@Test
		public void testSetDistance02() {
			with(setDistance)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetDistance01WithCache() {
			with(setDistance)
					.tokenize(tokenizer)
					.cacheTokens(setCache)
					.build();
		}
		
		@Test
		public void testSetDistance02WithCache() {
			with(setDistance)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.cacheTokens(setCache)
					.build();
		}
		
		public void testMultisetDistance() {
			with(multisetDistance)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testMultisetDistanceWithFilter() {
			with(multisetDistance)
					.tokenize(tokenizer)
					.filter(predicate)
					.build();
		}
		
		@Test
		public void testMultisetDistanceWithTransform() {
			with(multisetDistance)
					.tokenize(tokenizer)
					.transform(function)
					.build();
		}
		
		
		@Test
		public void testMultisetDistanceWithFilterAndTransform01() {
			with(multisetDistance)
					.tokenize(tokenizer)
					.filter(predicate)
					.transform(function)
					.build();
		}
	
		@Test
		public void testMultisetDistanceWithFilterAndTransform02() {
			with(multisetDistance)
					.tokenize(tokenizer)
					.transform(function)
					.filter(predicate)
					.build();
		}
	
		@Test
		public void testMultisetDistanceWithSimplifier01() {
			with(multisetDistance)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testMultisetDistanceWithSimplifier02() {
			with(multisetDistance)
					.simplify(simplifier)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
		@Test
		public void testMultisetDistanceWithSimplifier01WithCache() {
			with(multisetDistance)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testMultisetDistanceWithSimplifier02WithCache() {
			with(multisetDistance)
					.simplify(simplifier)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
		@Test
		public void testMultisetDistance02() {
			with(multisetDistance)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testMultisetDistance01WithCache() {
			with(multisetDistance)
					.tokenize(tokenizer)
					.cacheTokens(multisetCache)
					.build();
		}
		
		@Test
		public void testMultisetDistance02WithCache() {
			with(multisetDistance)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.cacheTokens(multisetCache)
					.build();
		}
	}
	
	public static class CachingListTokenizerTest extends CachingTokenizerTest<List<String>> {

		@Override
		protected boolean supportsTokenizeToSet() {
			return false;
		}

		@Override
		protected boolean supportsTokenizeToMultiset() {
			return false;
		}

		@Override
		public Tokenizer getTokenizer(Cache<String, List<String>> cache, Tokenizer tokenizer) {
			return new CachingListTokenizer(cache, tokenizer);
		}
	}
	
	public static class CachingMultisetTokenizerTest extends CachingTokenizerTest<Multiset<String>> {

		@Override
		protected final boolean supportsTokenizeToList() {
			return false;
		}
		
		@Override
		protected boolean supportsTokenizeToSet() {
			return false;
		}

		@Override
		public Tokenizer getTokenizer(Cache<String, Multiset<String>> cache, Tokenizer tokenizer) {
			return new CachingMultisetTokenizer(cache, tokenizer);
		}
	}
	
	public static class CachingSetTokenizerTest extends CachingTokenizerTest<Set<String>> {

		@Override
		protected final boolean supportsTokenizeToList() {
			return false;
		}
		
		@Override
		protected boolean supportsTokenizeToMultiset() {
			return false;
		}

		@Override
		public Tokenizer getTokenizer(Cache<String, Set<String>> cache, Tokenizer tokenizer) {
			return new CachingSetTokenizer(cache,tokenizer);
		}
	}
	
	public static class StringDistanceCachingSimplifierTest extends CachingSimplifierTest {

		@Override
		protected Simplifier getCachingSimplifier(Cache<String, String> cache,
				Simplifier innerSimplifier) {
			return new CachingSimplifier(cache, innerSimplifier);
		}
	}
}
