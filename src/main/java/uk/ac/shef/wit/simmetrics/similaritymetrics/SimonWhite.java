package uk.ac.shef.wit.simmetrics.similaritymetrics;

import java.util.ArrayList;

import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserQGram2;
import uk.ac.shef.wit.simmetrics.tokenisers.TokeniserWordQGram;

/**
 * Implementation taken from <a
 * href="http://www.catalysoft.com/articles/StrikeAMatch.html" >How to Strike a
 * Match</a>
 * 
 * The intention is that by considering adjacent characters I take account not
 * only of the characters, but also of the character ordering in the original
 * string, since each character pair contains a little information about the
 * original ordering." Let me explain the algorithm by comparing the two strings
 * 'France' and 'French'. First, I map them both to their upper case characters
 * (making the algorithm insensitive to case differences), then split them up
 * into their character pairs:" FRANCE: {FR, RA, AN, NC, CE}" FRENCH: {FR, RE,
 * EN, NC, CH}" Then I work out which character pairs are in both strings. In
 * this case the intersection is {FR, NC}. Now, I would like to express my
 * finding as a numeric metric that reflects the size of the intersection
 * relative to the sizes of the original strings. If pairs(x) is the function
 * that generates the pairs of adjacent letters in a string, then my numeric
 * metric of similarity is:" The similarity between two strings s1 and s2 is
 * twice the number of character pairs that are common to both strings divided
 * by the sum of the number of character pairs in the two strings. (The vertical
 * bars in the formula mean ?size of?.) Note that the formula rates completely
 * dissimilar strings with a similarity value of 0, since the size of the
 * letter-pair intersection in the numerator of the fraction will be zero. On
 * the other hand, if you compare a (non-empty) string to itself, then the
 * similarity is 1. For our comparison of 'FRANCE' and 'FRENCH', the metric is
 * computed as follows:" Given that the values of the metric always lie between
 * 0 and 1, it is also very natural to express these values as percentages. For
 * example, the similarity between 'FRANCE' and 'FRENCH' is 40%. From now on, I
 * will express similarity values as percentages rounded to the nearest whole
 * number.
 * 
 * @author Simon White
 */
public class SimonWhite extends AbstractStringMetric   {

	private final float ESTIMATEDTIMINGCONST = 0.00000034457142857142857142857142857146f;

	private final TokeniserWordQGram tokeniserWordQGram = new TokeniserWordQGram(new TokeniserQGram2());

	public float getSimilarityTimingEstimated(String string1, String string2) {

		final float str1Length = string1.length();
		final float str2Length = string2.length();
		return (str1Length + str2Length)
				* ((str1Length + str2Length) * ESTIMATEDTIMINGCONST);
	}

	public float getSimilarity(String string1, String string2) {
		final ArrayList<String> pairs1 = tokeniserWordQGram
				.tokenizeToArrayList(string1);
		final ArrayList<String> pairs2 = tokeniserWordQGram
				.tokenizeToArrayList(string2);

		int union = pairs1.size() + pairs2.size();
		
		if(union == 0){
			return 0.0f;
		}
		
		int intersection = 0;
		for (String pair : pairs1) {
			if (pairs2.remove(pair)) {
				intersection++;
			}
		}

		return new Float((2.0 * intersection) / union).floatValue();

	}

	@Override
	public float getUnNormalisedSimilarity(String string1, String string2) {
		return getSimilarity(string1, string2);
	}

}
