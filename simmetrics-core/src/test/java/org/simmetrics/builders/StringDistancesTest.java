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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.simmetrics.simplifiers.Simplifiers.toLowerCase;
import static org.simmetrics.simplifiers.SimplifiersMatcher.chain;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.Distance;
import org.simmetrics.StringDistance;
import org.simmetrics.StringDistanceTest;
import org.simmetrics.metrics.Identity;
import org.simmetrics.builders.StringDistances.ForList;
import org.simmetrics.builders.StringDistances.ForListWithSimplifier;
import org.simmetrics.builders.StringDistances.ForMultiset;
import org.simmetrics.builders.StringDistances.ForMultisetWithSimplifier;
import org.simmetrics.builders.StringDistances.ForSet;
import org.simmetrics.builders.StringDistances.ForSetWithSimplifier;
import org.simmetrics.builders.StringDistances.ForString;
import org.simmetrics.builders.StringDistances.ForStringWithSimplifier;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;

import com.google.common.collect.Multiset;

@SuppressWarnings({ "javadoc" })
@RunWith(Enclosed.class)
public class StringDistancesTest {

	public static final class Create {

		private final Distance<String> metric = new Identity<>();
		private final Distance<List<String>> listDistance = new Identity<>();
		private final Distance<Set<String>> setDistance = new Identity<>();

		private final Simplifier simplifier = Simplifiers.toLowerCase();
		private final Simplifier simplifier2 = Simplifiers.removeNonWord();
		private final Tokenizer tokenizer = Tokenizers.whitespace();

		@Test
		public void shouldReturnSame() {
			StringDistance s = new ForString(metric);
			assertSame(s, StringDistances.create(s));
		}

		@Test
		public void shouldReturnForString() {
			StringDistance wrapped = StringDistances.create(metric);
			assertEquals(ForString.class, wrapped.getClass());
			ForString forString = (ForString) wrapped;
			assertSame(metric, forString.getDistance());
		}

		@Test
		public void shouldReturnForStringWithSimplifier() {
			ForString forString = new ForString(metric);
			StringDistance wrapped = StringDistances.create(forString, simplifier);

			assertEquals(ForStringWithSimplifier.class, wrapped.getClass());
			ForStringWithSimplifier fsws = (ForStringWithSimplifier) wrapped;
			assertSame(metric, fsws.getDistance());
			assertSame(simplifier, fsws.getSimplifier());
		}

		@Test
		public void shouldReturnForStringWithChainedSimplifiers() {
			ForStringWithSimplifier forString = new ForStringWithSimplifier(metric, simplifier);
			StringDistance wrapped = StringDistances.create(forString, simplifier2);

			assertEquals(ForStringWithSimplifier.class, wrapped.getClass());
			ForStringWithSimplifier fsws = (ForStringWithSimplifier) wrapped;
			assertSame(metric, fsws.getDistance());
		}

		@Test
		public void shouldReturnForListWithSimplifier() {
			ForList forList = new ForList(listDistance, tokenizer);
			StringDistance wrapped = StringDistances.create(forList, simplifier);

			assertEquals(ForListWithSimplifier.class, wrapped.getClass());
			ForListWithSimplifier flws = (ForListWithSimplifier) wrapped;
			assertSame(listDistance, flws.getDistance());
			assertEquals(simplifier, flws.getSimplifier());
			assertSame(tokenizer, flws.getTokenizer());
		}

		@Test
		public void shouldReturnForListWithChainedSimplifiers() {
			ForListWithSimplifier forList = new ForListWithSimplifier(listDistance, simplifier, tokenizer);
			StringDistance wrapped = StringDistances.create(forList, simplifier2);

			assertEquals(ForListWithSimplifier.class, wrapped.getClass());
			ForListWithSimplifier flws = (ForListWithSimplifier) wrapped;
			assertSame(listDistance, flws.getDistance());
			assertThat(flws.getSimplifier(), chain(simplifier2, simplifier));
			assertSame(tokenizer, flws.getTokenizer());

		}

