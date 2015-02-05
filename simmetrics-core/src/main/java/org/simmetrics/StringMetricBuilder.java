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
package org.simmetrics;

import java.util.ArrayList;
import java.util.List;

import org.simmetrics.metrics.CompositeStringMetric;
import org.simmetrics.metrics.CompositeTokenListMetric;
import org.simmetrics.metrics.CompositeTokenSetMetric;
import org.simmetrics.simplifiers.CompositeSimplifier;
import org.simmetrics.simplifiers.PassThroughSimplifier;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.SimplifyingSimplifier;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizingTokenizer;

import com.google.common.base.Preconditions;

public class StringMetricBuilder {

	public SimplyfingMetricBuilder setMetric(StringMetric metric) {
		return new SimplyfingMetricBuilder(metric);
	}

	public TokenListMetricBuilder setMetric(ListMetric<String> metric) {
		return new TokenListMetricBuilder(metric);

	}

	public TokenSetMetricBuilder setMetric(SetMetric<String> metric) {
		return new TokenSetMetricBuilder(metric);

	}

	public class SimplyfingMetricBuilder {

		private final StringMetric metric;

		private final List<Simplifier> simplifiers = new ArrayList<>();

		private SimplifyingSimplifier stringCache;

		public SimplyfingMetricBuilder(StringMetric metric) {
			Preconditions.checkNotNull(metric);
			this.metric = metric;
		}

		public SimplyfingMetricBuilder addSimplifier(Simplifier simplifier) {
			Preconditions.checkNotNull(simplifier);
			this.simplifiers.add(simplifier);
			return this;
		}

		public SimplyfingMetricBuilder setCache(SimplifyingSimplifier cache) {
			Preconditions.checkNotNull(cache);
			this.stringCache = cache;
			return this;
		}

		public StringMetric build() {
			if (simplifiers.isEmpty()) {
				return metric;
			}

			Simplifier simplifier;
			if (simplifiers.size() == 1) {
				simplifier = simplifiers.get(0);
			} else {
				CompositeSimplifier composite = new CompositeSimplifier();
				composite.setSimplifiers(simplifiers.toArray(new Simplifier[simplifiers.size()]));
				simplifier  = composite;
			}

			
			if (stringCache != null) {
				stringCache.setSimplifier(simplifier);
				simplifier = stringCache;
			}

			return new CompositeStringMetric(metric, simplifier);

		}
	}

	public abstract class TokenMetricBuilder<T> {

		private final T metric;

		private final List<Simplifier> simplifiers = new ArrayList<>();

		private Tokenizer tokenizer;

		private TokenizingTokenizer tokenCache;

		private SimplifyingSimplifier stringCache;

		public TokenMetricBuilder(T metric) {
			this.metric = metric;
		}

		public TokenMetricBuilder<T> addSimplifier(Simplifier simplifier) {
			Preconditions.checkNotNull(simplifier);
			this.simplifiers.add(simplifier);
			return this;
		}

		public TokenMetricBuilder<T> setTokenizer(Tokenizer tokenizer) {
			Preconditions.checkNotNull(tokenizer);
			this.tokenizer = tokenizer;
			return this;
		}

		public TokenMetricBuilder<T> setCache(TokenizingTokenizer cache) {
			Preconditions.checkNotNull(cache);
			this.tokenCache = cache;
			return this;
		}

		public TokenMetricBuilder<T> setCache(SimplifyingSimplifier cache) {
			Preconditions.checkNotNull(cache);
			this.stringCache = cache;
			return this;
		}

		public StringMetric build() {

			Preconditions.checkNotNull(tokenizer,
					"A tokenizer must be set to build a tokenizing metric");

			
			Simplifier simplifier;
			if(simplifiers.isEmpty()){
				simplifier = new PassThroughSimplifier();
			} else if (simplifiers.size() == 1) {
				simplifier = simplifiers.get(0);
			} else {
				CompositeSimplifier composite = new CompositeSimplifier();
				composite.setSimplifiers(simplifiers.toArray(new Simplifier[simplifiers.size()]));
				simplifier  = composite;
			}

			if (stringCache != null) {
				stringCache.setSimplifier(simplifier);
				simplifier = stringCache;
			}

			Tokenizer tokenizer = this.tokenizer;

			if (tokenCache != null) {
				tokenCache.setTokenizer(tokenizer);
				tokenizer = tokenCache;
			}

			return build(metric, simplifier, tokenizer);

		}

		protected abstract StringMetric build(T metric, Simplifier simplifier,
				Tokenizer tokenizer);
	}

	public class TokenListMetricBuilder extends
			TokenMetricBuilder<ListMetric<String>> {

		public TokenListMetricBuilder(ListMetric<String> metric) {
			super(metric);
		}

		@Override
		protected StringMetric build(ListMetric<String> metric,
				Simplifier simplifier, Tokenizer tokenizer) {
			return new CompositeTokenListMetric(metric, simplifier, tokenizer);
		}

	}

	public class TokenSetMetricBuilder extends
			TokenMetricBuilder<SetMetric<String>> {

		public TokenSetMetricBuilder(SetMetric<String> metric) {
			super(metric);
		}

		@Override
		protected StringMetric build(SetMetric<String> metric,
				Simplifier simplifier, Tokenizer tokenizer) {
			return new CompositeTokenSetMetric(metric, simplifier, tokenizer);
		}

	}

}
