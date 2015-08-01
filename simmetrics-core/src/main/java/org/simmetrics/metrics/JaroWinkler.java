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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.commonPrefix;
import static java.lang.Math.min;

import org.simmetrics.StringMetric;

/**
 * Jaro-Winkler algorithm providing a similarity measure between two strings.
 * <p>
 * Can be configured with a prefix adjustment scale, max prefix length and boost
 * threshold.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">Wikipedia
 *      - Jaro-Winkler distance</a>
 * 
 * @see Jaro
 * 
 * 
 */
public class JaroWinkler implements StringMetric {

	private final Jaro jaro = new Jaro();

	private static final float PREFIX_SCALE = 0.1f;
	private static final float WINKLER_BOOST_THRESHOLD = 0.7f;

	private static final float BOOST_THRESHOLD = 0.0f;
	private static final int MAX_PREFIX_LENGTH = 4;

	private final float boostThreshold;
	private final float prefixScale;
	private final int maxPrefixLength;

	/**
	 * Constructs a new JaroWinkler metric.
	 */
	public JaroWinkler() {
		this(BOOST_THRESHOLD, PREFIX_SCALE, MAX_PREFIX_LENGTH);
	}

	/**
	 * Constructs a new JaroWinkler metric with Winklers boost threshold of 0.7.
	 * 
	 * @return a new JaroWinkler metric
	 */
	public static JaroWinkler createWithBoostThreshold() {
		return new JaroWinkler(WINKLER_BOOST_THRESHOLD, PREFIX_SCALE,
				MAX_PREFIX_LENGTH);
	}

	/**
	 * Constructs a new JaroWinkler metric.
	 * 
	 * 
	 * @param boostThreshold
	 *            minimum jaro score for which the score is boosted
	 * @param prefixScale
	 *            scale at which a common prefix adds a bonus
	 * @param maxPrefixLength
	 *            cutoff at which a longer common prefix does not improve the
	 *            score
	 */
	public JaroWinkler(float boostThreshold, float prefixScale,
			int maxPrefixLength) {
		checkArgument(boostThreshold >= 0);
		checkArgument(0 <= prefixScale && prefixScale <= 1);
		checkArgument(maxPrefixLength >= 0);

		this.boostThreshold = boostThreshold;
		this.prefixScale = prefixScale;
		this.maxPrefixLength = maxPrefixLength;
	}

	@Override
	public float compare(final String a, final String b) {
		final float jaroScore = jaro.compare(a, b);

		if (jaroScore < boostThreshold) {
			return jaroScore;
		}

		int prefixLength = min(commonPrefix(a, b).length(), maxPrefixLength);

		return jaroScore + (prefixLength * prefixScale * (1.0f - jaroScore));
	}

	@Override
	public String toString() {
		return "JaroWinkler [boostThreshold=" + boostThreshold
				+ ", prefixScale=" + prefixScale + ", maxPrefixLength="
				+ maxPrefixLength + "]";
	}

}
