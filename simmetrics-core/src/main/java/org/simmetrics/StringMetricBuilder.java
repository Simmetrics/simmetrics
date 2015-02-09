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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.simmetrics.simplifiers.PassThroughSimplifier;
import org.simmetrics.simplifiers.Simplifier;
import org.simmetrics.simplifiers.SimplifyingSimplifier;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.utils.CachingSimplifier;
import org.simmetrics.utils.CachingTokenizer;
import org.simmetrics.utils.CompositeSimplifier;
import org.simmetrics.utils.CompositeStringMetric;
import org.simmetrics.utils.CompositeTokenListMetric;
import org.simmetrics.utils.CompositeTokenSetMetric;
import org.simmetrics.utils.FilteringTokenizer;
import org.simmetrics.utils.CompositeTokenizer;
import org.simmetrics.utils.TokenizingTokenizer;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * Convenience tool to build string metrics.
 * 
 * <p>
 * String metrics build this way supports a general work flow that consists of
 * simplification, tokenization and comparison. Where applicable it should be
 * possible to construct a similarity metric with any simplifier, tokenizer. It
 * is also possible to add a cache for repeat operations.
 * 
 * <pre>
 * <code>
 * {@code
 * 	StringMetric metric = new StringMetricBuilder()
 * 			.with(new CosineSimilarity<String>())
 * 			.addSimplifier(new NonWordCharacterSimplifier())
 * 			.addSimplifier(new CaseSimplifier.Lower())
 * 			.setTokenizer(new WhitespaceTokenizer()).build();
 * }
 * </code>
 * </pre>
 * 
 * <h2>Simplification</h2>
 * 
 * Simplification increases the effectiveness of a metric by removing noise. The
 * process maps a a complex string such as
 * <code>Chilpéric II son of Childeric II</code> to a simpler format
 * <code>chilperic ii son of childeric ii</code>. This allows string from
 * different sources to be compared in the same normal form.
 * 
 * <p>
 * Simplification can be done by any class implementing the {@link Simplifier}
 * interface.
 * 
 * <h2>Tokenization</h2>
 * 
 * Tokenization cuts up a string into tokens e.g. <code>[chilperic, ii, son, of,
 * childeric, ii]</code>. Tokenization can also be done recursively
 * <code>[ch,hi,il,il,lp,pe,er,ri,ic, ii, so,on, of, ch,hi,il,ld,de,er,ri,ic, ii]</code>
 * 
 * <p>
 * The method of tokenization changes the effectiveness of a metric depending on
 * the context. A whitespace tokenizer might be more useful to measure
 * similarity between large bodies of texts whiles a q-gram tokenizer will work
 * more effectively for matching words.
 * 
 * <p>
 * Tokenization is required for all metrics that work on collections of tokens
 * rather then whole strings. These are {@link ListMetric}s and
 * {@link SetMetric}s
 * 
 * <p>
 * Tokenization can be done by any class implementing the
 * {@link TokenizingTokenizer} interface.
 * 
 * <h2>Measurement</h2>
 * 
 * Once simplified and possibly tokenized two strings can be compared. The exact
 * way which is determined by a metric. Metrics can either work on lists, sets
 * or directly work on strings. Any class implementing {@link StringMetric},
 * &nbsp;{@link ListMetric} or {@link SetMetric} can be used.
 * 
 * @author mpkorstanje
 *
 */
public class StringMetricBuilder {
	/**
	 * Starts building a metric with a string metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public CompositeStringMetricBuilder with(StringMetric metric) {
		return new CompositeStringMetricBuilder(metric);
	}

	/**
	 * Starts building a metric with a list metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public CompositeListMetricBuilder with(ListMetric<String> metric) {
		return new CompositeListMetricBuilder(metric);

	}

	/**
	 * Starts building a metric with a set metric.
	 * 
	 * @param metric
	 *            the metric to use as a base
	 * @return a builder for fluent chaining
	 */
	public CompositeSetMetricBuilder with(SetMetric<String> metric) {
		return new CompositeSetMetricBuilder(metric);

	}

