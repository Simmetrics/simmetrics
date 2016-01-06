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
import org.hamcrest.TypeSafeMatcher;

@SuppressWarnings("javadoc")
public class ImplementsToString<T> extends TypeSafeMatcher<T>{

	@Override
	public void describeTo(Description arg0) {
		arg0.appendText("implements toString");
	}

	@Override
	protected boolean matchesSafely(T item) {
	
		String itemToString = item.toString();
		String defaultToString = item.getClass().getName() + "@"
				+ Integer.toHexString(item.hashCode());

		return !defaultToString.equals(itemToString);
	}
	
	@Override
	protected void describeMismatchSafely(T item,
			Description mismatchDescription) {
		super.describeMismatchSafely(item, mismatchDescription);
		mismatchDescription.appendValue(item.getClass().getName());
		mismatchDescription.appendText(".toString() returned: ");
		mismatchDescription.appendValue(item.toString());
	}
	
	public static <T> Matcher<T> implementsToString(){
		return new ImplementsToString<>();
	}

}
