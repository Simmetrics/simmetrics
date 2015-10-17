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

import static org.simmetrics.StringMetricBuilder.with;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.simmetrics.ListMetric;
import org.simmetrics.SetMetric;
import org.simmetrics.StringMetric;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.utils.SimplifyingSimplifier;
import org.simmetrics.utils.TokenizingTokenizer;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;

@SuppressWarnings({"javadoc","deprecation"})
public class StringMetricBuilderTest {

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	private StringMetric stringMetric;
	@Mock
	private ListMetric<String> listMetric;
	@Mock
	private SetMetric<String> setMetric;
	@Mock
	private Simplifier simplifier;
	@Mock
	private Tokenizer tokenizer;

	@Mock
	private Predicate<String> predicate;
	@Mock
	private Function<String,String> function;
	
	@Mock
	private Cache<String, List<String>> listCache;
	@Mock
	private Cache<String, Set<String>> setCache;
	
	@Mock
	private SimplifyingSimplifier simplifyingSimplifier;

	@Mock
	private TokenizingTokenizer tokenizingTokenizer;
	
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
	public void testStringMetricWithSimplifier01WithCache() {
		with(stringMetric)
				.simplify(simplifier)
				.simplifierCache()
				.build();
	}
	@Test
	public void testStringMetricWithSimplifier01WithSimplifyingSimplifier() {
		with(stringMetric)
				.simplify(simplifier)
				.simplifierCache(simplifyingSimplifier)
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
	public void testStringMetricWithSimplifier02WithCache() {
		with(stringMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.simplifierCache()
				.build();
	}

	@Test
	public void testStringMetricWithSimplifier02WithSimplifyingSimplifier() {
		with(stringMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.simplifierCache(simplifyingSimplifier)
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
				.simplifierCache()
				.tokenize(tokenizer)
				.build();
	}
	@Test
	public void testListMetricWithSimplifier01WithSimplifyingSimplifier() {
		with(listMetric)
				.simplify(simplifier)
				.simplifierCache(simplifyingSimplifier)
				.tokenize(tokenizer)
				.build();
	}
	@Test
	public void testListMetricWithSimplifier02WithCache() {
		with(listMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.simplifierCache()
				.tokenize(tokenizer)
				.build();
	}
	@Test
	public void testListMetricWithSimplifier02WithSimplifyingSimplifier() {
		with(listMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.simplifierCache(simplifyingSimplifier)
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
				.tokenizerCache()
				.build();
	}
	@Test
	public void testListMetric01WithExternalCache() {
		with(listMetric)
				.tokenize(tokenizer)
				.cacheTokens(listCache)
				.build();
	}
	@Test
	public void testListMetric01WithTokenizingTokenizer() {
		with(listMetric)
				.tokenize(tokenizer)
				.tokenizerCache(tokenizingTokenizer)
				.build();
	}
	
	@Test
	public void testListMetric02WithCache() {
		with(listMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.tokenizerCache()
				.build();
	}
	@Test
	public void testListMetric02WithExternalCache() {
		with(listMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.cacheTokens(listCache)
				.build();
	}
	
	@Test	
	public void testListMetric02WithTokenizingTokenizer() {
		with(listMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.tokenizerCache(tokenizingTokenizer)
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
				.simplifierCache()
				.tokenize(tokenizer)
				.build();
	}

	@Test
	public void testSetMetricWithSimplifier01WithSimplifyingSimplifier() {
		with(setMetric)
				.simplify(simplifier)
				.simplifierCache(simplifyingSimplifier)
				.tokenize(tokenizer)
				.build();
	}
	@Test
	public void testSetMetricWithSimplifier02WithCache() {
		with(setMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.simplifierCache()
				.tokenize(tokenizer)
				.build();
	}
	@Test
	public void testSetMetricWithSimplifier02WithCacheSimplifyingSimplifier() {
		with(setMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.simplifierCache(simplifyingSimplifier)
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
				.tokenizerCache()
				.build();
	}
	@Test
	public void testSetMetric01WithExternalCache() {
		with(setMetric)
				.tokenize(tokenizer)
				.cacheTokens(setCache)
				.build();
	}
	@Test
	public void testSetMetric01WithTokenizingTokenizer() {
		with(setMetric)
				.tokenize(tokenizer)
				.tokenizerCache(tokenizingTokenizer)
				.build();
	}
	@Test
	public void testSetMetric02WithCache() {
		with(setMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.tokenizerCache()
				.build();
	}
	
	@Test
	public void testSetMetric02WithExternalCache() {
		with(setMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.cacheTokens(setCache)
				.build();
	}
	@Test
	public void testSetMetric02WithTokenizingTokenizer() {
		with(setMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.tokenizerCache(tokenizingTokenizer)
				.build();
	}
	
}
