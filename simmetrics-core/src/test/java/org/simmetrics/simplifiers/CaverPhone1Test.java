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
public class CaverPhone1Test extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new Caverphone1();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TNS111"),
				new T("James", "YMS111"),
				new T("", "111111"),
				new T("Travis", "TRFS11"),
				new T("Marcus", "MKS111"),
				new T("Ozymandias", "ASMNTS"),
				new T("Jones", "YNS111"),
				new T("Jenkins", "YNKNS1"),
				new T("Trevor", "TRF111"),
				new T("Marinus", "MRNS11"),
		};
	}

}
