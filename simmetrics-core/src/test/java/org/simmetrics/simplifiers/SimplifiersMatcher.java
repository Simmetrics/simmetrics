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
package org.simmetrics.simplifiers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.simmetrics.simplifiers.Simplifiers.ChainSimplifier;

import com.google.common.collect.ImmutableList;
import com.google.monitoring.runtime.instrumentation.common.com.google.common.collect.Lists;

@SuppressWarnings("javadoc")
public abstract class SimplifiersMatcher extends TypeSafeMatcher<Simplifier> {
	
	public static SimplifiersMatcher chain(final Simplifier simplifier,
			final Simplifier... simplifiers){
		return new SimplifiersMatcher() {
			
			@Override
			public void describeTo(Description arg0) {
				arg0.appendValue(Simplifiers.chain(simplifier, simplifiers));
			}

			@Override
			protected boolean matchesSafely(Simplifier item) {
				if(item.getClass() != ChainSimplifier.class){
					return false;
				}
				
				ChainSimplifier s = (ChainSimplifier) item;
				
				return ImmutableList.copyOf(Lists.asList(simplifier, simplifiers)).equals(s.getSimplifiers());
			}
		};
		
	}

}
