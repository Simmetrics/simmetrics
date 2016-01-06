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

package org.simmetrics.tokenizers;

@SuppressWarnings("javadoc")
public class WhitespaceTest extends TokenizerTest {

	@Override
	protected Tokenizer getTokenizer() {
		return new Tokenizers.Whitespace();
	}

	@Override
	protected T[] getTests() {

		return new T[] { 
				new T(""),
				new T(" "),
				new T(" A","A"),
				new T("A B C", "A", "B", "C"),
				new T("A   B  C", "A", "B", "C"),
				new T("A\nB", "A", "B"),
				new T("A\tB", "A", "B"), 
				new T("A\t\nB", "A", "B"),
		};
	}
}
