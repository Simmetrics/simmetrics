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
package org.simmetrics.utils;

import org.junit.Test;
import org.simmetrics.simplifier.SimplifierTest;
import org.simmetrics.simplifiers.Simplifier;
import static org.mockito.Mockito.*;

@SuppressWarnings("javadoc")
public class CachingSimplifierTest extends SimplifierTest {

	private Simplifier innerSimplifier;

	@Override
	protected final Simplifier getSimplifier() {

		innerSimplifier = mock(Simplifier.class);

		when(innerSimplifier.simplify("ABC")).thenReturn("abc");
		when(innerSimplifier.simplify("CCC")).thenReturn("ccc");
		when(innerSimplifier.simplify("EEE")).thenReturn("eee");

		when(innerSimplifier.simplify("")).thenReturn("");

		return new CachingSimplifier(2, 2, innerSimplifier);
	}

	@Override
	public final T[] getTests() {

		return new T[] { new T("ABC", "abc"), new T("CCC", "ccc"),
				new T("ABC", "abc"), new T("EEE", "eee"), new T("ABC", "abc"),
				new T("CCC", "ccc"), new T("", "")

		};
	}

	@Test
	public final void simplifyUsesCache() {
		for (T t : tests) {
			simplifier.simplify(t.string());
		}

		verify(innerSimplifier, times(1)).simplify("ABC");
		verify(innerSimplifier, times(2)).simplify("CCC");
	}

}
