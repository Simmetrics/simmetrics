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

package org.simmetrics.metrics;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.StringDistance;
import org.simmetrics.StringDistanceTest;
import org.simmetrics.metrics.StringDistances;

@SuppressWarnings("javadoc")
@RunWith(Enclosed.class)
public class StringDistancesTest {

	public static class CreateIdentity extends StringDistanceTest {

		@Override
		protected StringDistance getMetric() {
			return StringDistances.identity();
		}

		@Override
		protected T[] getTests() {
			return new T[] { 
				new T(1.0f, "To repeat repeat is to repeat", ""),
				new T(0.0f, "To repeat repeat is to repeat", "To repeat repeat is to repeat") };
		}
	}

	public static class CreateCosineSimilarity extends StringDistanceTest {

		@Override
		protected StringDistance getMetric() {
			return StringDistances.cosineSimilarity();
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected T[] getTests() {
			return new T[] { 
					new T(0.5000f, "test string1", "test string2"),
					new T(0.5000f, "test string1", "test string2"), 
					new T(0.2928f, "test", "test string2"),
					new T(1.0000f, "", "test string2"),
					new T(0.2500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"), 
					new T(0.2500f, "a b c d", "a b c e"), };
		}

	}

	public static class CreateDiceSimlarity extends StringDistanceTest {

		@Override
		protected StringDistance getMetric() {
			return StringDistances.dice();
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected T[] getTests() {
			return new T[] { 
					new T(0.5000f, "test string1", "test string2"), 
					new T(0.3333f, "test", "test string2"),
					new T(1.0000f, "", "test string2"), 
					new T(0.2500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.2500f, "a b c d", "a b c e"), };
		}

	}

	public static class CreateEuclideanMetric extends StringDistanceTest {

		@Override
		protected StringDistance getMetric() {
			return StringDistances.euclideanDistance();
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected T[] getTests() {
			return new T[] { 
					new T(1.4142f, "test string1", "test string2"), 
					new T(1.0000f, "test", "test string2"),
					new T(1.4142f, "", "test string2"), 
					new T(1.4142f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(1.4142f, "a b c d", "a b c e"), };
		}

	}

	public static class CreateJaccard extends StringDistanceTest {

		@Override
		protected StringDistance getMetric() {
			return StringDistances.jaccard();
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected T[] getTests() {
			return new T[] { 
					new T(0.6666f, "test string1", "test string2"), 
					new T(0.5000f, "test", "test string2"),
					new T(1.0000f, "", "test string2"), 
					new T(0.4000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.4000f, "a b c d", "a b c e"), };
		}

	}

	public static class CreateGeneralizedJaccard extends StringDistanceTest {

		@Override
		protected StringDistance getMetric() {
			return StringDistances.generalizedJaccard();
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected T[] getTests() {
			return new T[] { 
					new T(0.6666f, "test string1", "test string2"), 
					new T(0.5000f, "test", "test string2"),
					new T(1.0000f, "", "test string2"), 
					new T(0.4000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.4000f, "a b c d", "a b c e"), };
		}

	}

	public static class CreateOverlapCoefficient extends StringDistanceTest {
		@Override
		protected StringDistance getMetric() {
			return StringDistances.overlapCoefficient();
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected T[] getTests() {
			return new T[] { 
					new T(0.5000f, "test string1", "test string2"), 
					new T(0.0000f, "test", "test string2"),
					new T(1.0000f, "", "test string2"), 
					new T(0.2500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T(0.2500f, "a b c d", "a b c e"), };
		}

		@Override
		protected boolean satisfiesCoincidence() {
			return false;
		}

	}

	public static class CreateQGramsMetric extends StringDistanceTest {

		@Override
		protected StringDistance getMetric() {
			return StringDistances.qGramsDistance();
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected T[] getTests() {
			return new T[] { 
					new T( 6.0000f, "test string1", "test string2"), 
					new T(12.0000f, "test", "test string2"),
					new T(14.0000f, "", "test string2"), 
					new T(10.0000f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
					new T( 6.0000f, "a b c d", "a b c e"), };
		}

	}

	public static class CreateSimonWhite extends StringDistanceTest {

		@Override
		protected StringDistance getMetric() {
			return StringDistances.simonWhite();
		}
		
		@Override
		protected boolean toStringIncludesSimpleClassName() {
			return false;
		}
		
		@Override
		protected T[] getTests() {
			return new T[] { 
			new T(0.1111f, "test string1", "test string2"), 
			new T(0.5000f, "test", "test string2"),
			new T(1.0000f, "", "test string2"), 
			new T(0.2500f, "aaa bbb ccc ddd", "aaa bbb ccc eee") };
		}

	}



	public static class Utilities {
		//TODO: Test
		@Test
		public void blockMetric() {
			assertNotNull(StringDistances.blockDistance());
		}

	}

	public static class CreateStringDistances {
		@Test
		public void damerauLevenshtein() {
			assertNotNull(StringDistances.damerauLevenshtein());
		}

		@Test
		public void jaro() {
			assertNotNull(StringDistances.jaro());
		}

		@Test
		public void jaroWinkler() {
			assertNotNull(StringDistances.jaroWinkler());
		}

		@Test
		public void levenshtein() {
			assertNotNull(StringDistances.levenshtein());
		}
		
		@Test
		public void longestCommonSubsequence() {
			assertNotNull(StringDistances.longestCommonSubsequence());
		}
		
		@Test
		public void longestCommonSubstring() {
			assertNotNull(StringDistances.longestCommonSubstring());
		}
		
		@Test
		public void hammingDistance() {
			assertNotNull(StringDistances.hammingDistance());
		}
	}
	
}
