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

import java.util.Iterator;
import java.util.List;

import org.simmetrics.Distance;
import org.simmetrics.ListDistance;
import org.simmetrics.StringDistance;

import com.google.common.base.Objects;

/**
 * Hamming Distance algorithm to calculate distance between lists and strings.
 *
 * <p>
 * This class is immutable and thread-safe.
 * 
 */
public final class HammingDistance {
	
	private HammingDistance() {
		//Don't construct
	}
	/**
	 * Hamming Distance algorithm to calculate distance between lists of equal
	 * size.
	 *
	 * <p>
	 * This class is immutable and thread-safe.
	 * 
	 * @param <T>
	 *            type of the token
	 * 
	 */
	public static final class HammingListDistance<T> implements
			ListDistance<T> {

		HammingListDistance() {
			// avoid synthetics
		}

		/**
		 * Measures the distance between lists {@code a} and {@code b} of equal
		 * size. The measurement results in a non-negative value. A value of
		 * {@code 0.0} indicates that {@code a} and {@code b} are similar.
		 * 
		 * @param a
		 *            list a to compare
		 * @param b
		 *            list b to compare
		 * @return a non-negative value
		 * @throws NullPointerException
		 *             when either a or b is null
		 * @throws IllegalArgumentException
		 *             when a and b differ in size
		 */
		@Override
		public float distance(List<T> a, List<T> b) {
			checkArgument(a.size() == b.size());

			if (a.isEmpty()) {
				return 0;
			}

			int distance = 0;

			Iterator<T> aItt = a.iterator();
			Iterator<T> bItt = b.iterator();

			while (aItt.hasNext()) {
				if (!Objects.equal(aItt.next(), bItt.next())) {
					distance++;
				}
			}

			return distance;
		}
		
		@Override
		public String toString() {
			return "HammingListDistance";
		}
	}

	/**
	 * Hamming Distance algorithm to calculate distance between strings of equal
	 * length.
	 *
	 * <p>
	 * This class is immutable and thread-safe.
	 * 
	 */
	public static final class HammingStringDistance implements StringDistance {

		HammingStringDistance() {
			// avoid synthetics
		}

		/**
		 * Measures the distance between strings {@code a} and {@code b} of
		 * equal length. The measurement results in a non-negative value. A
		 * value of {@code 0.0} indicates that {@code a} and {@code b} are
		 * similar.
		 * 
		 * @param a
		 *            string a to compare
		 * @param b
		 *            string b to compare
		 * @return a non-negative value
		 * @throws NullPointerException
		 *             when either a or b is null
		 * @throws IllegalArgumentException
		 *             when a and b differ in length
		 */
		@Override
		public float distance(String a, String b) {
			checkArgument(a.length() == b.length());

			if (a.isEmpty()) {
				return 0;
			}

			int distance = 0;
			for (int i = 0; i < a.length(); i++) {
				if (a.charAt(i) != b.charAt(i)) {
					distance++;
				}
			}
			return distance;
		}
		
		@Override
		public String toString() {
			return "HammingStringDistance";
		}
	}

	/**
	 * Constructs a new Hamming distance to compare lists.
	 * 
	 * @return a new Hamming distance to compare lists
	 */
	public static <T> ListDistance<T> forList() {
		return new HammingListDistance<>();
	}

	/**
	 * Constructs a new Hamming distance to compare strings.
	 * 
	 * @return a new Hamming distance to compare strings
	 */
	public static Distance<String> forString() {
		return new HammingStringDistance();
	}
}
