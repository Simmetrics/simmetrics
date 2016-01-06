/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * #L%
 */
package org.simmetrics.tokenizers;

import static com.google.common.base.Predicates.and;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.simmetrics.tokenizers.Tokenizers.chain;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizers.Filter;
import org.simmetrics.tokenizers.Tokenizers.Filter.TransformFilter;
import org.simmetrics.tokenizers.Tokenizers.QGram;
import org.simmetrics.tokenizers.Tokenizers.QGramExtended;
import org.simmetrics.tokenizers.Tokenizers.Recursive;
import org.simmetrics.tokenizers.Tokenizers.Split;
import org.simmetrics.tokenizers.Tokenizers.Transform;
import org.simmetrics.tokenizers.Tokenizers.Transform.FilterTransform;
import org.simmetrics.tokenizers.Tokenizers.Whitespace;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

@SuppressWarnings({ "javadoc" })
public class TokenizersTest {

	private final String regex = "\\s+";
	private final Pattern pattern = Pattern.compile(regex);
	private final Tokenizer whitespace = Tokenizers.whitespace();
	private final Function<String, String> identity = Functions.identity();
	private final Function<String, String> identity2 = Functions.identity();
	private final Predicate<String> alwaysTrue = Predicates.alwaysTrue();
	private final Predicate<String> alwaysFalse = Predicates.alwaysFalse();

	@Test
	public void shouldReturnSplitForPattern() {
		Tokenizer tokenizer = Tokenizers.pattern(pattern);

		assertEquals(Split.class, tokenizer.getClass());

		Split split = (Split) tokenizer;
		assertEquals(pattern, split.getPattern());
	}

	@Test
	public void shouldReturnSplitForRegex() {
		Tokenizer tokenizer = Tokenizers.pattern(regex);

		assertEquals(Split.class, tokenizer.getClass());

		Split split = (Split) tokenizer;
		assertEquals(regex, split.getPattern().toString());
	}

	@Test
	public void shouldReturnQGram() {
		Tokenizer tokenizer = Tokenizers.qGram(3);

		assertEquals(QGram.class, tokenizer.getClass());

		QGram qGram = (QGram) tokenizer;
		assertEquals(3, qGram.getQ());
	}

	@Test
	public void shouldReturnQGramWithFilter() {
		Tokenizer tokenizer = Tokenizers.qGramWithFilter(3);

		assertEquals(QGram.class, tokenizer.getClass());

		QGram qGram = (QGram) tokenizer;
		assertEquals(3, qGram.getQ());
		assertTrue(qGram.isFilter());
	}

	@Test
	public void shouldReturnQGramWithPadding() {
		Tokenizer tokenizer = Tokenizers.qGramWithPadding(3);

		assertEquals(QGramExtended.class, tokenizer.getClass());

		QGramExtended qGram = (QGramExtended) tokenizer;
		assertEquals(3, qGram.getQ());
		assertEquals("##", qGram.getStartPadding());
		assertEquals("##", qGram.getEndPadding());
	}

	@Test
	public void shouldReturnQGramWithCustomPadding() {
		Tokenizer tokenizer = Tokenizers.qGramWithPadding(3, "@");

		assertEquals(QGramExtended.class, tokenizer.getClass());

		QGramExtended qGram = (QGramExtended) tokenizer;
		assertEquals(3, qGram.getQ());
		assertEquals("@@", qGram.getStartPadding());
		assertEquals("@@", qGram.getEndPadding());
	}

	@Test
	public void shouldReturnQGramWithCustomStartAndEndPadding() {
		Tokenizer tokenizer = Tokenizers.qGramWithPadding(3, "^", "$");

		assertEquals(QGramExtended.class, tokenizer.getClass());

		QGramExtended qGram = (QGramExtended) tokenizer;
		assertEquals(3, qGram.getQ());
		assertEquals("^^", qGram.getStartPadding());
		assertEquals("$$", qGram.getEndPadding());
	}

	@Test
	public void shouldReturnWhitespace() {
		assertEquals(Whitespace.class,
				Tokenizers.whitespace().getClass());
	}

	@Test
	public void shouldReturnTransform() {
		Tokenizer tokenizer = Tokenizers.transform(whitespace, identity);

		assertEquals(Transform.class, tokenizer.getClass());

		Transform transform = (Transform) tokenizer;
		assertSame(whitespace, transform.getTokenizer());
		assertSame(identity, transform.getFunction());
	}