	/**
	 * Convenience tool to add simplification and caching support to a string
	 * metric.
	 * 
	 * @author mpkorstanje
	 *
	 */
	public abstract class SimplyfingBuilder<T> {

		protected final T metric;

		protected Simplifier simplifier = new PassThroughSimplifier();

		SimplyfingBuilder(T metric) {
			checkNotNull(metric);
			this.metric = metric;
		}

		void setSimplifier(Simplifier simplifier) {
			this.simplifier = simplifier;
		}

		public abstract SimplifierChainBuilder simplify(Simplifier simplifier);

	}

	public final class CompositeStringMetricBuilder extends
			SimplyfingBuilder<StringMetric> {

		public CompositeStringMetricBuilder(StringMetric metric) {
			super(metric);
		}

		/**
		 * Adds a simplifier to the metric.
		 * 
		 * @param simplifier
		 *            a simplifier to add
		 * @return this for fluent chaining
		 */
		@Override
		public StringSimplifierChainBuilder simplify(Simplifier simplifier) {
			return new StringSimplifierChainBuilder(this, simplifier);
		}

		/**
		 * Builds a metric with the simplifiers and cache.
		 * 
		 * @return a metric
		 */
		public StringMetric build() {
			return new CompositeStringMetric(metric, simplifier);
		}

	}

	public abstract class CollectionMetricBuilder<T> extends
			SimplyfingBuilder<T> {

		protected Tokenizer tokenizer;

		CollectionMetricBuilder(T metric) {
			super(metric);
			// TODO Auto-generated constructor stub
		}

		void setTokenizer(Tokenizer tokenizer) {
			checkNotNull(tokenizer);
			this.tokenizer = tokenizer;
		}

		public TokenizingChainBuilder tokenize(Tokenizer tokenizer) {
			return new TokenizingChainBuilder(this, tokenizer);
		}

		@Override
		public TokenSimplifierChainBuilder<T> simplify(Simplifier simplifier) {
			return new TokenSimplifierChainBuilder<>(this, simplifier);
		}

		abstract StringMetric build();

	}

	/**
	 * Convenience tool to create a string metric from a {@link ListMetric} .
	 * Supports simplification and caching.
	 * 
	 * @author mpkorstanje
	 *
	 */
	public final class CompositeListMetricBuilder extends
			CollectionMetricBuilder<ListMetric<String>> {

		CompositeListMetricBuilder(ListMetric<String> metric) {
			super(metric);
		}

		@Override
		CompositeTokenListMetric build() {
			return new CompositeTokenListMetric(metric, simplifier, tokenizer);
		}
	}

	/**
	 * Convenience tool to create a string metric from a {@link SetMetric} .
	 * Supports simplification and caching.
	 * 
	 * @author mpkorstanje
	 *
	 */
	public final class CompositeSetMetricBuilder extends
			CollectionMetricBuilder<SetMetric<String>> {

		CompositeSetMetricBuilder(SetMetric<String> metric) {
			super(metric);
		}

		@Override
		public CompositeTokenSetMetric build() {
			return new CompositeTokenSetMetric(metric, simplifier, tokenizer);
		}

	}

	public final class TokenSimplifierChainBuilder<T> extends
			SimplifierChainBuilder {

		private final CollectionMetricBuilder<T> builder;

		public TokenSimplifierChainBuilder(CollectionMetricBuilder<T> builder,
				Simplifier simplifier) {
			super(simplifier);

			this.builder = builder;

		}

		@Override
		public TokenSimplifierChainBuilder<T> simplify(Simplifier simplifier) {
			super.simplify(simplifier);
			return this;
		}

		public TokenizingChainBuilder tokenize(Tokenizer tokenizer) {
			builder.setSimplifier(innerBuild());
			return builder.tokenize(tokenizer);
		}

		@Override
		public TokenSimplifierChainBuilder<T> setCache(
				SimplifyingSimplifier cache) {
			super.setCache(cache);
			return this;
		}
		
		@Override
		public TokenSimplifierChainBuilder<T> setCache(int initialCapacity,
				int maximumSize) {
			super.setCache(initialCapacity, maximumSize);
			return this;
		}
		
		@Override
		public TokenSimplifierChainBuilder<T> setCache() {
			super.setCache();
			return this;
		}
		
		

	}

