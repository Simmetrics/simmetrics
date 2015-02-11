package org.simmetrics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.simmetrics.ListMetric;
import org.simmetrics.SetMetric;
import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
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
		StringMetric composed = new StringMetricBuilder().with(stringMetric)
				.build();
		System.out.println(composed);

		System.out.println(composed);
	}

	@Test
	public void testStringMetricWithSimplifier01() {
		StringMetric composed = new StringMetricBuilder().with(stringMetric)
				.simplify(simplifier)
				.build();
		System.out.println(composed);
		System.out.println(composed);
	}

	@Test
	public void testStringMetricWithSimplifier02() {
		StringMetric composed = new StringMetricBuilder().with(stringMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.build();
		System.out.println(composed);
		System.out.println(composed);
	}

	@Test
	public void testStringMetricWithSimplifier02WithCache() {
		StringMetric composed = new StringMetricBuilder().with(stringMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.setSimplifierCache()
				.build();
		System.out.println(composed);
		System.out.println(composed);
	}

	@Test
	public void testStringMetricWithSimplifier01WithCache() {
		StringMetric composed = new StringMetricBuilder().with(stringMetric)
				.simplify(simplifier)
				.setSimplifierCache()
				.build();
		System.out.println(composed);
		System.out.println(composed);
	}

	@Test
	public void testListMetric() {
		StringMetric composed = new StringMetricBuilder().with(listMetric)
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
		System.out.println(composed);
	}

	@Test
	public void testListMetricWithFilter() {
		StringMetric composed = new StringMetricBuilder().with(listMetric)
				.tokenize(tokenizer)
				.filter(predicate)
				.build();
		System.out.println(composed);
		System.out.println(composed);
	}

	@Test
	public void testListMetricWithSimplifier01() {
		StringMetric composed = new StringMetricBuilder().with(listMetric)
				.simplify(simplifier)
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
		System.out.println(composed);
	}

	@Test
	public void testListMetricWithSimplifier02() {
		StringMetric composed = new StringMetricBuilder().with(listMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testListMetricWithSimplifier01WithCache() {
		StringMetric composed = new StringMetricBuilder().with(listMetric)
				.simplify(simplifier)
				.setSimplifierCache()
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testListMetricWithSimplifier02WithCache() {
		StringMetric composed = new StringMetricBuilder().with(listMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.setSimplifierCache()
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testListMetric02() {
		StringMetric composed = new StringMetricBuilder().with(listMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testListMetric01WithCache() {
		StringMetric composed = new StringMetricBuilder().with(listMetric)
				.tokenize(tokenizer)
				.setTokenizerCache()
				.build();
		System.out.println(composed);
	}

	@Test
	public void testListMetric02WiCache() {
		StringMetric composed = new StringMetricBuilder().with(listMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.setTokenizerCache()
				.build();
		System.out.println(composed);
	}

	@Test
	public void testSetMetric() {
		StringMetric composed = new StringMetricBuilder().with(setMetric)
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testSetMetricWithFilter() {
		StringMetric composed = new StringMetricBuilder().with(setMetric)
				.tokenize(tokenizer)
				.filter(predicate)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testSetMetricWithSimplifier01() {
		StringMetric composed = new StringMetricBuilder().with(setMetric)
				.simplify(simplifier)
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testSetMetricWithSimplifier02() {
		StringMetric composed = new StringMetricBuilder().with(setMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testSetMetricWithSimplifier01WithCache() {
		StringMetric composed = new StringMetricBuilder().with(setMetric)
				.simplify(simplifier)
				.setSimplifierCache()
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testSetMetricWithSimplifier02WithCache() {
		StringMetric composed = new StringMetricBuilder().with(setMetric)
				.simplify(simplifier)
				.simplify(simplifier)
				.setSimplifierCache()
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testSetMetric02() {
		StringMetric composed = new StringMetricBuilder().with(setMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.build();
		System.out.println(composed);
	}

	@Test
	public void testSetMetric01WithCache() {
		StringMetric composed = new StringMetricBuilder().with(setMetric)
				.tokenize(tokenizer)
				.setTokenizerCache()
				.build();
		System.out.println(composed);
	}

	@Test
	public void testSetMetric02WiCache() {
		StringMetric composed = new StringMetricBuilder().with(setMetric)
				.tokenize(tokenizer)
				.tokenize(tokenizer)
				.setTokenizerCache()
				.build();
		System.out.println(composed);
	}
}
