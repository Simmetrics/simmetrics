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

package org.simmetrics.simplifiers;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.asList;
import static java.util.Arrays.asList;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.simmetrics.builders.StringMetricBuilder;

import com.google.common.collect.ImmutableList;

/**
 * Construct simple simplifiers or chain multiple simplifiers into a single
 * simplifier.
 * <p>
 * The created simplifiers are immutable and thread-safe provided all their
 * components are also immutable and thread-safe.
 */
public final class Simplifiers {

	static final class ChainSimplifier implements Simplifier {

		private final List<Simplifier> simplifiers;

		ChainSimplifier(List<Simplifier> simplifiers) {
			checkArgument(!simplifiers.contains(null));
			this.simplifiers = ImmutableList.copyOf(simplifiers);
		}

		List<Simplifier> getSimplifiers() {
			return simplifiers;
		}

		@Override
		public String simplify(String input) {
			checkNotNull(input);
			String output = input;
			for (Simplifier s : simplifiers) {
				output = s.simplify(output);
			}

			return output;

		}

		@Override
		public String toString() {
			return on(" -> ").join(simplifiers);
		}
		
		
	}

	
	/**
	 * A simplifier that normalizes a string into a composed or decomposed form.
	 * <p>
	 * This class is thread-safe and immutable.
	 * 
	 * @see Normalizer
	 */
	static final class Normalize implements Simplifier {

		private final  Form form;

		public Normalize(Form form) {
			this.form = form;
		}

		@Override
		public String simplify(String input) {
			return Normalizer.normalize(input, form);
		}

		@Override
		public String toString() {
			return "Normalize[" + form + "]";
		}

		Form getForm() {
			return form;
		}
	}
	
	/**
	 * A simplifier that removes diacritics.
	 * <p>
	 * This class is thread-safe and immutable.
	 */
	static final class RemoveDiacritics implements Simplifier {

		private static final Pattern DIACRITICS_AND_FRIENDS = Pattern
				.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

		RemoveDiacritics() {
		}

		/**
		 * Simplifies the input string by removing all diacritics.
		 * <p>
		 * The input string is transformed to canonical decomposition. After
		 * which any characters matching the regex
		 * <code>\p{InCombiningDiacriticalMarks}\p{IsLm}\p{IsSk}]+</code> are
		 * removed. The resulting string will be in canonical decomposition
		 * form.
		 * <p>
		 * 
		 * @return the input string in canonical decomposition form without
		 *         diacritics
		 *
		 */
		@Override
		public String simplify(String input) {
			return DIACRITICS_AND_FRIENDS.matcher(
					Normalizer.normalize(input, Normalizer.Form.NFD))
					.replaceAll("");
		}

		@Override
		public String toString() {
			return "RemoveDiacritics";
		}

	}

	static final class ReplaceAll implements Simplifier {
		private final Pattern pattern;

		private final String repplacement;

		public ReplaceAll(Pattern pattern, String replacement) {
			checkNotNull(replacement);
			checkNotNull(pattern);
			this.pattern = pattern;
			this.repplacement = replacement;
		}

		@Override
		public String simplify(String input) {
			return pattern.matcher(input).replaceAll(repplacement);
		}

		@Override
		public String toString() {
			return "Replace [" + pattern + " -> '" + repplacement + "' ]";
		}
	}

	static final class ToLowerCase implements Simplifier {

		private final Locale locale;

		ToLowerCase(Locale locale) {
			this.locale = locale;
		}

		@Override
		public String simplify(String s) {
			return s.toLowerCase(locale);
		}

		@Override
		public String toString() {
			return "ToLowerCase [locale=" + locale + "]";
		}
	}

	static final class ToUpperCase implements Simplifier {

		private final Locale locale;

		ToUpperCase(Locale locale) {
			this.locale = locale;
		}

		@Override
		public String simplify(String s) {
			return s.toUpperCase(locale);
		}

		@Override
		public String toString() {
			return "ToUpperCase [locale=" + locale + "]";
		}
	}

	/**
	 * Constructs a new chain of simplifiers. Applies the simplifiers in order.
	 * 
	 * @param simplifiers
	 *            a non-empty list of simplifiers
	 * @return a new composite simplifier
	 * 
	 * @see StringMetricBuilder
	 */
	public static Simplifier chain(List<Simplifier> simplifiers) {
		if (simplifiers.size() == 1) {
			return simplifiers.get(0);
		}
		return new ChainSimplifier(flatten(simplifiers));
	}

	/**
	 * Constructs a new chain of simplifiers. Applies the simplifiers in order.
	 * 
	 * @param simplifier
	 *            the first simplifier
	 * @param simplifiers
	 *            the others
	 * @return a new composite simplifier
	 * 
	 * @see StringMetricBuilder
	 */
	public static Simplifier chain(Simplifier simplifier,
			Simplifier... simplifiers) {
		checkArgument(simplifier != null);
		if (simplifiers.length == 0) {
			return simplifier;
		}

		return chain(asList(simplifier, simplifiers));
	}

	private static List<Simplifier> flatten(List<Simplifier> simplifiers) {
		final List<Simplifier> flattend = new ArrayList<>(simplifiers.size());

		for (Simplifier s : simplifiers) {
			if (s instanceof ChainSimplifier) {
				// Simplifiers controls the creation of chain simplifiers
				// all chain simplifiers are flat so we don't have
				// to flatten recursively
				final ChainSimplifier c = (ChainSimplifier) s;
				flattend.addAll(c.getSimplifiers());
			} else {
				flattend.add(s);
			}
		}

		return flattend;
	}
	/**
	 * Returns a simplifier that normalizes the input into a composed or 
	 * decomposed form
	 * 
	 * @see Normalizer
	 * 
	 * @param form
	 *            the form to normalize to
	 * 
	 * @return a simplifier that remove parts from the input
	 */
	public static Simplifier normalize(Form form) {
		return new Normalize(form);
	}
	