		@Test
		public void shouldReturnForSetWithSimplifier() {
			ForSet forSet = new ForSet(setDistance, tokenizer);
			StringDistance wrapped = StringDistances.create(forSet, simplifier);

			assertEquals(ForSetWithSimplifier.class, wrapped.getClass());
			ForSetWithSimplifier fsws = (ForSetWithSimplifier) wrapped;
			assertSame(setDistance, fsws.getDistance());
			assertSame(simplifier, fsws.getSimplifier());
			assertSame(tokenizer, fsws.getTokenizer());

		}

		@Test
		public void shouldReturnForSetWithChainedSimplifiers() {
			ForSetWithSimplifier forSet = new ForSetWithSimplifier(setDistance, simplifier, tokenizer);
			StringDistance wrapped = StringDistances.create(forSet, simplifier2);

			assertEquals(ForSetWithSimplifier.class, wrapped.getClass());
			ForSetWithSimplifier fsws = (ForSetWithSimplifier) wrapped;
			assertSame(setDistance, fsws.getDistance());
			assertThat(fsws.getSimplifier(), chain(simplifier2, simplifier));
			assertSame(tokenizer, fsws.getTokenizer());
		}

	}

	public static final class CreateForList {

		private final Distance<List<String>> metric = new Identity<>();
		private final Tokenizer tokenizer = Tokenizers.whitespace();
		private final Simplifier simplifier = Simplifiers.toLowerCase();

		@Test
		public void shouldReturnForList() {

			StringDistance wrapped = StringDistances.createForListDistance(metric, tokenizer);
			assertEquals(ForList.class, wrapped.getClass());
			ForList forList = (ForList) wrapped;
			assertSame(metric, forList.getDistance());
			assertSame(tokenizer, forList.getTokenizer());
		}

		@Test
		public void shouldReturnForListWithSimplifier() {

			StringDistance wrapped = StringDistances.createForListDistance(metric, simplifier, tokenizer);
			assertEquals(ForListWithSimplifier.class, wrapped.getClass());
			ForListWithSimplifier forList = (ForListWithSimplifier) wrapped;
			assertSame(metric, forList.getDistance());
			assertSame(tokenizer, forList.getTokenizer());
			assertSame(simplifier, forList.getSimplifier());
		}

	}

	public static final class CreateForSet {

		private final Distance<Set<String>> metric = new Identity<>();
		private final Tokenizer tokenizer = Tokenizers.whitespace();
		private final Simplifier simplifier = Simplifiers.toLowerCase();

		@Test
		public void shouldReturnForSet() {

			StringDistance wrapped = StringDistances.createForSetDistance(metric, tokenizer);
			assertEquals(ForSet.class, wrapped.getClass());
			ForSet forSet = (ForSet) wrapped;
			assertSame(metric, forSet.getDistance());
			assertSame(tokenizer, forSet.getTokenizer());
		}

		@Test
		public void shouldReturnForSetWithSimplifier() {

			StringDistance wrapped = StringDistances.createForSetDistance(metric, simplifier, tokenizer);
			assertEquals(ForSetWithSimplifier.class, wrapped.getClass());
			ForSetWithSimplifier forSet = (ForSetWithSimplifier) wrapped;
			assertSame(metric, forSet.getDistance());
			assertSame(tokenizer, forSet.getTokenizer());
			assertSame(simplifier, forSet.getSimplifier());
		}

	}
	
	public static final class CreateForMultiset {

		private final Distance<Multiset<String>> metric = new Identity<>();
		private final Tokenizer tokenizer = Tokenizers.whitespace();
		private final Simplifier simplifier = Simplifiers.toLowerCase();

		@Test
		public void shouldReturnForSet() {

			StringDistance wrapped = StringDistances.createForMultisetDistance(metric, tokenizer);
			assertEquals(ForMultiset.class, wrapped.getClass());
			ForMultiset forSet = (ForMultiset) wrapped;
			assertSame(metric, forSet.getDistance());
			assertSame(tokenizer, forSet.getTokenizer());
		}

		@Test
		public void shouldReturnForSetWithSimplifier() {

			StringDistance wrapped = StringDistances.createForMultisetDistance(metric, simplifier, tokenizer);
			assertEquals(ForMultisetWithSimplifier.class, wrapped.getClass());
			ForMultisetWithSimplifier forSet = (ForMultisetWithSimplifier) wrapped;
			assertSame(metric, forSet.getDistance());
			assertSame(tokenizer, forSet.getTokenizer());
			assertSame(simplifier, forSet.getSimplifier());
		}

	}

