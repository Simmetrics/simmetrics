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

package org.simmetrics.simplifiers;

import java.util.Locale;

/**
 * Simplifiers for upper and lower case. Allows a locale to be set for the
 * correct transformation.
 * 
 * <p>
 * This class is immutable and thread-safe.
 *
 */
public abstract class Case implements Simplifier {

	/**
	 * Lower case simplifier. Transforms all upper case characters into their
	 * lower case equivalent.
	 * 
	 * 
	 *
	 */
	public static class Lower extends Case {

		private final Locale locale;

		/**
		 * Constructs a new Lower case simplifier that will use the given
		 * locale.
		 * 
		 * @param locale
		 *            to use in transformation
		 */
		public Lower(Locale locale) {
			this.locale = locale;
		}

		/**
		 * Constructs a new Lower case simplifier. The default locale will be
		 * used.
		 * 
		 * */
		public Lower() {
			this(Locale.getDefault());
		}

		@Override
		public String simplify(String s) {
			return s.toLowerCase(locale);
		}

		@Override
		public String toString() {
			return "Lower [locale=" + locale + "]";
		}
	}

	/**
	 * Upper case simplifier. Transforms all lower case characters into their
	 * upper case equivalent.
	 * 
	 * 
	 *
	 */
	public static class Upper extends Case {

		private final Locale locale;

		/**
		 * Constructs a new Upper case simplifier that will use the given
		 * locale.
		 * 
		 * @param locale
		 *            to use in transformation
		 */
		public Upper(Locale locale) {
			super();
			this.locale = locale;
		}

		/**
		 * Constructs a new Upper case simplifier. The default locale will be
		 * used.
		 * 
		 */
		public Upper() {
			locale = Locale.getDefault();
		}

		@Override
		public String simplify(String s) {
			return s.toUpperCase(locale);
		}

		@Override
		public String toString() {
			return "Upper [locale=" + locale + "]";
		}

	}
}
