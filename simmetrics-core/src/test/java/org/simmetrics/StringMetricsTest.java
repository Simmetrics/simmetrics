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

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.BlockDistance;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.metrics.Jaro;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.simplifiers.Case;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Whitespace;

import com.google.common.base.Predicate;

import static org.simmetrics.StringMetricBuilder.with;

@SuppressWarnings("javadoc")
public final class StringMetricsTest {

	private static final float DELTA = 0.001f;
	private final String[] names1 = new String[] {
			"Louis Philippe, le Roi Citoyen", "Charles X", "Louis XVIII",
			"Napoleon II", "Napoleon I" };

	private final float[] expected = new float[] { 0.071f, 0.153f, 0.000f,
			0.933f, 1.000f };

	private StringMetric metric = with(new SimonWhite<String>())
			.tokenize(new Whitespace()).filter(new Predicate<String>() {

				@Override
				public boolean apply(String input) {
					return input.length() >= 2;
				}
			}).tokenize(new QGram(2)).build();

	@Test
	public void compareList() {
		assertArrayEquals(
				expected,
				StringMetrics.compare(metric, "Napoleon I",
						Arrays.asList(names1)), DELTA);
	}

	@Test
	public void compareArray() {
		assertArrayEquals(expected,
				StringMetrics.compare(metric, "Napoleon I", names1), DELTA);

	}

	@Test
	public void compareArrays() {
		assertArrayEquals(new float[0], StringMetrics.compareArrays(metric,
				new String[] {}, new String[] {}), DELTA);

		final String[] names2 = new String[] { "Louis XVIII",
				"Napoléon Ier, le Grand",
				"Louis XVI le Restaurateur de la Liberté Française",
				"Napoleon II", "Napoleon I" };
		final float[] expected2 = new float[] { 0.275f, 0.095f, 0.285f, 1.000f,
				1.000f };

		assertArrayEquals(expected2,
				StringMetrics.compareArrays(metric, names1, names2), DELTA);

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void compareArraysDifferentLength(){
		StringMetrics.compareArrays(metric,
				new String[] {""}, new String[] {});
	}
	

	@Test
	public void blockDistance() {
		assertNotNull(StringMetrics.blockDistance());
	}

	@Test
	public void cosineSimilarity() {
		assertNotNull(StringMetrics.cosineSimilarity());
	}
@Test
	public void damerauLevenshtein() {
		assertNotNull(StringMetrics.damerauLevenshtein());
	}

	@Test
	public void diceSimilarity() {
		assertNotNull(StringMetrics.diceSimilarity());

	}

	@Test
	public void euclideanDistance() {
		assertNotNull(StringMetrics.euclideanDistance());

	}

	@Test
	public void jaccardSimilarity() {
		assertNotNull(StringMetrics.jaccardSimilarity());

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
	public void matchingCoefficient() {
		assertNotNull(StringMetrics.matchingCoefficient());

	}

	@Test
	public void mongeElkan() {
		assertNotNull(StringMetrics.mongeElkan());

	}

	@Test
	public void needlemanWunch() {
		assertNotNull(StringMetrics.needlemanWunch());

	}

	@Test
	public void overlapCoefficient() {
		assertNotNull(StringMetrics.overlapCoefficient());

	}

	@Test
	public void qGramsDistance() {
		assertNotNull(StringMetrics.qGramsDistance());

	}

	@Test
	public void simonWhite() {
		assertNotNull(StringMetrics.simonWhite());

	}

	@Test
	public void smithWaterman() {
		assertNotNull(StringMetrics.smithWaterman());

	}

	@Test
	public void smithWatermanGotoh() {
		assertNotNull(StringMetrics.smithWatermanGotoh());
	}

	@Test
	public void soundex() {
		assertNotNull(StringMetrics.soundex());
	}

	public static final class ForList extends StringMetricTest {

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(1.0000f, "test string1", "test string1"),
					new T(0.5000f, "test string1", "test string2"),
					new T(0.6666f, "test", "test string2"),
					new T(0.0000f, "", "test string2"),
					new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.5000f, "aaa bbb", "aaa aaa"),
					new T(0.6666f, "aaa", "aaa aaa"),
					new T(0.7500f, "a b c d", "a b c e"),
					new T(0.5000f, "a b c d", "a b e f"), };
		}

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.createForListMetric(
					new BlockDistance<String>(), new Whitespace());
		}

	}

	public static final class ForListWithSimplifier extends StringMetricTest {

		@Override
		protected T[] getStringTests() {
			return new T[] {

			new T(1.0000f, "test string1", "test string1"),
					new T(0.5000f, "test string1", "test string2"),
					new T(0.6666f, "test", "test string2"),
					new T(0.0000f, "", "test string2"),
					new T(0.7500f, "AAA bbb ccc DDDD", "aaa bbb ccc eee"),
					new T(0.5000f, "aaa BBB", "aaa aaa"),
					new T(0.6666f, "aaa", "AAA aaa"),
					new T(0.7500f, "a B c d", "a b c e"),
					new T(0.5000f, "a B c d", "a b e f"),

			};
		}

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.createForListMetric(
					new BlockDistance<String>(), new Case.Lower(),
					new Whitespace());
		}

	}

	public static final class ForSet extends StringMetricTest {

		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(1.0000f, "test string1", "test string1"),
					new T(0.5000f, "test string1", "test string2"),
					new T(0.7071f, "test", "test string2"),
					new T(0.0000f, "", "test string2"),
					new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.7071f, "aaa bbb", "aaa aaa"),
					new T(1.0000f, "aaa", "aaa aaa"),
					new T(0.7500f, "a b c d", "a b c e"),
					new T(0.5000f, "a b c d", "a b e f"), };
		}

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.createForSetMetric(
					new CosineSimilarity<String>(), new Whitespace());
		}

	}

	public static final class ForSetWithSimplifier extends StringMetricTest {

		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}

		@Override
		protected T[] getStringTests() {
			return new T[] { new T(1.0000f, "test string1", "test string1"),
					new T(0.5000f, "test string1", "test string2"),
					new T(0.7071f, "test", "test string2"),
					new T(0.0000f, "", "test string2"),
					new T(0.7500f, "AAA bbb ccc DDDD", "aaa bbb ccc eee"),
					new T(0.7071f, "aaa BBB", "aaa aaa"),
					new T(1.0000f, "aaa", "AAA aaa"),
					new T(0.7500f, "a B c d", "a b c e"),
					new T(0.5000f, "a B c d", "a b e f"),

			};
		}

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.createForSetMetric(
					new CosineSimilarity<String>(), new Case.Lower(),
					new Whitespace());
		}

	}

	public static final class ForStringWithSimplifier extends StringMetricTest {

		@Override
		protected boolean satisfiesSubadditivity() {
			return false;
		}

		@Override
		protected T[] getStringTests() {
			return new T[] {
					new T(0.9444f, "test string1", "test string2"),
					new T(0.7777f, "test", "test string2"),
					new T(0.0000f, "", "test string2"),
					new T(0.8667f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.9048f, "a b c d", "a b c e"),
			};
		}

		@Override
		protected Metric<String> getMetric() {
			return StringMetrics.create(new Jaro(), new Case.Lower());
		}

	}
}
