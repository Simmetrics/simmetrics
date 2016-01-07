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

package org.simmetrics;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.util.List;

import org.simmetrics.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
public abstract class ListMetricTest extends CollectionMetricTest<String,List<String>> {
	
	protected static final class T extends C<String,List<String>>{
		
		public T(float similarity, List<String> a, List<String> b) {
			super(similarity, a,b);
		}
		
		public T(Tokenizer t, float similarity, String a, String b) {
			this(similarity, t.tokenizeToList(a), t.tokenizeToList(b));
		}
		
		public T(float similarity, String a, String b) {
			this(whitespace(), similarity, a, b);
		}
	}
	
	@Override
	protected abstract T[] getTests();
	
	@Override
	protected final List<String> getEmpty() {
		return emptyList();
	}
	
	@Override
	public List<String> getCollectionContainNull() {
		return asList((String)null);
	}

}
