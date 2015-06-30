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
package org.simmetrics.metrics.costfunctions;

import org.simmetrics.metrics.functions.AffineGap;
import org.simmetrics.metrics.functions.Gap;

@SuppressWarnings("javadoc")
public final class AffineGapTest {

	public static final class AffineGap1_1Over3Test extends GapCostTest {

		@Override
		public Gap getCost() {
			return new AffineGap(-1f, -1 / 3f);
		}

		@Override
		public T[] getTests() {
			final String testString = "hello world AAAAAAA BBB ABCDEF this is a test";
			return new T[] { new T(-2.6667f, testString, 0, 6),
					new T(-1.0000f, testString, 3, 4),
					new T(-2.0000f, testString, 13, 17),
					new T(-1.6667f, testString, 19, 22),
					new T(-2.6667f, testString, 23, 29),
					new T(-1.0000f, testString, 5, 6), };
		}
	}

	public static final class AffineGap5_1Test extends GapCostTest {

		@Override
		public Gap getCost() {
			return new AffineGap(-5.0f, -1.0f);
		}

		@Override
		public T[] getTests() {
			final String testString = "hello world AAAAAAA BBB ABCDEF this is a test";
			return new T[] { new T(-10.0000f, testString, 0, 6),
					new T(-5.0000f, testString, 3, 4),
					new T(-8.0000f, testString, 13, 17),
					new T(-7.0000f, testString, 19, 22),
					new T(-10.0000f, testString, 23, 29),
					new T(-5.0000f, testString, 5, 6),

			};
		}
	}
}