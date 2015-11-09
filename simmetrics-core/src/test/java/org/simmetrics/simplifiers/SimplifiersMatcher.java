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
