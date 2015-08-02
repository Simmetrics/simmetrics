/*
 * #%L
 * Simmetrics Examples
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package org.simmetrics.example;

import static org.simmetrics.StringMetricBuilder.with;

import java.util.Arrays;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetrics;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.Case;
import org.simmetrics.tokenizers.QGram;

public class CacheExample {

	public static void main(String[] args) {

		String[] names = new String[] { "Maryjo Murff", "Page Desmarais",
				"Alayna Sawin", "Charmain Scoggin", "Sanora Larkey" };
		String name = "Gearldine Desanti";

		StringMetric metric = with(new CosineSimilarity<String>())
				.simplify(new Case.Lower())
				.simplifierCache()
				.tokenize(new QGram(2))
				.tokenizerCache()
				.build();

		float[] scores = StringMetrics.compare(metric, name, names);

		System.out.println(String.format(
				"Comparing %s to %s using %s gives: %s",
				Arrays.toString(names), name, metric, Arrays.toString(scores)));

	}
}
