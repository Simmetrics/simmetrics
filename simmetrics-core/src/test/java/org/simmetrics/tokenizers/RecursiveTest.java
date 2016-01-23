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

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizers.Recursive;

import static java.util.Arrays.asList;
import static org.simmetrics.tokenizers.Tokenizers.qGram;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class RecursiveTest extends TokenizerTest {
	
	@Override
	protected T[] getTests() {
		return new T[] {
					new T("Mouse", 
							"Mo", "ou", "ou", "us", "ou", "us", "us","se"), 
					new T("")
				};
	}

	@Override
	protected Tokenizer getTokenizer() {
		return new Recursive(asList(qGram(5), qGram(4), qGram(3), qGram(2)));
	}
	
	@Test(expected = NullPointerException.class)
	public void shouldThrowForListContainingNull() {
		new Recursive(asList(whitespace(), (Tokenizer) null));
	}
	
	public void shouldCopyListOfTokenizers() {
		List<Tokenizer> tokenizerList = asList(whitespace());
		Recursive tokenizer = new Recursive(tokenizerList);
		assertThat(tokenizer.getTokenizers(), is(sameInstance(tokenizer.getTokenizers())));
		assertThat(tokenizer.getTokenizers(), is(not(sameInstance(tokenizerList))));
	}
	

}
