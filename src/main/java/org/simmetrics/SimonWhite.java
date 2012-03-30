package org.simmetrics;

import java.util.ArrayList;

import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;

import uk.ac.shef.wit.simmetrics.similaritymetrics.InterfaceStringMetric;

/**
 * Implementation taken from "How to Strike a Match"
 * 
 * @author Simon White - <a
 *         href="http://www.catalysoft.com/articles/StrikeAMatch.html"
 *         >Catalysoft</a>
 */
public class SimonWhite implements InterfaceStringMetric {

	public String getShortDescriptionString() {
		return "Find out how many adjacent character pairs are contained in both strings";
	}

	public String getLongDescriptionString() {
		return "The intention is that by considering adjacent characters, "
				+ "I take account not only of the characters, but also of the character ordering "
				+ "in the original string, since each character pair contains a little information "
				+ "about the original ordering."
				+ "Let me explain the algorithm by comparing the two strings 'France' "
				+ "and 'French'. First, I map them both to their upper case characters "
				+ "(making the algorithm insensitive to case differences), then split "
				+ "them up into their character pairs:"
				+ "FRANCE: {FR, RA, AN, NC, CE}"
				+ "FRENCH: {FR, RE, EN, NC, CH}"
				+ "Then I work out which character pairs are in both strings. In this case, "
				+ "the intersection is {FR, NC}. Now, I would like to express my finding as a numeric "
				+ "metric that reflects the size of the intersection relative to the sizes of "
				+ "the original strings. If pairs(x) is the function that generates the pairs "
				+ "of adjacent letters in a string, then my numeric metric of similarity is:"
				+ "The similarity between two strings s1 and s2 is twice the number of "
				+ "character pairs that are common to both strings divided by the sum of the number of character pairs in the two strings. (The vertical bars in the formula mean ?size of?.) Note that the formula rates completely dissimilar strings with a similarity value of 0, since the size of the letter-pair intersection in the numerator of the fraction will be zero. On the other hand, if you compare a (non-empty) string to itself, then the similarity is 1. For our comparison of 'FRANCE' and 'FRENCH', the metric is computed as follows:"
				+ "Given that the values of the metric always lie between 0 and 1, it is also very natural to "
				+ "express these values as percentages. For example, the similarity between 'FRANCE' "
				+ "and 'FRENCH' is 40%. From now on, I will express similarity values as percentages, "
				+ "rounded to the nearest whole number.";
	}

	public long getSimilarityTimingActual(String string1, String string2) {
		StopWatch stopWatch = new LoggingStopWatch("getSimilarityTimingActual");
		this.getSimilarity(string1, string2);
		stopWatch.stop();
		return stopWatch.getElapsedTime();
	}

	public float getSimilarityTimingEstimated(String string1, String string2) {
		return this.getSimilarityTimingActual(string1, string2);
	}

	public float getSimilarity(String string1, String string2) {
		ArrayList<String> pairs1 = wordLetterPairs(string1.toUpperCase());
		ArrayList<String> pairs2 = wordLetterPairs(string2.toUpperCase());
		int intersection = 0;
		int union = pairs1.size() + pairs2.size();
		for (int i = 0; i < pairs1.size(); i++) {
			Object pair1 = pairs1.get(i);
			for (int j = 0; j < pairs2.size(); j++) {
				Object pair2 = pairs2.get(j);
				if (pair1.equals(pair2)) {
					intersection++;
					pairs2.remove(j);
					break;
				}
			}
		}
		return new Float((2.0 * intersection) / union).floatValue();
	}

	public String getSimilarityExplained(String string1, String string2) {
		return this.getLongDescriptionString();
	}

	/** @return an array of adjacent letter pairs contained in the input string */
	private String[] letterPairs(String str) {
		int numPairs = str.length() - 1;
		String[] pairs = new String[numPairs];
		for (int i = 0; i < numPairs; i++) {
			pairs[i] = str.substring(i, i + 2);
		}
		return pairs;
	}

	/** @return an ArrayList of 2-character Strings. */
	private ArrayList<String> wordLetterPairs(String str) {
		ArrayList<String> allPairs = new ArrayList<String>();
		// Tokenize the string and put the tokens/words into an array
		String[] words = str.split("\\s");
		// For each word
		for (int w = 0; w < words.length; w++) {
			// Find the pairs of characters
			String[] pairsInWord = letterPairs(words[w]);
			for (int p = 0; p < pairsInWord.length; p++) {
				allPairs.add(pairsInWord[p]);
			}
		}
		return allPairs;
	}
}
