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
public class NysiisTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new Nysiis();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TANASA"),
				new T("James", "JAN"),
				new T("", ""),
				new T("Travis", "TRAV"),
				new T("Marcus", "MARC"),
				new T("Ozymandias", "OSYNAN"),
				new T("Jones", "JAN"),
				new T("Jenkins", "JANCAN"),
				new T("Trevor", "TRAFAR"),
				new T("Marinus", "MARAN"),
		};
	}

}
