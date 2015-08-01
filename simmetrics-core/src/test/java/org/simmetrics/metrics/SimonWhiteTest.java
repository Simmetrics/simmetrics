/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics;

import static java.util.Arrays.asList;
import static org.simmetrics.tokenizers.Tokenizers.chain;
import static org.simmetrics.tokenizers.Tokenizers.filter;

import org.simmetrics.ListMetric;
import org.simmetrics.ListMetricTest;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.Whitespace;

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
				new Whitespace(), new MinimumLenght()), new QGram(2)));
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
