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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.simmetrics.StringMetricBuilder.with;
import static org.simmetrics.simplifiers.SimplifiersMatcher.chain;
import static org.simmetrics.tokenizers.Tokenizers.qGram;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.StringMetrics.ForList;
import org.simmetrics.StringMetrics.ForListWithSimplifier;
import org.simmetrics.StringMetrics.ForSet;
import org.simmetrics.StringMetrics.ForSetWithSimplifier;
import org.simmetrics.StringMetrics.ForString;
import org.simmetrics.StringMetrics.ForStringWithSimplifier;
import org.simmetrics.metrics.Identity;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;

import com.google.common.base.Predicate;

@SuppressWarnings({ "javadoc", "static-method" })
@RunWith(Enclosed.class)
public class StringMetricsTest {

	public static final class Create {

		private final Metric<String> metric = new Identity<>();
		private final Metric<List<String>> listMetric = new Identity<>();
		private final Metric<Set<String>> setMetric = new Identity<>();

		private final Simplifier simplifier = Simplifiers.toLowerCase();
		private final Simplifier simplifier2 = Simplifiers.removeNonWord();
		private final Tokenizer tokenizer = Tokenizers.whitespace();

		@Test
		public void shouldReturnSame() {
			StringMetric s = new ForString(metric);
			assertSame(s, StringMetrics.create(s));
		}

		@Test
		public void shouldReturnForString() {
			StringMetric wrapped = StringMetrics.create(metric);
			assertEquals(ForString.class, wrapped.getClass());
			ForString forString = (ForString) wrapped;
			assertSame(metric, forString.getMetric());
		}

		@Test
		public void shouldReturnForStringWithSimplifier() {
			ForString forString = new ForString(metric);
			StringMetric wrapped = StringMetrics.create(forString, simplifier);

			assertEquals(ForStringWithSimplifier.class, wrapped.getClass());
			ForStringWithSimplifier fsws = (ForStringWithSimplifier) wrapped;
			assertSame(metric, fsws.getMetric());
			assertSame(simplifier, fsws.getSimplifier());
		}

		@Test
		public void shouldReturnForStringWithChainedSimplifiers() {
			ForStringWithSimplifier forString = new ForStringWithSimplifier(metric, simplifier);
			StringMetric wrapped = StringMetrics.create(forString, simplifier2);

			assertEquals(ForStringWithSimplifier.class, wrapped.getClass());
			ForStringWithSimplifier fsws = (ForStringWithSimplifier) wrapped;
			assertSame(metric, fsws.getMetric());
		}

		@Test
		public void shouldReturnForListWithSimplifier() {
			ForList forList = new ForList(listMetric, tokenizer);
			StringMetric wrapped = StringMetrics.create(forList, simplifier);

			assertEquals(ForListWithSimplifier.class, wrapped.getClass());
			ForListWithSimplifier flws = (ForListWithSimplifier) wrapped;
			assertSame(listMetric, flws.getMetric());
			assertEquals(simplifier, flws.getSimplifier());
			assertSame(tokenizer, flws.getTokenizer());
		}

		@Test
		public void shouldReturnForListWithChainedSimplifiers() {
			ForListWithSimplifier forList = new ForListWithSimplifier(listMetric, simplifier, tokenizer);
			StringMetric wrapped = StringMetrics.create(forList, simplifier2);

			assertEquals(ForListWithSimplifier.class, wrapped.getClass());
			ForListWithSimplifier flws = (ForListWithSimplifier) wrapped;
			assertSame(listMetric, flws.getMetric());
			assertThat(flws.getSimplifier(), chain(simplifier2, simplifier));
			assertSame(tokenizer, flws.getTokenizer());

		}

		@Test
		public void shouldReturnForSetWithSimplifier() {
			ForSet forSet = new ForSet(setMetric, tokenizer);
			StringMetric wrapped = StringMetrics.create(forSet, simplifier);

			assertEquals(ForSetWithSimplifier.class, wrapped.getClass());
			ForSetWithSimplifier fsws = (ForSetWithSimplifier) wrapped;
			assertSame(setMetric, fsws.getMetric());
			assertSame(simplifier, fsws.getSimplifier());
			assertSame(tokenizer, fsws.getTokenizer());

		}

