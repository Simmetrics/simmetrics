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

import com.google.common.base.Predicate;


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
