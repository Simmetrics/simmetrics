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

import static org.simmetrics.builders.StringMetricBuilder.with;

import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.simmetrics.ListMetric;
import org.simmetrics.MultisetMetric;
import org.simmetrics.SetMetric;
import org.simmetrics.StringMetric;
import org.simmetrics.builders.StringMetricBuilder.CachingSimplifier;
import org.simmetrics.builders.StringMetricBuilder.CachingListTokenizer;
import org.simmetrics.builders.StringMetricBuilder.CachingMultisetTokenizer;
import org.simmetrics.builders.StringMetricBuilder.CachingSetTokenizer;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.collect.Multiset;

@SuppressWarnings({"javadoc"})
@RunWith(Enclosed.class)
public class StringMetricBuilderTest {
	
	public static class StringMetricBuilderChainTest {

		@Rule
		public final MockitoRule mockitoRule = MockitoJUnit.rule();
	
		@Mock
		private StringMetric stringMetric;
		@Mock
		private ListMetric<String> listMetric;
		@Mock
		private SetMetric<String> setMetric;
		@Mock
		private MultisetMetric<String> multisetMetric;	
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
		public void testStringMetric01() {
			with(stringMetric)
					.build();
		}
	
		@Test
		public void testStringMetricWithSimplifier01() {
			with(stringMetric)
					.simplify(simplifier)
					.build();
		}
		
		@Test
		public void testStringMetricWithSimplifier02() {
			with(stringMetric)
					.simplify(simplifier)
					.simplify(simplifier)
					.build();
		}
		@Test
		public void testStringMetricWithSimplifier01WithCache() {
			with(stringMetric)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.build();
		}
		@Test
		public void testStringMetricWithSimplifer02WithCache() {
			with(stringMetric)
					.simplify(simplifier)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.build();
		}
		
		@Test
		public void testListMetric() {
			with(listMetric)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testListMetricWithFilter() {
			with(listMetric)
					.tokenize(tokenizer)
					.filter(predicate)
					.build();
		}
		@Test
		public void testListMetricWithTransform() {
			with(listMetric)
					.tokenize(tokenizer)
					.transform(function)
					.build();
		}
		
		
		@Test
		public void testListMetricWithFilterAndTransform01() {
			with(listMetric)
					.tokenize(tokenizer)
					.filter(predicate)
					.transform(function)
					.build();
		}
	
		@Test
		public void testListMetricWithFilterAndTransform02() {
			with(listMetric)
					.tokenize(tokenizer)
					.transform(function)
					.filter(predicate)
					.build();
		}
	
		@Test
		public void testListMetricWithSimplifier01() {
			with(listMetric)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testListMetricWithSimplifier02() {
			with(listMetric)
					.simplify(simplifier)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
		
		@Test
		public void testListMetricWithSimplifier01WithCache() {
			with(listMetric)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testListMetricWithSimplifier02WithCache() {
			with(listMetric)
					.simplify(simplifier)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
		
		@Test
		public void testListMetric02() {
			with(listMetric)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.build();
		}
		
		@Test
		public void testListMetric01WithCache() {
			with(listMetric)
					.tokenize(tokenizer)
					.cacheTokens(listCache)
					.build();
		}
		
		@Test
		public void testListMetric02WithCache() {
			with(listMetric)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.cacheTokens(listCache)
					.build();
		}
		
		public void testSetMetric() {
			with(setMetric)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetMetricWithFilter() {
			with(setMetric)
					.tokenize(tokenizer)
					.filter(predicate)
					.build();
		}
		
		@Test
		public void testSetMetricWithTransform() {
			with(setMetric)
					.tokenize(tokenizer)
					.transform(function)
					.build();
		}
		
		
		@Test
		public void testSetMetricWithFilterAndTransform01() {
			with(setMetric)
					.tokenize(tokenizer)
					.filter(predicate)
					.transform(function)
					.build();
		}
	
		@Test
		public void testSetMetricWithFilterAndTransform02() {
			with(setMetric)
					.tokenize(tokenizer)
					.transform(function)
					.filter(predicate)
					.build();
		}
	
		@Test
		public void testSetMetricWithSimplifier01() {
			with(setMetric)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetMetricWithSimplifier02() {
			with(setMetric)
					.simplify(simplifier)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetMetricWithSimplifier01WithCache() {
			with(setMetric)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetMetricWithSimplifier02WithCache() {
			with(setMetric)
					.simplify(simplifier)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
		@Test
		public void testSetMetric02() {
			with(setMetric)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testSetMetric01WithCache() {
			with(setMetric)
					.tokenize(tokenizer)
					.cacheTokens(setCache)
					.build();
		}
		
		@Test
		public void testSetMetric02WithCache() {
			with(setMetric)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.cacheTokens(setCache)
					.build();
		}
		
		public void testMultisetMetric() {
			with(multisetMetric)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testMultisetMetricWithFilter() {
			with(multisetMetric)
					.tokenize(tokenizer)
					.filter(predicate)
					.build();
		}
		
		@Test
		public void testMultisetMetricWithTransform() {
			with(multisetMetric)
					.tokenize(tokenizer)
					.transform(function)
					.build();
		}
		
		
		@Test
		public void testMultisetMetricWithFilterAndTransform01() {
			with(multisetMetric)
					.tokenize(tokenizer)
					.filter(predicate)
					.transform(function)
					.build();
		}
	
		@Test
		public void testMultisetMetricWithFilterAndTransform02() {
			with(multisetMetric)
					.tokenize(tokenizer)
					.transform(function)
					.filter(predicate)
					.build();
		}
	
		@Test
		public void testMultisetMetricWithSimplifier01() {
			with(multisetMetric)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testMultisetMetricWithSimplifier02() {
			with(multisetMetric)
					.simplify(simplifier)
					.simplify(simplifier)
					.tokenize(tokenizer)
					.build();
		}
		@Test
		public void testMultisetMetricWithSimplifier01WithCache() {
			with(multisetMetric)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testMultisetMetricWithSimplifier02WithCache() {
			with(multisetMetric)
					.simplify(simplifier)
					.simplify(simplifier)
					.cacheStrings(stringCache)
					.tokenize(tokenizer)
					.build();
		}
		@Test
		public void testMultisetMetric02() {
			with(multisetMetric)
					.tokenize(tokenizer)
					.tokenize(tokenizer)
					.build();
		}
	
		@Test
		public void testMultisetMetric01WithCache() {
			with(multisetMetric)
					.tokenize(tokenizer)
					.cacheTokens(multisetCache)
					.build();
		}
		
		@Test
		public void testMultisetMetric02WithCache() {
			with(multisetMetric)
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
