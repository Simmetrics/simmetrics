/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
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


package org.simmetrics.utils;

@SuppressWarnings("javadoc")
@Deprecated
public final class Math {

	private Math() {
		// Utility class
	}
	public static float max(float a, float b, float c) {
		return java.lang.Math.max(java.lang.Math.max(a, b), c);
	}

	public static int max(final int a, final int b, final int c) {
		return java.lang.Math.max(java.lang.Math.max(a, b), c);
	}

	public static float max(float a, float b, float c, float d) {
		return java.lang.Math.max(
				java.lang.Math.max(java.lang.Math.max(a, b), c), d);
	}


	public static int max(final int a, final int b, final int c, final int d) {
		return java.lang.Math.max(
				java.lang.Math.max(java.lang.Math.max(a, b), c), d);
	}

	public static float min(float a, float b, float c) {
		return java.lang.Math.min(java.lang.Math.min(a, b), c);
	}

	public static int min(final int a, final int b, final int c) {
		return java.lang.Math.min(java.lang.Math.min(a, b), c);
	}
	
	public static float min(final float a, final float b, final float c,
			final float d) {
		return java.lang.Math.min(
				java.lang.Math.min(java.lang.Math.min(a, b), c), d);
	}

	public static int min(final int a, final int b, final int c, final int d) {
		return java.lang.Math.min(
				java.lang.Math.min(java.lang.Math.min(a, b), c), d);
	}
}
