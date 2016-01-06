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

package org.simmetrics.metrics;

import static org.simmetrics.metrics.StringMetrics.createForListMetric;
import static org.simmetrics.metrics.StringMetrics.createForMultisetMetric;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.simmetrics.ListMetric;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.SimonWhite;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;

@SuppressWarnings("javadoc")
public class SimonWhiteCaliper {

	enum Value {
		Shakespear("Ahoy ye sailors!—friends and noblemen— Riding ‘twixt glist’ring waves so bright and blue That one cannot help but stand and marvel At the resplendence of Neptune’s kingdom And the miracle of color correction! A Band of Brothers we are not, but rather, A jambalaya of studs and starlets, Drawn from ev’ry creed and ev’ry hair-type, Selected, as if by algorithm, To inflame the hearts and body issues Of the prize’d target demographic. Anon, we join this ship—this Battleship!— With spirits high and cheekbones higher still, Our sextants fix’d upon the one truly Bankable star aboard this o’erstuffed vessel. He whose sapphire eyes and manly shoulders, Doth evoke the simple ethos of the Heartland; belied only slightly by the Rich Irish brogue that doth cling to ev’ry Consonant like so many barnacles."), 
		Rumi("For years, copying other people, I tried to know myself. From within, I couldn’t decide what to do. Unable to see, I heard my name being called. Then I walked outside.  The breeze at dawn has secrets to tell you. Don’t go back to sleep. You must ask for what you really want. Don’t go back to sleep. People are going back and forth across the doorsill where the two worlds touch. The door is round and open. Don’t go back to sleep.");

		final String s;

		Value(String s) {
			this.s =  s;
		}
	}

	enum Method {

		latest(createForMultisetMetric(new SimonWhite<String>(), whitespace())), 
		v3_0_2(createForListMetric(new SimonWhite_V_3_0_2<String>(), whitespace()));

		final StringMetric metric;

		private Method(StringMetric metric) {
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
		final StringMetric m = method.metric;

		float dummy = 0;
		for (int i = 0; i < reps; i++) {
			dummy += m.compare(a.s, b.s);
		}
		return dummy;
	}

	public static void main(String[] args) {
		CaliperMain.main(SimonWhiteCaliper.class, args);
	}

	@Test
	public void dryrun() throws Exception {
		PrintWriter stdout = new PrintWriter(System.out, true);
		PrintWriter stderr = new PrintWriter(System.err, true);
		String[] args = new String[] { "--dry-run", SimonWhiteCaliper.class.getName() };
		CaliperMain.exitlessMain(args, stdout, stderr);
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
