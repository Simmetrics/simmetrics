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

import org.simmetrics.simplifiers.NonDiacritics;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings({"javadoc","deprecation"})
public class NonDiacriticsTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new NonDiacritics();
	}

	@Override
	protected T[] getTests() {
		return new T[] {
				new T("Chilpéric II son of Childeric II",
						"Chilperic II son of Childeric II"),
				new T("The 11th Hour", "The 11th Hour"), 
				new T("", ""), };
	}

}
