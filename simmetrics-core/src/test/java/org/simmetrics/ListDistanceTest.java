package org.simmetrics;

import static java.util.Collections.unmodifiableList;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Whitespace;

@SuppressWarnings("javadoc")
public abstract class ListDistanceTest extends DistanceTest<List<String>> {

	protected static final class T {
		protected final List<String> a;
		protected final List<String> b;
		protected final float similarity;

		public T(float similarity, List<String> a, List<String> b) {
			this.a = a;
			this.b = b;
			this.similarity = similarity;
		}

		public T(float similarity, String a, String b) {
			this(new Whitespace(), similarity, a, b);
		}

		public T(Tokenizer t, float similarity, String a, String b) {
			this(similarity, t.tokenizeToList(a), t.tokenizeToList(b));
		}
	}

	private static DistanceTest.T<List<String>>[] transformTest(T... tests) {
		@SuppressWarnings("unchecked")
		DistanceTest.T<List<String>>[] transformed = new DistanceTest.T[tests.length];
		for (int i = 0; i < tests.length; i++) {
			T t = tests[i];
			transformed[i] = new DistanceTest.T<>(t.similarity,
					unmodifiableList(t.a), unmodifiableList(t.b));
		}
		return transformed;
	}

	@Test
	public final void containsListWithNullVsListWithouthNullTest() {
		for (T t : getListTests()) {
			if (t.a.contains(null) ^ t.b.contains(null)) {
				return;
			}
		}

		fail("tests did not contain list with null vs list without null test");
	}

	@Override
	protected final List<String> getEmpty() {
		return Collections.emptyList();
	}

	protected abstract T[] getListTests();

	@Override
	protected final org.simmetrics.DistanceTest.T<List<String>>[] getTests() {
		return transformTest(getListTests());
	}

}
