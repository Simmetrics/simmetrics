/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.simmetrics;

import static org.junit.Assert.fail;
import java.util.Collection;
import org.junit.Test;

@SuppressWarnings("javadoc")
public abstract class CollectionMetricTest<V, K extends Collection<V>> extends MetricTest<K> {

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

	private void testIllegalArgumentException(Metric<K> metric) {
		try {
			metric.compare(getCollectionContainNull(), getEmpty());
			fail("Metric should have thrown a illegal argument exception for the first argument");
		} catch (IllegalArgumentException ignored) {
			// Ignored
		}

		try {
			metric.compare(getEmpty(), getCollectionContainNull());
			fail("Metric should have thrown a illegal argument exception for the second argument");
		} catch (IllegalArgumentException ignored) {
			// Ignored
		}

		try {
			metric.compare(getCollectionContainNull(), getCollectionContainNull());
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
