package uk.ac.shef.wit.simmetrics.simplifier;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class NonDiacriticSimplifier extends AbstractSimplifier {

	public String simplify(String input) {
		return DIACRITICS_AND_FRIENDS.matcher(
				Normalizer.normalize(input, Normalizer.Form.NFD))
				.replaceAll("");
	}

	public static final Pattern DIACRITICS_AND_FRIENDS = Pattern
			.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

}
