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

import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.WordCharacters;

@SuppressWarnings({ "javadoc", "deprecation" })
public class WordCharactersTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new WordCharacters();
	}

	@Override
	protected T[] getTests() {
		return new T[] { new T("##", "  "),
				new T("The ##th Hour", "The   th Hour"), new T("", "") };
	}

}
