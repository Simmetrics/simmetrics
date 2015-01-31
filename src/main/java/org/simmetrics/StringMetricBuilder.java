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

	public abstract class TokenMetricBuilder<T> {

		protected final T metric;

		protected Simplifier simplifier = new PassThroughSimplifier();

		protected Tokenizer tokenizer;

		protected TokenizingTokenizer tokenCache;

		protected SimplifyingSimplifier stringCache;

		public TokenMetricBuilder(T metric) {
			this.metric = metric;
		}

		public TokenMetricBuilder<T> setSimplifier(Simplifier simplifier) {
			Preconditions.checkNotNull(simplifier);
			this.simplifier = simplifier;
			return this;
		}

		public TokenMetricBuilder<T> setTokeninzer(Tokenizer tokenizer) {
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

			return build(metric, simplifier, tokenizer);

		}

		protected abstract StringMetric build(T metric,
				Simplifier simplifier, Tokenizer tokenizer);
	}

	public class TokenListMetricBuilder extends
			TokenMetricBuilder<TokenListMetric> {

		public TokenListMetricBuilder(TokenListMetric metric) {
			super(metric);
		}

		@Override
		protected StringMetric build(TokenListMetric metric,
				Simplifier simplifier, Tokenizer tokenizer) {
			return new CompositeTokenListMetric(metric, simplifier, tokenizer);
		}

	}

	public class TokenSetMetricBuilder extends
			TokenMetricBuilder<TokenSetMetric> {

		public TokenSetMetricBuilder(TokenSetMetric metric) {
			super(metric);
		}

		@Override
		protected StringMetric build(TokenSetMetric metric,
				Simplifier simplifier, Tokenizer tokenizer) {
			return new CompositeTokenSetMetric(metric, simplifier, tokenizer);
		}

	}

}
