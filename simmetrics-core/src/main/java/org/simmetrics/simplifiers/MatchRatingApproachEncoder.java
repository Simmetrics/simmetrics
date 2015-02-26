package org.simmetrics.simplifiers;

/**
 * Match Rating Approach Phonetic Algorithm Developed by <CITE>Western Airlines</CITE> in 1977.
 *
 * This class is immutable and thread-safe.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Match_rating_approach">Wikipedia - Match Rating Approach</a>
 * @see org.apache.commons.codec.language.MatchRatingApproachEncoder
 */
public class MatchRatingApproachEncoder implements Simplifier {

	private final org.apache.commons.codec.language.MatchRatingApproachEncoder simplifier = new org.apache.commons.codec.language.MatchRatingApproachEncoder();

	@Override
	public String simplify(String input) {
		return simplifier.encode(input);
	}

}