	public abstract class SimplifierChainBuilder {

		private static final int CACHE_SIZE = 2;

		private final List<Simplifier> simplifiers = new ArrayList<>();

		private SimplifyingSimplifier cache;

		public SimplifierChainBuilder(Simplifier simplifier) {
			checkNotNull(simplifier);
			this.simplifiers.add(simplifier);
		}

		public SimplifierChainBuilder simplify(Simplifier simplifier) {
			simplifiers.add(simplifier);
			return this;
		}

		public SimplifierChainBuilder setCache(SimplifyingSimplifier cache) {
			Preconditions.checkNotNull(cache);
			this.cache = cache;
			return this;
		}

		public SimplifierChainBuilder setCache(int initialCapacity,
				int maximumSize) {
			return setCache(new CachingSimplifier(initialCapacity, maximumSize));
		}

		public SimplifierChainBuilder setCache() {
			return setCache(CACHE_SIZE, CACHE_SIZE);
		}

		Simplifier innerBuild() {
			Simplifier s;
			if (simplifiers.size() == 1) {
				s = simplifiers.get(0);
			} else {
				s = new CompositeSimplifier(simplifiers);
			}

			if (cache != null) {
				cache.setSimplifier(s);
				s = cache;
			}

			return s;
		}

	}

	public final class StringSimplifierChainBuilder extends
			SimplifierChainBuilder {

		private final CompositeStringMetricBuilder builder;

		public StringSimplifierChainBuilder(CompositeStringMetricBuilder builder,
				Simplifier simplifier) {
			super(simplifier);
			this.builder = builder;
		}

		@Override
		public StringSimplifierChainBuilder simplify(Simplifier simplifier) {
			super.simplify(simplifier);
			return this;
		}

		@Override
		public StringSimplifierChainBuilder setCache(SimplifyingSimplifier cache) {
			super.setCache(cache);
			return this;
		}
		
		@Override
		public StringSimplifierChainBuilder setCache(int initialCapacity,
				int maximumSize) {
			super.setCache(initialCapacity, maximumSize);
			return this;
		}
		
		@Override
		public StringSimplifierChainBuilder setCache() {
			super.setCache();
			return this;
		}

		public StringMetric build() {
			builder.setSimplifier(innerBuild());
			return builder.build();
		}

	}

	public final class TokenizingChainBuilder {
		private static final int CACHE_SIZE = 2;

		private final CollectionMetricBuilder<?> builder;

		private Tokenizer tokenizer;

		private TokenizingTokenizer cache;

		public TokenizingChainBuilder(CollectionMetricBuilder<?> builder,
				Tokenizer tokenizer) {

			checkNotNull(tokenizer);

			this.builder = builder;
			this.tokenizer = tokenizer;

		}

		public TokenizingChainBuilder tokenize(Tokenizer tokenizer) {
			Preconditions.checkNotNull(tokenizer);
			this.tokenizer = new CompositeTokenizer(this.tokenizer, tokenizer);
			return this;
		}

		public TokenizingChainBuilder filter(Predicate<String> predicate) {
			Preconditions.checkNotNull(predicate);
			this.tokenizer = new FilteringTokenizer(this.tokenizer, predicate);
			return this;
		}

		public TokenizingChainBuilder setCache(TokenizingTokenizer cache) {
			Preconditions.checkNotNull(cache);
			this.cache = cache;
			return this;
		}
		
		public TokenizingChainBuilder setCache(){
			return setCache(CACHE_SIZE,CACHE_SIZE);
		}

		private TokenizingChainBuilder setCache(int initialCapacity, int maximumSize) {
			return setCache(new CachingTokenizer(initialCapacity,maximumSize));
		}

		private Tokenizer innerBuild() {

			if (cache != null) {
				cache.setTokenizer(tokenizer);
				return cache;
			} else {
				return tokenizer;
			}
		}

		public StringMetric build() {
			builder.setTokenizer(innerBuild());
			return builder.build();
		}

	}

}
