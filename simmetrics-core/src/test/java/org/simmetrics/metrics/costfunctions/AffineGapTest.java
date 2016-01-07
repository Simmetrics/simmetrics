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

package org.simmetrics.metrics.costfunctions;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.metrics.functions.AffineGap;
import org.simmetrics.metrics.functions.Gap;

@SuppressWarnings("javadoc")
@RunWith(Enclosed.class)
public final class AffineGapTest {

	public static final class AffineGap1_1Over3Test extends GapCostTest {

		@Override
		protected Gap getCost() {
			return new AffineGap(-1f, -1 / 3f);
		}

		@Override
		protected T[] getTests() {
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
		protected Gap getCost() {
			return new AffineGap(-5.0f, -1.0f);
		}

		@Override
		protected T[] getTests() {
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
