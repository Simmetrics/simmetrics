
package org.simmetrics.metrics.costfunctions;

import org.simmetrics.metrics.functions.Gap;
import org.simmetrics.metrics.functions.LinearGap;

@SuppressWarnings("javadoc")
public final class LinearGapTest extends GapCostTest {
	@Override
	public Gap getCost() {
		return new LinearGap(-0.42f);
	}

	@Override
	public T[] getTests() {
		final String testString = "hello world AAAAAAA BBB ABCDEF this is a test";
		return new T[] { new T(-2.1000f, testString, 0, 6),
				new T(-0.0000f, testString, 3, 4),
				new T(-1.2600f, testString, 13, 17),
				new T(-0.8400f, testString, 19, 22),
				new T(-2.1000f, testString, 23, 29),
				new T(-0.0000f, testString, 5, 6), };
	}

}