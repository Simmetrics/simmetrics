
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
