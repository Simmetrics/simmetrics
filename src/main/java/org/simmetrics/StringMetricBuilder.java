package org.simmetrics;

import org.simmetrics.metrics.CompositeStringMetric;
import org.simmetrics.metrics.CompositeTokenListMetric;
import org.simmetrics.metrics.CompositeTokenSetMetric;
import org.simmetrics.metrics.SimplifyingSimplifier;
import org.simmetrics.simplifier.PassThroughSimplifier;
import org.simmetrics.simplifier.Simplifier;
import org.simmetrics.tokenisers.Tokenizer;
import org.simmetrics.tokenisers.TokenizingTokenizer;

import com.google.common.base.Preconditions;

public class StringMetricBuilder {

	public SimplyfingMetricBuilder setMetric(StringMetric metric) {
		return new SimplyfingMetricBuilder(metric);
	}

	public TokenListMetricBuilder setMetric(TokenListMetric metric) {
		return new TokenListMetricBuilder(metric);

	}

	public TokenSetMetricBuilder setMetric(TokenSetMetric metric) {
		return new TokenSetMetricBuilder(metric);

	}

	public class SimplyfingMetricBuilder {

		private final StringMetric metric;

		private Simplifier simplifier = new PassThroughSimplifier();

		private SimplifyingSimplifier stringCache;

		public SimplyfingMetricBuilder(StringMetric metric) {
			Preconditions.checkNotNull(metric);
			this.metric = metric;
		}

		public SimplyfingMetricBuilder setSimplifier(Simplifier simplifier) {
			Preconditions.checkNotNull(simplifier);
			this.simplifier = simplifier;
			return this;
		}

		public SimplyfingMetricBuilder setCache(SimplifyingSimplifier cache) {
			Preconditions.checkNotNull(cache);
			this.stringCache = cache;
			return this;
		}

		public StringMetric build() {
			if (simplifier instanceof PassThroughSimplifier) {
				return metric;
			}

			Simplifier simplifier = this.simplifier;

			if (stringCache != null) {
				stringCache.setSimplifier(simplifier);
				simplifier = stringCache;
			}

			return new CompositeStringMetric(metric, simplifier);

		}
	}

	public class TokenListMetricBuilder {

		private final TokenListMetric metric;

		private Simplifier simplifier = new PassThroughSimplifier();

		private Tokenizer tokenizer;

		private TokenizingTokenizer tokenCache;

		private SimplifyingSimplifier stringCache;

		public TokenListMetricBuilder(TokenListMetric metric) {
			this.metric = metric;
		}

		public TokenListMetricBuilder setSimplifier(Simplifier simplifier) {
			Preconditions.checkNotNull(simplifier);
			this.simplifier = simplifier;
			return this;
		}

		public TokenListMetricBuilder setTokeninzer(Tokenizer tokenizer) {
			Preconditions.checkNotNull(tokenizer);
			this.tokenizer = tokenizer;
			return this;
		}

		public TokenListMetricBuilder setCache(TokenizingTokenizer cache) {
			Preconditions.checkNotNull(cache);
			this.tokenCache = cache;
			return this;
		}

		public TokenListMetricBuilder setCache(SimplifyingSimplifier cache) {
			Preconditions.checkNotNull(cache);
			this.stringCache = cache;
			return this;
		}

		public StringMetric build() {
			Preconditions.checkNotNull(tokenizer,
					"A tokenizer must be set to build a tokenizing metric");

			Simplifier simplifier = this.simplifier;

			if (stringCache != null) {
				stringCache.setSimplifier(simplifier);
				simplifier = stringCache;
			}

			Tokenizer tokenizer = this.tokenizer;

			if (tokenCache != null) {
				tokenCache.setTokenizer(tokenizer);
				tokenizer = tokenCache;
			}

			return new CompositeTokenListMetric(metric, simplifier, tokenizer);

		}
	}

	public class TokenSetMetricBuilder {

		private final TokenSetMetric metric;

		private Simplifier simplifier = new PassThroughSimplifier();

		private Tokenizer tokenizer;

		private TokenizingTokenizer tokenCache;

		private SimplifyingSimplifier stringCache;

		public TokenSetMetricBuilder(TokenSetMetric metric) {
			this.metric = metric;
		}

		public TokenSetMetricBuilder setSimplifier(Simplifier simplifier) {
			this.simplifier = simplifier;
			return this;
		}

		public TokenSetMetricBuilder setTokeninzer(Tokenizer tokenizer) {
			this.tokenizer = tokenizer;
			return this;
		}

		public TokenSetMetricBuilder setCache(TokenizingTokenizer cache) {
			Preconditions.checkNotNull(cache);
			this.tokenCache = cache;
			return this;
		}

		public TokenSetMetricBuilder setCache(SimplifyingSimplifier cache) {
			Preconditions.checkNotNull(cache);
			this.stringCache = cache;
			return this;
		}

		public StringMetric build() {
			Preconditions.checkNotNull(tokenizer,
					"A tokenizer must be set to build a tokenizing metric");

			Simplifier simplifier = this.simplifier;

			if (stringCache != null) {
				stringCache.setSimplifier(simplifier);
				simplifier = stringCache;
			}

			Tokenizer tokenizer = this.tokenizer;

			if (tokenCache != null) {
				tokenCache.setTokenizer(tokenizer);
				tokenizer = tokenCache;
			}

			return new CompositeTokenSetMetric(metric, simplifier, tokenizer);

		}

	}

}
