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
package org.simmetrics.tokenizers;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.in;
import static com.google.common.base.Predicates.not;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizers.Filter.TransformFilter;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import static org.simmetrics.tokenizers.Tokenizers.Filter;
import static org.simmetrics.tokenizers.Tokenizers.Transform;

@SuppressWarnings("javadoc")
public class FilterTest extends TokenizerTest {

	private final Tokenizer whitespace = Tokenizers.whitespace();
	private final Function<String, String> identity = Functions.identity();
	private final Predicate<String> alwaysTrue = Predicates.alwaysTrue();
	private final Predicate<String> alwaysFalse = Predicates.alwaysFalse();

	static Predicate<String> theAndOr() {
		return not(in(asList("the", "and", "or")));
	}

	@Override
	protected T[] getTests() {
		return new T[] {
				new T("the mouse and cat or dog", "mouse", "cat", "dog"),
				new T("")
		};
	}

	@Override
	protected Tokenizer getTokenizer() {
		return new Filter(whitespace, theAndOr());
	}

	@Test
	public void shouldCreateCombinedForFilter() {
		Filter f = new Filter(whitespace, alwaysTrue);
		Tokenizer tokenizer = Filter.createCombined(f, alwaysFalse);

		assertEquals(Filter.class, tokenizer.getClass());

		Filter filter = (Filter) tokenizer;

		assertSame(whitespace, filter.getTokenizer());
		assertEquals(and(alwaysTrue, alwaysFalse), filter.getPredicate());

	}

	@Test
	public void shouldCreateCombinedForTransformFilter() {
		Transform transform = new Transform(whitespace, identity);
		TransformFilter t = new TransformFilter(transform, alwaysTrue);
		Tokenizer tokenizer = Filter.createCombined(t, alwaysFalse);

		assertEquals(TransformFilter.class, tokenizer.getClass());

		TransformFilter transformFilter = (TransformFilter) tokenizer;

		assertSame(transform, transformFilter.getTokenizer());
		assertEquals(and(alwaysTrue, alwaysFalse), transformFilter.getPredicate());
	}

	@Test
	public void shouldCreateCombinedForTransform() {
		Transform transform = new Transform(whitespace, identity);
		Tokenizer tokenizer = Filter.createCombined(transform, alwaysTrue);

		assertEquals(TransformFilter.class, tokenizer.getClass());

		TransformFilter transformFilter = (TransformFilter) tokenizer;

		assertSame(transform, transformFilter.getTokenizer());
		assertSame(alwaysTrue, transformFilter.getPredicate());

	}

}
