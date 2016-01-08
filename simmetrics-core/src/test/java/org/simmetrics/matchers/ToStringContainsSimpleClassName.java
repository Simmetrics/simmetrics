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
package org.simmetrics.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

@SuppressWarnings("javadoc")
public class ToStringContainsSimpleClassName<T> extends TypeSafeDiagnosingMatcher<T>{

	@Override
	public void describeTo(Description arg0) {
		arg0.appendText(".toString() contains simple class name");
	}

	public static <T> Matcher<T> toStringContainsSimpleClassName(){
		return new ToStringContainsSimpleClassName<>();
	}

	@Override
	protected boolean matchesSafely(T item, Description mismatchDescription) {
		mismatchDescription.appendText("was ");
		mismatchDescription.appendValue(item.getClass().getSimpleName());
		mismatchDescription.appendText(".toString()=");
		mismatchDescription.appendValue(item.toString());
		
		return item.toString().contains(item.getClass().getSimpleName());
	}

}
