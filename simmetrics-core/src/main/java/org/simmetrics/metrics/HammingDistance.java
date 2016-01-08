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

import java.util.Iterator;
import java.util.List;

import org.simmetrics.ListDistance;
import org.simmetrics.StringDistance;

import com.google.common.base.Objects;

/**
 * Calculates the Hamming distance distance between lists and strings.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Hamming_distance">Wikipedia -
 *      Hamming distance</a>
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
	private static final class HammingListDistance<T> implements
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
	private static final class HammingStringDistance implements StringDistance {

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
			for (int i = 0, length =  a.length(); i < length; i++) {
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
	 * Returns a Hamming distance to compare lists.
	 * 
	 * @return a Hamming distance to compare lists
	 */
	public static <T> ListDistance<T> forList() {
		return new HammingListDistance<>();
	}

	/**
	 * Returns a Hamming distance to compare strings.
	 * 
	 * @return a new Hamming distance to compare strings
	 */
	public static StringDistance forString() {
		return new HammingStringDistance();
	}
}
