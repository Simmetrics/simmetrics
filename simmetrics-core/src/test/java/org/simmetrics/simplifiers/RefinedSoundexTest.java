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
public class RefinedSoundexTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new RefinedSoundex();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "T6080309"),
				new T("James", "J40803"),
				new T("", ""),
				new T("Travis", "T690203"),
				new T("Marcus", "M809303"),
				
				new T("Ozymandias", "O050808603"),
				new T("Jones", "J40803"),
				new T("Jenkins", "J4083083"),
				new T("Trevor", "T690209"),
				new T("Marinus", "M8090803"),
		};
	}

}
