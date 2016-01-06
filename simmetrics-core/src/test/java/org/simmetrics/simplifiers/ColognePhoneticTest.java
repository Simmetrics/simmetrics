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
public class ColognePhoneticTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new ColognePhonetic();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "2687"),
				new T("James", "068"),
				new T("", ""),
				new T("Travis", "2738"),
				new T("Marcus", "6748"),
				new T("Ozymandias", "086628"),
				new T("Jones", "068"),
				new T("Jenkins", "06468"),
				new T("Trevor", "2737"),
				new T("Marinus", "6768"),
		};
	}

}
