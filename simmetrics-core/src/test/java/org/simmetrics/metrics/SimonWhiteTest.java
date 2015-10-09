/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
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

import static java.util.Arrays.asList;
import static org.simmetrics.tokenizers.Tokenizers.chain;
import static org.simmetrics.tokenizers.Tokenizers.filter;
import static org.simmetrics.tokenizers.Tokenizers.qGram;
import static org.simmetrics.tokenizers.Tokenizers.whitespace;

import org.simmetrics.ListMetric;
import org.simmetrics.ListMetricTest;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.tokenizers.Tokenizer;
import com.google.common.base.Predicate;

@SuppressWarnings("javadoc")
public final class SimonWhiteTest extends ListMetricTest {
	
	@Override
	protected boolean satisfiesSubadditivity(){
		return false;
	}

	static class MinimumLenght implements Predicate<String> {
		@Override
		public boolean apply(String input) {
			return input.length() >= 2;
		}

		@Override
		public String toString() {
			return "MinimumLenght";
		}
	}

	@Override
	protected ListMetric<String> getMetric() {
		return new SimonWhite<>();
	}

	private static Tokenizer getTokenizer() {
		return chain(asList(filter(
				whitespace(), new MinimumLenght()), qGram(2)));
	}

	@Override
	protected T[] getListTests() {
		final Tokenizer t = getTokenizer();
		return new T[] {
				new T(  0.5000f, asList("test", null), asList("test","string2")),
				new T(t,0.8889f, "test string1", "test string2"),
				new T(t,0.5000f, "test", "test string2"),
				new T(t,0.0000f, "", "test string2"),
				new T(t,0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(t,1.0000f, "a b c d", "a b c e"),
				new T(t,0.8000f, "Healed", "Sealed"),
				new T(t,0.5455f, "Healed", "Healthy"),
				new T(t,0.4444f, "Healed", "Heard"),
				new T(t,0.4000f, "Healed", "Herded"),
				new T(t,0.2500f, "Healed", "Help"),
				new T(t,0.0000f, "Healed", "Sold"),
				new T(t,0.2500f, "Healed", "Help"),
				new T(t,0.7273f, "Sam J Chapman", "Samuel John Chapman"),
				new T(t,0.8571f, "Sam Chapman", "S Chapman"),
				new T(t,0.2857f, "John Smith", "Samuel John Chapman"),
				new T(t,0.0000f, "John Smith", "Sam Chapman"),
				new T(t,0.0000f, "John Smith", "Sam J Chapman"),
				new T(t,0.0000f, "John Smith", "S Chapman"),
				new T(t,0.8163f, "Web Database Applications",
						"Web Database Applications with PHP & MySQL"),
				new T(t,0.7143f, "Web Database Applications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(t,0.7018f, "Web Database Applications",
						"Building Database Applications on the Web Using PHP3"),
				new T(t,0.6667f, "Web Database Applications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(t,0.5106f, "Web Database Applications",
						"Web Application Development With PHP"),
				new T(t,
						0.4878f,
						"Web Database Applications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(t,0.0909f, "Web Database Applications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(t,0.0488f, "Web Database Applications",
						"How to Find a Scholarship Online"),
				new T(t,0.5854f, "Web Aplications",
						"Web Database Applications with PHP & MySQL"),
				new T(t,0.5000f, "Web Aplications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(t,0.4898f, "Web Aplications",
						"Building Database Applications on the Web Using PHP3"),
				new T(t,0.4615f, "Web Aplications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(t,0.5641f, "Web Aplications",
						"Web Application Development With PHP"),
				new T(t,
						0.3243f,
						"Web Aplications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(t,0.0690f, "Web Aplications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(t,0.0606f, "Web Aplications",
						"How to Find a Scholarship Online") };
	}
}
