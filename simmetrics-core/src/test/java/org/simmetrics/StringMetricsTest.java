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
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Whitespace;

import com.google.common.base.Predicate;

import static org.simmetrics.StringMetricBuilder.with;
import static org.simmetrics.StringMetrics.*;


public class StringMetricsTest {

	private static final float DELTA = 0.001f;
	private final String[] names1 = new String[] {
			"Louis Philippe, le Roi Citoyen", "Charles X", "Louis XVIII",
			"Napoleon II", "Napoleon I" };

	private final float[] expected = new float[] { 0.071f, 0.153f, 0.000f,
			0.933f, 1.000f };

	private StringMetric metric = 
			 with(new SimonWhite<String>())
				.tokenize(new Whitespace())
				.filter(new Predicate<String>() {
					
					@Override
					public boolean apply(String input) {
						return input.length() >= 2;
					}
				})
				.tokenize(new QGram(2))
				.build();

	@Test
	public void testCompareList() {
		assertArrayEquals(expected,
				compare(metric, "Napoleon I", Arrays.asList(names1)), DELTA);
	}

	@Test
	public void testCompareArray() {
		assertArrayEquals(expected, compare(metric, "Napoleon I", names1),
				DELTA);

	}

	@Test
	public void testCompareArrays() {
		assertArrayEquals(new float[0],
				compareArrays(metric, new String[] {}, new String[] {}), DELTA);

		final String[] names2 = new String[] { "Louis XVIII",
				"Napoléon Ier, le Grand",
				"Louis XVI le Restaurateur de la Liberté Française",
				"Napoleon II", "Napoleon I" };
		final float[] expected2 = new float[] { 0.275f, 0.095f, 0.285f, 1.000f,
				1.000f };

		assertArrayEquals(expected2, compareArrays(metric, names1, names2),
				DELTA);

	}

}
