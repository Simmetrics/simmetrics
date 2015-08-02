
package org.simmetrics;

@SuppressWarnings("javadoc")
public abstract class StringMetricTest extends MetricTest<String> {

	protected static final class T {
		protected final float similarity;
		protected final String string1;
		protected final String string2;

		public T(float similarity, String string1, String string2) {
			this.string1 = string1;
			this.string2 = string2;
			this.similarity = similarity;
		}

	}

	private static MetricTest.T<String>[] transformTest(T... tests) {
		@SuppressWarnings("unchecked")
		MetricTest.T<String>[] transformed = new MetricTest.T[tests.length];
		for (int i = 0; i < tests.length; i++) {
			T t = tests[i];
			transformed[i] = new MetricTest.T<>(t.similarity,t.string1,t.string2);
		}
		return transformed;
	}

	@Override
	protected final MetricTest.T<String>[] getTests() {
		return transformTest(getStringTests());
	}

	protected abstract T[] getStringTests();

	@Override
	protected final String getEmpty() {
		return "";
	}

}