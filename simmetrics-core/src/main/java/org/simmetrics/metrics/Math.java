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

import java.util.Set;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;

final class Math {

	private Math() {
		// Utility class
	}
	static float max(float a, float b, float c) {
		return java.lang.Math.max(java.lang.Math.max(a, b), c);
	}

	static int max(final int a, final int b, final int c) {
		return java.lang.Math.max(java.lang.Math.max(a, b), c);
	}

	static float max(float a, float b, float c, float d) {
		return java.lang.Math.max(
				java.lang.Math.max(java.lang.Math.max(a, b), c), d);
	}


	static int max(final int a, final int b, final int c, final int d) {
		return java.lang.Math.max(
				java.lang.Math.max(java.lang.Math.max(a, b), c), d);
	}

	static float min(float a, float b, float c) {
		return java.lang.Math.min(java.lang.Math.min(a, b), c);
	}

	static int min(final int a, final int b, final int c) {
		return java.lang.Math.min(java.lang.Math.min(a, b), c);
	}
	
	static float min(final float a, final float b, final float c,
			final float d) {
		return java.lang.Math.min(
				java.lang.Math.min(java.lang.Math.min(a, b), c), d);
	}

	static int min(final int a, final int b, final int c, final int d) {
		return java.lang.Math.min(
				java.lang.Math.min(java.lang.Math.min(a, b), c), d);
	}

	static <T> Multiset<T> union(Multiset<T> a, Multiset<T> b) {
		// Lager set first for performance improvement.
		// See: MathCaliper
		if (a.size() < b.size()) {
			return Multisets.union(b, a);
		}

		return Multisets.union(a, b);
	}

	static <T> Multiset<T> intersection(Multiset<T> a, Multiset<T> b) {
		// Smaller set first for performance improvement.
		// See: MathCaliper
		if (a.size() < b.size()) {
			return Multisets.intersection(a, b);
		}

		return Multisets.intersection(b, a);
	}

	static <T> Set<T> intersection(Set<T> a, Set<T> b) {
		// Smaller set first for performance improvement.
		// See: MathCaliper and note at Sets.intersection
		if (a.size() < b.size()) {
			return Sets.intersection(a, b);
		}

		return Sets.intersection(b, a);
	}
}
