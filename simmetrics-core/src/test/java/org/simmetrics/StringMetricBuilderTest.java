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
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics;

import static org.simmetrics.StringMetricBuilder.with;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.simmetrics.ListMetric;
import org.simmetrics.SetMetric;
import org.simmetrics.StringMetric;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.base.Function;
import com.google.common.base.Predicate;


@SuppressWarnings("javadoc")
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
	public void testStringMetricWithSimplifier02WithCache() {
		with(stringMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.simplifierCache()
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
	public void testListMetricWithSimplifier02WithCache() {
		with(listMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.simplifierCache()
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
	public void testListMetric02WiCache() {
		with(listMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.tokenizerCache()
				.build();
	}

	@Test
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
	public void testSetMetricWithSimplifier02WithCache() {
		with(setMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.simplifierCache()
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
	public void testSetMetric02WiCache() {
		with(setMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.tokenizerCache()
				.build();
	}
}
