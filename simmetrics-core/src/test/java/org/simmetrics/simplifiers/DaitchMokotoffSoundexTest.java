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
public class DaitchMokotoffSoundexTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new DaitchMokotoffSoundex();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "365490"),
				new T("James", "164000"),
				new T("", "000000"),
				new T("Travis", "397400"),
				new T("Marcus", "694400"),
				new T("Ozymandias", "046634"),
				new T("Jones", "164000"),
				new T("Jenkins", "165640"),
				new T("Trevor", "397900"),
				new T("Marinus", "696400"),
		};
	}

}
