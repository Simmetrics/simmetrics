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
