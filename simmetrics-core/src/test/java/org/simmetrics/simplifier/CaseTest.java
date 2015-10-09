/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
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
package org.simmetrics.simplifier;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.simmetrics.simplifiers.Case;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings({"javadoc","deprecation"})
@RunWith(Enclosed.class)
public class CaseTest {
	
	public static class CaseUpper extends SimplifierTest {

		@Override
		protected Simplifier getSimplifier() {
			return new Case.Upper();
		}

		@Override
		protected T[] getTests() {
			return new T[]{
					new T("A","A"),
					new T("a","A"),
					new T("","")
			};
		}
		
		
	}
	
	public static class CaseLower extends SimplifierTest {

		@Override
		protected Simplifier getSimplifier() {
			return new Case.Lower();
		}

		@Override
		protected T[] getTests() {
			return new T[]{
					new T("A","a"),
					new T("a","a"),
					new T("","")
			};
		}
		
		
	}

}
