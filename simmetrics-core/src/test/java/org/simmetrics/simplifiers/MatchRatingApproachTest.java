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
package org.simmetrics.simplifiers;

@SuppressWarnings("javadoc")
@Deprecated
public class MatchRatingApproachTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new MatchRatingApproach();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TNHSR"),
				new T("James", "JMS"),
				new T("", ""),
				new T("Travis", "TRVS"),
				new T("Marcus", "MRCS"),
				new T("Ozymandias", "OZYNDS"),
				new T("Jones", "JNS"),
				new T("Jenkins", "JNKNS"),
				new T("Trevor", "TRVR"),
				new T("Marinus", "MRNS"),
		};
	}

}
