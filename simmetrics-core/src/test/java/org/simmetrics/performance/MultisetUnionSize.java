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
package org.simmetrics.performance;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

@SuppressWarnings("javadoc")
public class MultisetUnionSize {
	
	enum Value {
		A1k(1000),
		A2k(2000),
		A5k(5000);

		final Multiset<String> s;

		Value(int n) {
			s = HashMultiset.create(n);
			for(int i = 0; i < n; i++){
				s.add("sheep" + i);
			}
		}
	}
	
	
	enum Method {

		entrySetSize {
			@Override
			public int size(Multiset<String> a, Multiset<String> b) {
				return Multisets.union(a, b).entrySet().size();
			}
		}, elementSetSize {

			@Override
			public int size(Multiset<String> a, Multiset<String> b) {
				return Multisets.union(a, b).elementSet().size();
			}
		
		} ;
		

		public abstract int size(Multiset<String> a, Multiset<String> b);
	}
	
	@Param
	Value a;

	@Param
	Value b;
	
	@Param
	Method method;
	
	@Benchmark
	float compare(int reps) {
		final Method method = this.method;

		float dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += method.size(a.s, b.s);
		}
		return dummy;
	}

	public static void main(String[] args) {
		CaliperMain.main(MultisetUnionSize.class, args);
	}
	

}
