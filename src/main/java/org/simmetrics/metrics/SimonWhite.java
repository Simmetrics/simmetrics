/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package org.simmetrics.metrics;

import java.util.ArrayList;
import java.util.List;

import org.simmetrics.TokenListMetric;

/**
 * Idea taken from <a
 * href="http://www.catalysoft.com/articles/StrikeAMatch.html">How to Strike a
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
 * @author mpkorstanje
 */
public class SimonWhite implements TokenListMetric {

	public float compare(List<String> pairs1, List<String> b) {

		ArrayList<String> pairs2 = new ArrayList<>(b);
		int union = pairs1.size() + pairs2.size();

		if (union == 0) {
			return 0.0f;
		}

		// Count elements in the list intersection.
		// Elements are counted only once in both lists.
		// E.g. the intersection of [ab,ab,ab] and [ab,ab,ac,ad] is [ab,ab].
		int intersection = 0;
		for (String pair : pairs1) {
			if (pairs2.remove(pair)) {
				intersection++;
			}
		}

		return 2.0f * intersection / union;

	}

	@Override
	public String toString() {
		return "SimonWhite";
	}

}
