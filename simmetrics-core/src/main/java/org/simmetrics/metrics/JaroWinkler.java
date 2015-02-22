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
package org.simmetrics.metrics;

import static com.google.common.base.Strings.commonPrefix;
import static java.lang.Math.min;

import org.simmetrics.StringMetric;

/**
 * 
 * 
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">Wikipedia
 *      - Jaro-Winkler distance</a>
 * 
 * @see Jaro
 * @author mpkorstanje
 * 
 */
public class JaroWinkler implements StringMetric {

	private final Jaro jaro = new Jaro();

	private static final float PREFIX_ADJUSTMENT_SCALE = 0.1f;
	private static final float BOOST_THRESHOLD = 0;
	private static final int MAX_PREFIX_LENGTH = 4;

	private final float boostThreshold;
	private final float prefixAdjustmentScale;
	private final int maxPrefixLength;

	public JaroWinkler() {
		this(BOOST_THRESHOLD, PREFIX_ADJUSTMENT_SCALE, MAX_PREFIX_LENGTH);
	}

	public JaroWinkler(float boostThreshold, float prefixAdjustmentScale,
			int maxPrefixLength) {
		super();
		this.boostThreshold = boostThreshold;
		this.prefixAdjustmentScale = prefixAdjustmentScale;
		this.maxPrefixLength = maxPrefixLength;
	}

	@Override
	public float compare(final String a, final String b) {
		final float jaroScore = jaro.compare(a, b);

		if (jaroScore < boostThreshold) {
			return jaroScore;
		}

		int prefixLength = min(commonPrefix(a, b).length(), maxPrefixLength);

		return jaroScore
				+ (prefixLength * prefixAdjustmentScale * (1.0f - jaroScore));
	}

	@Override
	public String toString() {
		return "JaroWinkler";
	}
}
