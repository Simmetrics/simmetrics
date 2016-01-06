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

package org.simmetrics.builders;

import org.simmetrics.builders.StringMetricBuilder.CachingMultisetTokenizer;
import org.simmetrics.tokenizers.Tokenizer;
import com.google.common.cache.Cache;
import com.google.common.collect.Multiset;

@SuppressWarnings("javadoc")
public class CachingMultisetTokenizerTest extends CachingTokenizerTest<Multiset<String>> {

	
	
	@Override
	protected final boolean supportsTokenizeToList() {
		return false;
	}
	
	@Override
	protected boolean supportsTokenizeToSet() {
		return false;
	}

	@Override
	public Tokenizer getTokenizer(Cache<String, Multiset<String>> cache, Tokenizer tokenizer) {
		return new CachingMultisetTokenizer(cache, tokenizer);
	}
}
