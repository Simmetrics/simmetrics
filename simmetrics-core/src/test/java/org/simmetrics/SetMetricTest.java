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

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
public abstract class SetMetricTest extends MetricTest<Set<String>> {

	protected static final class T {
		protected final float similarity;
		protected final String string1;
		protected final String string2;

		public T(float similarity, String string1, String string2) {
			this.string1 = string1;
			this.string2 = string2;
			this.similarity = similarity;
		}

	}
	
	@Override
	protected MetricTest.T<Set<String>>[] getTests() {
		return transformTest(getTokenizer(), getSetTests());
	}

	protected abstract T[] getSetTests();

	protected abstract Tokenizer getTokenizer();

	private static MetricTest.T<Set<String>>[] transformTest(
			Tokenizer tokenizer, T... tests) {
		@SuppressWarnings("unchecked")
		MetricTest.T<Set<String>>[] transformed = new MetricTest.T[tests.length];
		for (int i = 0; i < tests.length; i++) {
			T t = tests[i];
			transformed[i] = new MetricTest.T<>(t.similarity,
					unmodifiableSet(tokenizer.tokenizeToSet(t.string1)),
					unmodifiableSet(tokenizer.tokenizeToSet(t.string2)));
		}
		return transformed;
	}

	@Override
	protected Set<String> getEmpty() {
		return emptySet();
	}
}
