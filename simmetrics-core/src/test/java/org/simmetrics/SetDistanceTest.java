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

import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simmetrics.tokenizers.Tokenizer;

import com.google.monitoring.runtime.instrumentation.common.com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public abstract class SetDistanceTest extends CollectionDistanceTest<String,Set<String>>{

	protected static final class T  extends C<String,Set<String>>{
		
		public T(float similarity, Set<String> a, Set<String> b) {
			super(similarity, a,b);
		}
		public T(float similarity, String a, String b) {
			this(whitespace(), similarity, a, b);
		}
		public T(float similarity, List<String> a, List<String> b) {
			this(similarity, new HashSet<>(a), new HashSet<>(b));
		}
		public T(Tokenizer t, float similarity, String a, String b) {
			this(similarity, t.tokenizeToSet(a), t.tokenizeToSet(b));
		}

	}
	
	@Override
	protected abstract T[] getTests();
	
	@Override
	protected final Set<String> getEmpty() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getCollectionContainNull() {
		return Sets.newHashSet((String)null);
	}
}
