/*
 * #%L
 * Simmetrics Core
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


package org.simmetrics.metrics;

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
}
