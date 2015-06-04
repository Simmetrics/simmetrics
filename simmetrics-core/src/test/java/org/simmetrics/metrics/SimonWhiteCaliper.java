package org.simmetrics.metrics;

import java.util.ArrayList;
import java.util.List;

import org.simmetrics.ListMetric;
import org.simmetrics.Metric;
import org.simmetrics.tokenizers.QGram;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;

public class SimonWhiteCaliper {

	enum Value {
		Shakespear("This happy breed of men"), Rumi("Sell your cleverness and buy bewilderment.");

		final List<String> s;

		Value(String s) {
			this.s = new QGram(2).tokenizeToList(s);
		}
	}

	enum Method {

		latest(new SimonWhite<String>()), v3_0_2(new SimonWhite_V_3_0_2<String>());

		final Metric<List<String>> metric;

		private Method(Metric<List<String>> metric) {
			this.metric = metric;
		}
	}

	@Param
	Value a;

	@Param
	Value b;

	@Param
	Method method;

	@Benchmark
	float compare(int reps) {
		final Metric<List<String>> m = method.metric;

		float dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += m.compare(a.s, b.s);
		}
		return dummy;
	}

	public static void main(String[] args) {
		CaliperMain.main(SimonWhiteCaliper.class, args);
	}

	public static class SimonWhite_V_3_0_2<T> implements ListMetric<T> {

		@Override
		public float compare(List<T> a, List<T> b) {

			if (a.isEmpty() && b.isEmpty()) {
				return 1.0f;
			}

			if (a.isEmpty() || b.isEmpty()) {
				return 0.0f;
			}

			// Copy for destructive list difference
			List<T> bCopy = new ArrayList<>(b);

			// Count elements in the list intersection.
			// Elements are counted only once in both lists.
			// E.g. the intersection of [ab,ab,ab] and [ab,ab,ac,ad] is [ab,ab].
			// Note: this is not the same as b.retainAll(a).size()
			int intersection = 0;
			for (T token : a) {
				if (bCopy.remove(token)) {
					intersection++;
				}
			}

			return 2.0f * intersection / (a.size() + b.size());

		}

		@Override
		public String toString() {
			return "SimonWhite";
		}

	}

}
