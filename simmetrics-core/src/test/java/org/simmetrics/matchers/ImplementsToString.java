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
