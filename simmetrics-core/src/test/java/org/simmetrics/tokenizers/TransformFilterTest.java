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

import static com.google.common.base.Predicates.in;
import static com.google.common.base.Predicates.not;
import static java.util.Arrays.asList;

import org.simmetrics.tokenizers.Tokenizers.Filter;
import org.simmetrics.tokenizers.Tokenizers.Filter.TransformFilter;
import org.simmetrics.tokenizers.Tokenizers.Transform.FilterTransform;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import static org.simmetrics.tokenizers.Tokenizers.whitespace;

@SuppressWarnings("javadoc")
public class TransformFilterTest extends TokenizerTest {

	private static Predicate<String> theAndOr() {
		return not(in(asList("THE", "AND", "OR")));
	}
	private static Predicate<String> dog() {
		return not(in(asList("dog")));
	}
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
				new T("the mouse and cat or dog", "MOUSE", "CAT"),
				new T("")
		};
	}

	@Override
	protected Tokenizer getTokenizer() {
		final Filter filter = new Filter(whitespace(), dog()); 
		final FilterTransform transform = new FilterTransform(filter,toUpperCase());
		return new TransformFilter(transform, theAndOr());
		
	}

	

}
