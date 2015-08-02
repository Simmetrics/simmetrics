
package org.simmetrics.metrics.costfunctions;

import org.simmetrics.metrics.functions.AffineGap;
import org.simmetrics.metrics.functions.Gap;

@SuppressWarnings("javadoc")
public final class AffineGapTest {

	public static final class AffineGap1_1Over3Test extends GapCostTest {

		@Override
		public Gap getCost() {
			return new AffineGap(-1f, -1 / 3f);
		}

		@Override
		public T[] getTests() {
			final String testString = "hello world AAAAAAA BBB ABCDEF this is a test";
			return new T[] { new T(-2.6667f, testString, 0, 6),
					new T(-1.0000f, testString, 3, 4),
					new T(-2.0000f, testString, 13, 17),
					new T(-1.6667f, testString, 19, 22),
					new T(-2.6667f, testString, 23, 29),
					new T(-1.0000f, testString, 5, 6), };
		}
	}

	public static final class AffineGap5_1Test extends GapCostTest {

		@Override
		public Gap getCost() {
			return new AffineGap(-5.0f, -1.0f);
		}

		@Override
		public T[] getTests() {
			final String testString = "hello world AAAAAAA BBB ABCDEF this is a test";
			return new T[] { new T(-10.0000f, testString, 0, 6),
					new T(-5.0000f, testString, 3, 4),
					new T(-8.0000f, testString, 13, 17),
					new T(-7.0000f, testString, 19, 22),
					new T(-10.0000f, testString, 23, 29),
					new T(-5.0000f, testString, 5, 6),

			};
		}
	}
}