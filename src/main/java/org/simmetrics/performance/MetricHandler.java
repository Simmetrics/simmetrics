/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package org.simmetrics.performance;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.simmetrics.StringMetric;

public class MetricHandler {

	/**
	 * Gets the metrics available in the uk.ac.shef.wit.simmetrics package.
	 *
	 */
	public static Set<StringMetric> getMetricsAvailable() {
		Reflections reflections = new Reflections("org.simmetrics");

		Set<Class<? extends StringMetric>> metrics = reflections
				.getSubTypesOf(StringMetric.class);

		Set<StringMetric> retSet = new HashSet<StringMetric>();
		
		for (Class<? extends StringMetric> m : metrics) {
			StringMetric metric = createMetric(m);
			if (metric != null)
				retSet.add(metric);
		}
		
		return retSet;

	}

	/**
	 * creates a metric with a given name using reflection.
	 *
	 * @param metricName
	 *            the <code>String</code> name of the metric to create
	 * @return if a valid name the metric otherwise null
	 */
	public static StringMetric createMetric(
			Class<? extends StringMetric> metric) {
		StringMetric aplugin = null;

		
		try {
			Constructor<? extends StringMetric> constructor = metric
					.getConstructor();
			return constructor.newInstance();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return aplugin;
	}
}
