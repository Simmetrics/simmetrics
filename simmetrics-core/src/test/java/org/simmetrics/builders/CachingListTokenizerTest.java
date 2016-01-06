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

import java.util.List;
import static org.simmetrics.builders.StringMetricBuilder.CachingListTokenizer;
import org.simmetrics.tokenizers.Tokenizer;

import com.google.common.cache.Cache;

@SuppressWarnings("javadoc")
public class CachingListTokenizerTest extends CachingTokenizerTest<List<String>> {

	@Override
	protected boolean supportsTokenizeToSet() {
		return false;
	}

	@Override
	protected boolean supportsTokenizeToMultiset() {
		return false;
	}

	@Override
	public Tokenizer getTokenizer(Cache<String, List<String>> cache, Tokenizer tokenizer) {
		return new CachingListTokenizer(cache, tokenizer);
	}
}
