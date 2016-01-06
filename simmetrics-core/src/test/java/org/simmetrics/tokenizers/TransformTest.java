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

import static com.google.common.base.Functions.compose;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizers.Transform.FilterTransform;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import static org.simmetrics.tokenizers.Tokenizers.Filter;
import static org.simmetrics.tokenizers.Tokenizers.Transform;

@SuppressWarnings("javadoc")
public class TransformTest extends TokenizerTest {

	private final Tokenizer whitespace = Tokenizers.whitespace();
	private final Function<String, String> identity = Functions.identity();
	private final Function<String, String> identity2 = Functions.identity();
	private final Predicate<String> alwaysTrue = Predicates.alwaysTrue();

	private static Function<String, String> toUpperCase() {
		return new Function<String, String>() {

			@Override
			public String apply(String input) {
				return input.toUpperCase();
			}
		};
	}

	@Override
	protected T[] getTests() {
		return new T[] {
				new T("the mouse and cat or dog", 
						"THE", "MOUSE", "AND", "CAT", "OR", "DOG"), 
				new T("") 
		};
	}

	@Override
	protected Tokenizer getTokenizer() {
		return new Transform(whitespace, toUpperCase());
	}

	@Test
	public void shouldCreateCombinedForFilter() {
		Filter filter = new Filter(whitespace, alwaysTrue);
		Tokenizer tokenizer = Transform.createCombined(filter, identity);

		assertEquals(FilterTransform.class, tokenizer.getClass());

		FilterTransform filterTransform = (FilterTransform) tokenizer;

		assertSame(filter, filterTransform.getTokenizer());
		assertSame(identity, filterTransform.getFunction());

	}

	@Test
	public void shouldCreateCombinedForFilterTransform() {
		Filter filter = new Filter(whitespace, alwaysTrue);
		FilterTransform t = new FilterTransform(filter, identity);
		Tokenizer tokenizer = Transform.createCombined(t, identity2);

		assertEquals(FilterTransform.class, tokenizer.getClass());

		FilterTransform filterTransform = (FilterTransform) tokenizer;

		assertSame(filter, filterTransform.getTokenizer());
		assertEquals(compose(identity2, identity), filterTransform.getFunction());
	}

	@Test
	public void shouldCreateCombinedForTransform() {
		Transform t = new Transform(whitespace, identity);
		Tokenizer tokenizer = Transform.createCombined(t, identity2);

		assertEquals(Transform.class, tokenizer.getClass());

		Transform transform = (Transform) tokenizer;

		assertSame(whitespace, transform.getTokenizer());
		assertEquals(compose(identity2, identity), transform.getFunction());
	}

}
