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

package org.simmetrics;

import org.junit.Test;
import org.simmetrics.simplifier.SimplifierTest;
import org.simmetrics.simplifiers.Simplifier;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import static org.mockito.Mockito.*;

@SuppressWarnings("javadoc")
public class CachingSimplifierTest extends SimplifierTest {

	private Simplifier innerSimplifier;
	private Cache<String, String> cache;

	@Override
	protected final Simplifier getSimplifier() {

		innerSimplifier = mock(Simplifier.class);

		when(innerSimplifier.simplify("ABC")).thenReturn("abc");
		when(innerSimplifier.simplify("CCC")).thenReturn("ccc");
		when(innerSimplifier.simplify("EEE")).thenReturn("eee");

		when(innerSimplifier.simplify("")).thenReturn("");

		cache = CacheBuilder.newBuilder().initialCapacity(2).maximumSize(2)
				.build();

		return new StringMetricBuilder.CachingSimplifier(cache, innerSimplifier);
	}

	@Override
	protected final T[] getTests() {

		return new T[] { new T("ABC", "abc"), new T("CCC", "ccc"),
				new T("ABC", "abc"), new T("EEE", "eee"), new T("ABC", "abc"),
				new T("CCC", "ccc"), new T("", "")

		};
	}

	@Test
	public final void simplifyShouldUseCache() {
		for (T t : tests) {
			simplifier.simplify(t.string());
		}

		verify(innerSimplifier, times(1)).simplify("ABC");
		verify(innerSimplifier, times(2)).simplify("CCC");
	}

}
