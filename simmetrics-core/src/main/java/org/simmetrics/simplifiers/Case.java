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

package org.simmetrics.simplifiers;

import java.util.Locale;

/**
 * Simplifiers for upper and lower case. Allows a locale to be set for the
 * correct transformation.
 * 
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @deprecated use {@link Simplifiers#toLowerCase()} and
 *             {@link Simplifiers#toUpperCase()} instead.
 */
@Deprecated
public abstract class Case implements Simplifier {

	/**
	 * Lower case simplifier. Transforms all upper case characters into their
	 * lower case equivalent.
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
		@Deprecated
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
			this.locale = locale;
		}

		/**
		 * Constructs a new Upper case simplifier. The default locale will be
		 * used.
		 * 
		 */
		public Upper() {
			this(Locale.getDefault());
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
