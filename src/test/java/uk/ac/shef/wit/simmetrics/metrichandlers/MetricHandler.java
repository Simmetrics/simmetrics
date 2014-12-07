/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package uk.ac.shef.wit.simmetrics.metrichandlers;

import uk.ac.shef.wit.simmetrics.similaritymetrics.InterfaceStringMetric;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;

public class MetricHandler {

	/**
	 * Gets the metrics available in the uk.ac.shef.wit.simmetrics package.
	 *
	 */
	public static Set<InterfaceStringMetric> getMetricsAvailable() {
		Reflections reflections = new Reflections("uk.ac.shef.wit.simmetrics");

		Set<Class<? extends InterfaceStringMetric>> metrics = reflections
				.getSubTypesOf(InterfaceStringMetric.class);

		Set<InterfaceStringMetric> retSet = new HashSet<InterfaceStringMetric>();
		
		for (Class<? extends InterfaceStringMetric> m : metrics) {
			InterfaceStringMetric metric = createMetric(m);
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
	public static InterfaceStringMetric createMetric(
			Class<? extends InterfaceStringMetric> metric) {
		InterfaceStringMetric aplugin = null;

		try {
			Constructor<? extends InterfaceStringMetric> constructor = metric
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
