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
