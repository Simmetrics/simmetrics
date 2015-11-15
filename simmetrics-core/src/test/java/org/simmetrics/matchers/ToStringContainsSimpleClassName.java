package org.simmetrics.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

@SuppressWarnings("javadoc")
public class ToStringContainsSimpleClassName<T> extends TypeSafeMatcher<T>{

	@Override
	public void describeTo(Description arg0) {
		arg0.appendText("toString contains simple class name");
	}

	@Override
	protected boolean matchesSafely(T item) {
		return item.toString().contains(item.getClass().getSimpleName());
	}
	
	@Override
	protected void describeMismatchSafely(T item,
			Description mismatchDescription) {
		super.describeMismatchSafely(item, mismatchDescription);
		mismatchDescription.appendValue(item.getClass());
		mismatchDescription.appendText(".toString(): ");
		mismatchDescription.appendValue(item.toString());
		mismatchDescription.appendText(" to contain: ");
		mismatchDescription.appendValue(item.getClass().getSimpleName());
	}
	
	public static <T> Matcher<T> toStringContainsSimpleClassName(){
		return new ToStringContainsSimpleClassName<>();
	}

}
