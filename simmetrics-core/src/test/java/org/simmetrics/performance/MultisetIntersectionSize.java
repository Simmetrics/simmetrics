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
public class MultisetIntersectionSize {
	
	enum Value {
		A100(100), 
		A1k(1000),
		A10k(10*1000);

		final Multiset<String> s;

		Value(int n) {
			s = HashMultiset.create(n);
			for(int i = 0; i < n; i++){
				s.add("sheep" + i);
			}
		}
	}
	
	@Param
	Value a;

	@Param
	Value b;
	
	@Benchmark
	float compare(int reps) {

		float dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += Multisets.intersection(a.s, b.s).size();
		}
		return dummy;
	}

	public static void main(String[] args) {
		CaliperMain.main(MultisetIntersectionSize.class, args);
	}
	

}
