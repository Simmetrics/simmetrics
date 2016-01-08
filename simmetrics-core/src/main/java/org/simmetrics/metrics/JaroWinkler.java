/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
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

package org.simmetrics.metrics;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.commonPrefix;
import static java.lang.Math.min;

import org.simmetrics.StringDistance;
import org.simmetrics.StringMetric;

/**
 * Calculates the Jaro-Winkler distance (similarity) over two strings.
 * <p>
 * <code>
 * similarity(a,b) = jaro-winkler(a,b)
 * <br>
 * distance(a,b) = 1 - similarity(a,b)
 * </code>
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
public final class JaroWinkler implements StringMetric, StringDistance {

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
	public float distance(String a, String b) {
		return 1.0f - compare(a, b);
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
