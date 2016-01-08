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

import static java.util.Collections.emptySet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;

import com.google.monitoring.runtime.instrumentation.common.com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public abstract class SetMetricTest extends CollectionMetricTest<String,Set<String>>{

	protected static final class T  extends C<String,Set<String>>{
		
		public T(float similarity, Set<String> a, Set<String> b) {
			super(similarity, a,b);
		}

		public T(float similarity, String a, String b) {
			this(Tokenizers.whitespace(), similarity, a,b);
		}

		public T(Tokenizer tokenizer, float similarity, String a, String b) {
			this(similarity, tokenizer.tokenizeToSet(a),tokenizer.tokenizeToSet(b));
		}

		public T(float similarity, List<String> a, List<String> b) {
			this(similarity, new HashSet<>(a), new HashSet<>(b));
		}

	}
	
	@Override
	protected abstract T[] getTests();
	

	@Override
	protected Set<String> getEmpty() {
		return emptySet();
	}
	
	@Override
	public Set<String> getCollectionContainNull() {
		return Sets.newHashSet((String)null);
	}
}