		@Test
		public void shouldReturnForSetWithChainedSimplifiers() {
			ForSetWithSimplifier forSet = new ForSetWithSimplifier(setMetric, simplifier, tokenizer);
			StringMetric wrapped = StringMetrics.create(forSet, simplifier2);

			assertEquals(ForSetWithSimplifier.class, wrapped.getClass());
			ForSetWithSimplifier fsws = (ForSetWithSimplifier) wrapped;
			assertSame(setMetric, fsws.getMetric());
			assertThat(fsws.getSimplifier(), chain(simplifier2, simplifier));
			assertSame(tokenizer, fsws.getTokenizer());
		}

	}

	public static final class CreateForList {

		private final Metric<List<String>> metric = new Identity<>();
		private final Tokenizer tokenizer = Tokenizers.whitespace();
		private final Simplifier simplifier = Simplifiers.toLowerCase();

		@Test
		public void shouldReturnForList() {

			StringMetric wrapped = StringMetrics.createForListMetric(metric, tokenizer);
			assertEquals(ForList.class, wrapped.getClass());
			ForList forList = (ForList) wrapped;
			assertSame(metric, forList.getMetric());
			assertSame(tokenizer, forList.getTokenizer());
		}

		@Test
		public void shouldReturnForListWithSimplifier() {

			StringMetric wrapped = StringMetrics.createForListMetric(metric, simplifier, tokenizer);
			assertEquals(ForListWithSimplifier.class, wrapped.getClass());
			ForListWithSimplifier forList = (ForListWithSimplifier) wrapped;
			assertSame(metric, forList.getMetric());
			assertSame(tokenizer, forList.getTokenizer());
			assertSame(simplifier, forList.getSimplifier());
		}

	}

	public static final class CreateForSet {

		private final Metric<Set<String>> metric = new Identity<>();
		private final Tokenizer tokenizer = Tokenizers.whitespace();
		private final Simplifier simplifier = Simplifiers.toLowerCase();

		@Test
		public void shouldReturnForSet() {

			StringMetric wrapped = StringMetrics.createForSetMetric(metric, tokenizer);
			assertEquals(ForSet.class, wrapped.getClass());
			ForSet forSet = (ForSet) wrapped;
			assertSame(metric, forSet.getMetric());
			assertSame(tokenizer, forSet.getTokenizer());
		}

		@Test
		public void shouldReturnForSetWithSimplifier() {

			StringMetric wrapped = StringMetrics.createForSetMetric(metric, simplifier, tokenizer);
			assertEquals(ForSetWithSimplifier.class, wrapped.getClass());
			ForSetWithSimplifier forSet = (ForSetWithSimplifier) wrapped;
			assertSame(metric, forSet.getMetric());
			assertSame(tokenizer, forSet.getTokenizer());
			assertSame(simplifier, forSet.getSimplifier());
		}

	}