	/**
	 * Returns a simplifier that removes every subsequence of the input that
	 * matches the regex.
	 * 
	 * @see Matcher#replaceAll(String)
	 * 
	 * @param regex
	 *            the regex to remove
	 * 
	 * @return a simplifier that remove parts from the input
	 */
	public static Simplifier removeAll(String regex) {
		return removeAll(Pattern.compile(regex));
	}

	/**
	 * Returns a simplifier that removes every subsequence of the input that
	 * matches the pattern.
	 * 
	 * @see Matcher#replaceAll(String)
	 * 
	 * @param pattern
	 *            the pattern to remove
	 * 
	 * @return a simplifier that remove parts from the input
	 */
	public static Simplifier removeAll(Pattern pattern) {
		return new ReplaceAll(pattern, "");
	}

	/**
	 * Returns a simplifier that removes diacritics.
	 * <p>
	 * The input string is transformed to the canonical decomposition form.
	 * After which any characters matching the regex
	 * <code>\p{InCombiningDiacriticalMarks}\p{IsLm}\p{IsSk}]+</code> are
	 * removed. The resulting string will be in canonical decomposition form.
	 * 
	 * @return a simplifier that removes diacritics
	 * 
	 *
	 */
	public static Simplifier removeDiacritics() {
		return new RemoveDiacritics();
	}

	/**
	 * Returns a simplifier that removes all non-word {@code [^0-9a-zA-Z]}
	 * characters.
	 * 
	 * @return a simplifier that removes all non-word characters
	 * 
	 * @see #removeAll(Pattern)
	 */
	public static Simplifier removeNonWord() {
		return removeNonWord("");
	}

	/**
	 * Returns a simplifier that removes all consecutive non-word characters
	 * {@code [^0-9a-zA-Z]+} and replaces them with the {@code replacement}.
	 * <p>
	 * 
	 * @see #removeAll(Pattern)
	 * 
	 * @param replacement
	 *            replaces the consecutive non word characters
	 * 
	 * @return a simplifier that replaces all consecutive non-word characters
	 *         with a replacement
	 * 
	 */
	public static Simplifier removeNonWord(String replacement) {
		return removeAll("\\W+");
	}

	/**
	 * Returns a simplifier that replaces every subsequence of the input that
	 * matches the regex with the given replacement string.
	 * 
	 * @see Matcher#replaceAll(String)
	 * 
	 * @param regex
	 *            the regex to replace
	 * @param replacement
	 *            the replacement string
	 * @return a simplifier that replaces a pattern in the input
	 * 
	 */
	public static Simplifier replaceAll(String regex, String replacement) {
		return replaceAll(Pattern.compile(regex), replacement);
	}

	/**
	 * Returns a simplifier that replaces every subsequence of the input that
	 * matches the pattern with the given replacement string.
	 * 
	 * @see Matcher#replaceAll(String)
	 * 
	 * @param pattern
	 *            the pattern to replace
	 * @param replacement
	 *            the replacement string
	 * @return a simplifier that replaces a pattern in the input
	 * 
	 */
	public static Simplifier replaceAll(Pattern pattern, String replacement) {
		return new ReplaceAll(pattern, replacement);
	}

	/**
	 * Returns a simplifier that replaces all individual non-word characters
	 * {@code [^0-9a-zA-Z]} with a space.
	 * 
	 * @return a simplifier that replaces all non-word characters
	 */
	public static Simplifier replaceNonWord() {
		return replaceNonWord(" ");
	}

	/**
	 * Returns a simplifier that replaces all individual non-word characters
	 * {@code [^0-9a-zA-Z]} with the {@code replacement}.
	 * 
	 * @param replacement
	 *            replaces the non word characters
	 * 
	 * @return a simplifier that replaces all non-word characters
	 */
	public static Simplifier replaceNonWord(String replacement) {
		return replaceAll("\\W", replacement);
	}

	/**
	 * Returns a simplifier that transforms all upper case characters into their
	 * lower case equivalent.
	 * <P>
	 * Uses the default locale to apply the transform.
	 * 
	 * @return a simplifier that transforms all upper case characters into their
	 *         lower case equivalent
	 */
	public static Simplifier toLowerCase() {
		return toLowerCase(Locale.getDefault());
	}

	/**
	 * Returns a simplifier that transforms all upper case characters into their
	 * lower case equivalent.
	 * 
	 * @param l
	 *            locale in which the transform is applied
	 *
	 * @return a simplifier that transforms all upper case characters into their
	 *         lower case equivalent
	 */
	public static Simplifier toLowerCase(Locale l) {
		return new ToLowerCase(l);
	}

	/**
	 * Returns a simplifier that transforms all lower case characters into their
	 * upper case equivalent.
	 * <P>
	 * Uses the default locale to apply the transform.
	 * 
	 * @return a simplifier that transforms all lower case characters into their
	 *         upper case equivalent
	 */
	public static Simplifier toUpperCase() {
		return toUpperCase(Locale.getDefault());
	}

	/**
	 * Returns a simplifier that transforms all lower case characters into their
	 * upper case equivalent.
	 * 
	 * @param l
	 *            locale in which the transform is applied
	 *
	 * @return a simplifier that transforms all upper case characters into their
	 *         lower case equivalent
	 */
	public static Simplifier toUpperCase(Locale l) {
		return new ToUpperCase(l);
	}

	private Simplifiers() {
		// Utility class
	}

}