	@Test
	public void shouldReturnTransformForTransform() {
		Transform t = new Transform(whitespace, identity);
		Tokenizer tokenizer = Tokenizers.transform(t, identity2);

		assertEquals(Transform.class, tokenizer.getClass());

		Transform transform = (Transform) tokenizer;
		assertSame(whitespace, transform.getTokenizer());
		assertEquals(Functions.compose(identity2, identity),
				transform.getFunction());
	}

	@Test
	public void shouldReturnTransformForFilter() {
		Tokenizers.Filter filter = new Tokenizers.Filter(whitespace, alwaysTrue);
		Tokenizer tokenizer = Tokenizers.transform(filter, identity);

		assertEquals(FilterTransform.class, tokenizer.getClass());

		FilterTransform transform = (FilterTransform) tokenizer;
		assertSame(filter, transform.getTokenizer());
		assertSame(identity, transform.getFunction());
	}
	
	@Test
	public void shouldChainSingletonList() {
		List<Tokenizer> tokenizers = asList(whitespace);
		Tokenizer tokenizer = Tokenizers.chain(tokenizers);
		assertSame(whitespace, tokenizer);
	}
	
	@Test
	public void shouldChainPlusOne() {
		Tokenizer tokenizer = Tokenizers.chain(whitespace);
		assertSame(whitespace, tokenizer);
	}
	
	@Test
	public void shouldChainList() {
		List<Tokenizer> tokenizers = asList(whitespace, whitespace, whitespace);
		Tokenizer tokenizer = Tokenizers.chain(tokenizers);

		assertEquals(Recursive.class, tokenizer.getClass());
		Recursive recursive = (Recursive) tokenizer;
		assertEquals(tokenizers, recursive.getTokenizers());
	}
	
	@Test
	public void shouldChainArrayPlusOne() {
		Tokenizer tokenizer = Tokenizers.chain(whitespace, whitespace, whitespace);

		assertEquals(Recursive.class, tokenizer.getClass());
		Recursive recursive = (Recursive) tokenizer;
		assertEquals(asList(whitespace, whitespace, whitespace), recursive.getTokenizers());
	}
	
	@Test
	public void shouldChainRecursiveList() {
		List<Tokenizer> tokenizers = asList(whitespace,
				chain(whitespace, whitespace), whitespace);
		Tokenizer tokenizer = Tokenizers.chain(tokenizers);

		assertEquals(Recursive.class, tokenizer.getClass());
		Recursive recursive = (Recursive) tokenizer;
		assertEquals(asList(whitespace, whitespace, whitespace, whitespace),
				recursive.getTokenizers());
	}
	
	@Test
	public void shouldChainRecursiveArrayPlusOne() {
		Tokenizer tokenizer = Tokenizers.chain(whitespace,
				chain(whitespace, whitespace), whitespace);

		assertEquals(Recursive.class, tokenizer.getClass());
		Recursive recursive = (Recursive) tokenizer;
		assertEquals(asList(whitespace, whitespace, whitespace, whitespace),
				recursive.getTokenizers());
	}
	
	@Test
	public void shouldReturnFilter() {
		Tokenizer tokenizer = Tokenizers.filter(whitespace, alwaysTrue);

		assertEquals(Filter.class, tokenizer.getClass());

		Filter filter = (Filter) tokenizer;
		assertSame(whitespace, filter.getTokenizer());
		assertSame(alwaysTrue, filter.getPredicate());
	}
	
	@Test
	public void shouldReturnFilterForFilter() {
		Filter t = new Filter(whitespace, alwaysTrue);
		Tokenizer tokenizer = Tokenizers.filter(t, alwaysFalse);

		assertEquals(Filter.class, tokenizer.getClass());

		Filter filter = (Filter) tokenizer;
		
		assertSame(whitespace, filter.getTokenizer());
		assertEquals(and(alwaysTrue, alwaysFalse),filter.getPredicate());
	}
		@Test
	public void shouldReturnFilterForTransform() {
		Transform t = new Transform(whitespace, identity);
		Tokenizer tokenizer = Tokenizers.filter(t, alwaysTrue);

		assertEquals(TransformFilter.class, tokenizer.getClass());

		TransformFilter filter = (TransformFilter) tokenizer;
		
		assertSame(t, filter.getTokenizer());
		assertEquals(alwaysTrue,filter.getPredicate());
		
	}

}