	public static final class CreateIdentity extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.identity();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.0f, "To repeat repeat is to repeat", ""),
					new T(1.0f, "To repeat repeat is to repeat", "To repeat repeat is to repeat") };
		}
	}

	public static final class CreateCosineSimilarity extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.cosineSimilarity();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.5000f, "test string1", "test string2"),
					new T(0.5000f, "test string1", "test string2"), new T(0.7071f, "test", "test string2"),
					new T(0.0000f, "", "test string2"),

					new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"), new T(0.7500f, "a b c d", "a b c e"), };
		}

	}

	public static final class CreateDiceSimlarity extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.diceSimilarity();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.5000f, "test string1", "test string2"), new T(0.6666f, "test", "test string2"),
					new T(0.0000f, "", "test string2"), new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.7500f, "a b c d", "a b c e"), };
		}

	}

	public static final class CreateEuclideanDistance extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.euclideanDistance();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.5000f, "test string1", "test string2"), new T(0.5527f, "test", "test string2"),
					new T(0.2928f, "", "test string2"), new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.7500f, "a b c d", "a b c e"), };
		}

	}

	public static final class CreateJaccardSimilarity extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.jaccardSimilarity();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.3333f, "test string1", "test string2"), new T(0.5000f, "test", "test string2"),
					new T(0.0000f, "", "test string2"), new T(0.6000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.6000f, "a b c d", "a b c e"), };
		}

	}

	public static final class CreateMatchingCoefficient extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.matchingCoefficient();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.3333f, "test string1", "test string2"), new T(0.5000f, "test", "test string2"),
					new T(0.0000f, "", "test string2"), new T(0.6000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.6000f, "a b c d", "a b c e"), };
		}

	}

	public static final class CreateMongeElkan extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.mongeElkan();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.9286f, "test string1", "test string2"), new T(0.8660f, "test", "test string2"),
					new T(0.0000f, "", "test string2"), new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.7500f, "a b c d", "a b c e"), };
		}

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

	}

	public static final class CreateOverlapCoefficient extends StringMetricTest {
		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.overlapCoefficient();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.5000f, "test string1", "test string2"), new T(1.0000f, "test", "test string2"),
					new T(0.0000f, "", "test string2"), new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.7500f, "a b c d", "a b c e"), };
		}

		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}

	}

	public static final class CreateQGramsDistance extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.qGramsDistance();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.7857f, "test string1", "test string2"), new T(0.3999f, "test", "test string2"),
					new T(0.0000f, "", "test string2"), new T(0.7058f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.6666f, "a b c d", "a b c e"), };
		}

	}

	public static final class CreateSimonWhite extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.simonWhite();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.8889f, "test string1", "test string2"), new T(0.5000f, "test", "test string2"),
					new T(0.0000f, "", "test string2"), new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee") };
		}

	}

	public static final class CreateSoundex extends StringMetricTest {

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.soundex();
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(0.5000f, "Tannhauser", "Ozymandias"), new T(1.0000f, "James", "Jones"),
					new T(0.0000f, "", "Jenkins"), new T(0.8833f, "Travis", "Trevor"),
					new T(0.8666f, "Marcus", "Marinus"), };
		}

		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}
	}

	public static final class Utilities {

		private static final float DELTA = 0.001f;

		private final float[] expected = new float[] { 0.071f, 0.153f, 0.000f, 0.933f, 1.000f };

		private StringMetric metric = with(new SimonWhite<String>()).tokenize(whitespace())
				.filter(new Predicate<String>() {

					@Override
					public boolean apply(String input) {
						return input.length() >= 2;
					}
				}).tokenize(qGram(2)).build();

		private final String[] names1 = new String[] { "Louis Philippe, le Roi Citoyen", "Charles X", "Louis XVIII",
				"Napoleon II", "Napoleon I" };

		@Test
		public void blockDistance() {
			assertNotNull(StringMetrics.blockDistance());
		}

		@SuppressWarnings("deprecation")
		@Test
		public void compareArray() {
			assertArrayEquals(expected, StringMetrics.compare(metric, "Napoleon I", names1), DELTA);

		}

		@SuppressWarnings("deprecation")
		@Test
		public void compareArrays() {
			assertArrayEquals(new float[0], StringMetrics.compareArrays(metric, new String[] {}, new String[] {}),
					DELTA);

			final String[] names2 = new String[] { "Louis XVIII", "Napoléon Ier, le Grand",
					"Louis XVI le Restaurateur de la Liberté Française", "Napoleon II", "Napoleon I" };
			final float[] expected2 = new float[] { 0.275f, 0.095f, 0.285f, 1.000f, 1.000f };

			assertArrayEquals(expected2, StringMetrics.compareArrays(metric, names1, names2), DELTA);

		}

		@SuppressWarnings("deprecation")
		@Test(expected = IllegalArgumentException.class)
		public void compareArraysDifferentLength() {
			StringMetrics.compareArrays(metric, new String[] { "" }, new String[] {});
		}

		@SuppressWarnings("deprecation")
		@Test
		public void compareList() {
			assertArrayEquals(expected, StringMetrics.compare(metric, "Napoleon I", Arrays.asList(names1)), DELTA);
		}
	}

	public static final class CreateStringMetrics {
		@Test
		public void damerauLevenshtein() {
			assertNotNull(StringMetrics.damerauLevenshtein());
		}

		@Test
		public void jaro() {
			assertNotNull(StringMetrics.jaro());
		}

		@Test
		public void jaroWinkler() {
			assertNotNull(StringMetrics.jaroWinkler());
		}

		@Test
		public void levenshtein() {
			assertNotNull(StringMetrics.levenshtein());
		}

		@Test
		public void needlemanWunch() {
			assertNotNull(StringMetrics.needlemanWunch());

		}

		@Test
		public void smithWaterman() {
			assertNotNull(StringMetrics.smithWaterman());

		}

		@Test
		public void smithWatermanGotoh() {
			assertNotNull(StringMetrics.smithWatermanGotoh());
		}
	}
}
