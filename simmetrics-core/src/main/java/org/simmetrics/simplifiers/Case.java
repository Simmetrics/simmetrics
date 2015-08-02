

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
