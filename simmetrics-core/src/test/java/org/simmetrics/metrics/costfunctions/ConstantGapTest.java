
package org.simmetrics.metrics.costfunctions;

import org.simmetrics.metrics.functions.ConstantGap;
import org.simmetrics.metrics.functions.Gap;

@SuppressWarnings("javadoc")
public final class ConstantGapTest extends GapCostTest {
	@Override
	public Gap getCost() {
		return new ConstantGap(-42f);
	}

	@Override
	public T[] getTests() {
		final String testString = "hello world AAAAAAA BBB ABCDEF this is a test";
		return new T[] { new T(-42f, testString, 0, 6),
				new T(-42f, testString, 3, 4),
				new T(-42f, testString, 13, 17),
				new T(-42f, testString, 19, 22),
				new T(-42f, testString, 23, 29),
				new T(-42f, testString, 5, 6), };
	}

}