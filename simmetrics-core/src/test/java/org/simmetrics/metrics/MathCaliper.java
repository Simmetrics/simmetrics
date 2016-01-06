/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * #L%
 */
package org.simmetrics.metrics;

import java.io.PrintWriter;
import java.util.Set;

import org.junit.Test;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public class MathCaliper {

	enum Value {
		A1k(1000), A2k(2000), A5k(5000);

		final Multiset<String> multiset;
		final Set<String> set;
		Value(int n) {
			multiset = HashMultiset.create(n);
			set = Sets.newHashSet();
			for (int i = 0; i < n; i++) {
				multiset.add("sheep" + i);
				set.add("sheep" + i);
			}
		}
	}

	enum Method {

		GUAVA {
			@Override
			public Multiset<String> union(Multiset<String> a, Multiset<String> b) {
				return Multisets.union(a, b);
			}

			@Override
			public Multiset<String> intersection(Multiset<String> a,
					Multiset<String> b) {
				return Multisets.intersection(a, b);
			}

			@Override
			public Set<String> intersection(Set<String> a, Set<String> b) {
				return Sets.intersection(a, b);
			}
		},
		SIMMETRICS {

			@Override
			public Multiset<String> union(Multiset<String> a, Multiset<String> b) {
				return Math.union(a, b);
			}

			@Override
			public Multiset<String> intersection(Multiset<String> a,
					Multiset<String> b) {
				return Math.intersection(a, b);
			}

			@Override
			public Set<String> intersection(Set<String> a, Set<String> b) {
				return Math.intersection(a, b);
			}

		};

		public abstract Multiset<String> union(Multiset<String> a, Multiset<String> b);
		
		public abstract Multiset<String> intersection(Multiset<String> a, Multiset<String> b);

		public abstract Set<String> intersection(Set<String> a, Set<String> b);

	}

	@Param
	Value a;

	@Param
	Value b;

	@Param
	Method method;

	@Benchmark
	int unionOfMultisetsEntrySetSize(int reps) {
		final Method method = this.method;

		int dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += method.union(a.multiset, b.multiset).entrySet().size();
		}
		return dummy;
	}

	@Benchmark
	int unionOfMultisetsElementSetSize(int reps) {
		final Method method = this.method;

		int dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += method.union(a.multiset, b.multiset).elementSet().size();
		}
		return dummy;
	}
	
	@Benchmark
	int intersectionOfMultisetsEntrySetSize(int reps) {
		final Method method = this.method;

		int dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += method.intersection(a.multiset, b.multiset).entrySet().size();
		}
		return dummy;
	}

	@Benchmark
	int intersectionOfMultisetsElementSetSize(int reps) {
		final Method method = this.method;

		int dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += method.intersection(a.multiset, b.multiset).elementSet().size();
		}
		return dummy;
	}
	
	@Benchmark
	int intersectionOfSetsSize(int reps) {
		final Method method = this.method;

		int dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += method.intersection(a.set, b.set).size();
		}
		return dummy;
	}

	public static void main(String[] args) {
		CaliperMain.main(MathCaliper.class, args);
	}

	@Test
	public void dryrun() throws Exception {
		PrintWriter stdout = new PrintWriter(System.out, true);
		PrintWriter stderr = new PrintWriter(System.err, true);
		String[] args = new String[] { "--dry-run", MathCaliper.class.getName() };
		CaliperMain.exitlessMain(args, stdout, stderr);
	}

}
