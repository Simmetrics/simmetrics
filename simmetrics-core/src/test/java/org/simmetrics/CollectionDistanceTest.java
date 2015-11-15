package org.simmetrics;

import static org.junit.Assert.fail;
import java.util.Collection;
import org.junit.Test;

@SuppressWarnings("javadoc")
public abstract class CollectionDistanceTest<V, K extends Collection<V>> extends DistanceTest<K> {

	protected static class C<V, K extends Collection<V>> extends TestCase<K> {

		public C(float similarity, K a, K b) {
			super(similarity, a, b);
		}

	}
	
	@Override
	protected abstract C<V,K>[] getTests();
	
	private void testsShouldContainCollectionWithNullVsWithouthNull(C<V, K>[] cs) {
		for (C<V,K> t : cs) {
			if (t.a.contains(null) ^ t.b.contains(null)) {
				return;
			}
		}

		fail("tests did not contain collection with null vs collection without null");
	}

	private void testIllegalArgumentException(Distance<K> metric) {
		try {
			metric.distance(getCollectionContainNull(), getEmpty());
			fail("Metric should have thrown a illegal argument exception for the first argument");
		} catch (IllegalArgumentException ignored) {
			// Ignored
		}

		try {
			metric.distance(getEmpty(), getCollectionContainNull());
			fail("Metric should have thrown a illegal argument exception for the second argument");
		} catch (IllegalArgumentException ignored) {
			// Ignored
		}

		try {
			metric.distance(getCollectionContainNull(), getCollectionContainNull());
			fail("Metric should have thrown a illegal argument exception for either argument");
		} catch (IllegalArgumentException ignored) {
			// Ignored
		}
	}

	protected abstract K getCollectionContainNull();

	@Test
	public void nullValues() {
		if (supportsNullValues()) {
			testsShouldContainCollectionWithNullVsWithouthNull(getTests());
		} else {
			testIllegalArgumentException(metric);	
		}
	}

	protected boolean supportsNullValues() {
		return true;
	}

}