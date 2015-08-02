
package org.simmetrics;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
public abstract class SetMetricTest extends MetricTest<Set<String>> {

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
	
	@Override
	protected MetricTest.T<Set<String>>[] getTests() {
		return transformTest(getTokenizer(), getSetTests());
	}

	protected abstract T[] getSetTests();

	protected abstract Tokenizer getTokenizer();

	private static MetricTest.T<Set<String>>[] transformTest(
			Tokenizer tokenizer, T... tests) {
		@SuppressWarnings("unchecked")
		MetricTest.T<Set<String>>[] transformed = new MetricTest.T[tests.length];
		for (int i = 0; i < tests.length; i++) {
			T t = tests[i];
			transformed[i] = new MetricTest.T<>(t.similarity,
					unmodifiableSet(tokenizer.tokenizeToSet(t.string1)),
					unmodifiableSet(tokenizer.tokenizeToSet(t.string2)));
		}
		return transformed;
	}

	@Override
	protected Set<String> getEmpty() {
		return emptySet();
	}
}
