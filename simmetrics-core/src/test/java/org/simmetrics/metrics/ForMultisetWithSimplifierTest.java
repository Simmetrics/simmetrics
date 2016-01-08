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

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.simmetrics.Metric;
import org.simmetrics.StringMetricTest;
import org.simmetrics.metrics.Identity;
import org.simmetrics.metrics.StringMetrics.ForMultisetWithSimplifier;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Tokenizers;

import com.google.common.collect.Multiset;

@SuppressWarnings("javadoc")
public class ForMultisetWithSimplifierTest extends StringMetricTest{

	private final Tokenizer tokenizer = Tokenizers.whitespace();
	private final Metric<Multiset<String>> metric = new Identity<>();
	private final Simplifier simplifier = Simplifiers.toLowerCase();
	
	@Override
	protected ForMultisetWithSimplifier getMetric() {
		return new ForMultisetWithSimplifier(metric, simplifier, tokenizer);
	}
	
	@Override
	protected T[] getTests() {
		return new T[]{
				new T(0.0f, "To repeat repeat is to repeat", ""),
				new T(1.0f, "To repeat repeat is to repeat", "to repeat repeat is to repeat"),
				new T(1.0f, "To repeat repeat is to repeat", "to  repeat  repeat  is  to  repeat")
		};
	}
	
	@Override
	protected boolean satisfiesCoincidence() {
		return false;
	}
	
	@Override
	protected boolean toStringIncludesSimpleClassName() {
		return false;
	}
	
	@Test
	public void shouldReturnTokenizer(){
		assertSame(tokenizer,getMetric().getTokenizer());
	}
	
	@Test
	public void shouldReturnMetric(){
		assertSame(metric, getMetric().getMetric());
	}
	
	@Test
	public void shouldReturnSimplifier(){
		assertSame(simplifier, getMetric().getSimplifier());
	}
}
