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

import java.util.List;
import java.util.Set;

import com.google.common.collect.Multiset;

/**
 * A tokenizer divides an input string into tokens. A tokenizer may not provide
 * a collection containing null.
 * <p>
 * It is recommend that implementations extend {@link AbstractTokenizer}
 * rather then implement this interface.
 */
public interface Tokenizer {

	/**
	 * Return tokenized version of a string as a list of tokens.
	 *
	 * @param input
	 *            input string to tokenize
	 *
	 * @return List tokenized version of a string
	 */
	public List<String> tokenizeToList(String input);

	/**
	 * Return tokenized version of a string as a multiset of tokens.
	 *
	 * @param input
	 *            input string to tokenize
	 * @return tokenized version of a string as a multiset
	 */
	public Multiset<String> tokenizeToMultiset(String input);
	
	/**
	 * Return tokenized version of a string as a set of tokens.
	 *
	 * @param input
	 *            input string to tokenize
	 * @return tokenized version of a string as a set
	 */
	public Set<String> tokenizeToSet(String input);

}