	public static final class ForListTest extends StringDistanceTest {
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected StringDistance getMetric() {
			Distance<List<String>> identity = new Identity<>();
			return new StringDistances.ForList(identity, whitespace());
		}
		
		@Override
		protected T[] getTests() {
			return new T[]{
					new T(0.0f, "a b c","a b c"),
					new T(1.0f, "a b c","a b c d"),
					new T(1.0f, "","a b c")
			};
		}
	}
	
	public static final class ForListWithSimplifierTest extends StringDistanceTest {
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}

		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}
		
		@Override
		protected StringDistance getMetric() {
			Distance<List<String>> identity = new Identity<>();
			return new StringDistances.ForListWithSimplifier(identity, toLowerCase(), whitespace());
		}
		
		@Override
		protected T[] getTests() {
			return new T[]{
					new T(0.0f, "A B C","a b c"),
					new T(1.0f, "a b c","a b c d"),
					new T(1.0f, "","a b c")
			};
		}
	}
	
	public static final class ForSetTest extends StringDistanceTest {
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected StringDistance getMetric() {
			Distance<Set<String>> identity = new Identity<>();
			return new StringDistances.ForSet(identity, whitespace());
		}
		
		@Override
		protected T[] getTests() {
			return new T[]{
					new T(0.0f, "a b c","a b c"),
					new T(1.0f, "a b c","a b c d"),
					new T(1.0f, "","a b c")
			};
		}
	}
	
	public static final class ForSetWithSimplifierTest extends StringDistanceTest {
		
		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected StringDistance getMetric() {
			Distance<Set<String>> identity = new Identity<>();
			return new StringDistances.ForSetWithSimplifier(identity, toLowerCase(), whitespace());
		}
		
		@Override
		protected T[] getTests() {
			return new T[]{
					new T(0.0f, "A B C","a b c"),
					new T(1.0f, "a b c","a b c d"),
					new T(1.0f, "","a b c")
			};
		}
	}
	
	public static final class ForMultisetTest extends StringDistanceTest {
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected StringDistance getMetric() {
			Distance<Multiset<String>> identity = new Identity<>();
			return new StringDistances.ForMultiset(identity, whitespace());
		}
		
		@Override
		protected T[] getTests() {
			return new T[]{
					new T(0.0f, "a b c","a b c"),
					new T(1.0f, "a b c","a b c d"),
					new T(1.0f, "","a b c")
			};
		}
	}
	
	public static final class ForMultisetWithSimplifierTest extends StringDistanceTest {
		
		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected StringDistance getMetric() {
			Distance<Multiset<String>> identity = new Identity<>();
			return new StringDistances.ForMultisetWithSimplifier(identity, toLowerCase(), whitespace());
		}
		
		@Override
		protected T[] getTests() {
			return new T[]{
					new T(0.0f, "A B C","a b c"),
					new T(1.0f, "a b c","a b c d"),
					new T(1.0f, "","a b c")
			};
		}
	}
	
	public static final class ForStringTest extends StringDistanceTest {
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected StringDistance getMetric() {
			Distance<String> identity = new Identity<>();
			return new StringDistances.ForString(identity);
		}
		
		@Override
		protected T[] getTests() {
			return new T[]{
					new T(0.0f, "a b c","a b c"),
					new T(1.0f, "a b c","a b c d"),
					new T(1.0f, "","a b c")
			};
		}
	}
	
	public static final class ForStringWithSimplifierTest extends StringDistanceTest {
		
		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected StringDistance getMetric() {
			Distance<String> identity = new Identity<>();
			return new StringDistances.ForStringWithSimplifier(identity, toLowerCase());
		}
		
		@Override
		protected T[] getTests() {
			return new T[]{
					new T(0.0f, "A B C","a b c"),
					new T(1.0f, "a b c","a b c d"),
					new T(1.0f, "","a b c")
			};
		}
	}
}
