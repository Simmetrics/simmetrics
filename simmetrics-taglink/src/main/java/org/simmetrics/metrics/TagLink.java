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
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */

package org.simmetrics.metrics;

import java.util.*;

import org.simmetrics.StringMetric;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.WhitespaceTokenizer;

/**
 * TagLink inplements a TagLink String Metric.
 */
public class TagLink implements StringMetric {

	/**
	 * private idfMap contains the IDF weights for each token in the dataset.
	 */
	private HashMap<String, Float> idfMap;
	/**
	 * private characterBasedStringMetric is the method that measures
	 * similarity between tokens.
	 */
	private StringMetric characterBasedStringMetric;
	/**
	 * private DEFAULT_METRIC is the default method that measures similarity
	 * between tokens.
	 */
	private static final TagLinkToken DEFAULT_METRIC = new TagLinkToken();
	/**
	 * private tokenizer for tokenization of the query strings.
	 */
	private final Tokenizer tokenizer;

	/**
	 * TagLink default constructor. IDF weights are all equally weighted.
	 * Transposition constant value is 0.3
	 */
	public TagLink() {
		this(DEFAULT_METRIC);


	}

	/**
	 * TagLink constructor requires a character based string metric.
	 *
	 * @param characterBasedStringMetric
	 *            CharacterBasedStringMetric
	 */
	public TagLink(StringMetric characterBasedStringMetric) {
		this.characterBasedStringMetric = characterBasedStringMetric;
		tokenizer = new WhitespaceTokenizer();

	}

	/**
	 * TagLink constructor requires dataset data in order to compute the IDF
	 * weights. Default character based string metric is TagLinkToken.
	 *
	 * @param dataSetArray
	 *            String[]
	 */
	public TagLink(String[] dataSetArray) {
		this(dataSetArray, DEFAULT_METRIC);

	}

	/**
	 * TagLink constructor requires dataset data in order to compute the IDF
	 * weights. Also requires a character based string metric.
	 *
	 * @param dataSetArray
	 *            String[]
	 * @param characterBasedStringMetric
	 *            CharacterBasedStringMetric
	 */
	public TagLink(String[] dataSetArray,
			StringMetric characterBasedStringMetric) {
		this.characterBasedStringMetric = characterBasedStringMetric;
		tokenizer = new WhitespaceTokenizer();
		this.idfMap = getIDFMap(dataSetArray);

	}

	/**
	 * getMinStringSize count the number of characters in String array tTokens
	 * and String array uTokens and return the minimun size.
	 *
	 * @param tTokens
	 *            String[]
	 * @param uTokens
	 *            String[]
	 * @return float
	 */
	private static float getMinStringSize(String[] tTokens, String[] uTokens) {
		float tSize = 0, uSize = 0;
		for (String tToken : tTokens) {
			tSize += tToken.length();
		}
		for (String uToken : uTokens) {
			uSize += uToken.length();
		}
		return Math.min(tSize, uSize);
	}

	/**
	 * getSimilarity computes the similarity between a pair of strings T and U.
	 *
	 * @param T
	 *            String
	 * @param U
	 *            String
	 * @return float
	 */
	public float compare(String T, String U) {

		if (T.equals(U)) {
			return 1.0f;
		} else {
			List<String> tArrayList = tokenizer.tokenizeToList(T);
			List<String> uArrayList = tokenizer.tokenizeToList(U);
			String[] tTokens = tArrayList
					.toArray(new String[tArrayList.size()]), uTokens = uArrayList
					.toArray(new String[uArrayList.size()]);
			float[] tIdfArray = getIDFArray(tTokens), uIdfArray = getIDFArray(uTokens);
			return algorithm1(tTokens, uTokens, tIdfArray, uIdfArray);
		}
	}

	/**
	 * algorithm1 select the considered most appropiate token pairs and compute
	 * the sum of the selected pairs.
	 *
	 * @param tTokens
	 *            String[]
	 * @param uTokens
	 *            String[]
	 * @param tIdfArray
	 *            float[]
	 * @param uIdfArray
	 *            float[]
	 * @return float
	 */
	private float algorithm1(String[] tTokens, String[] uTokens,
			float[] tIdfArray, float[] uIdfArray) {
		ArrayList<Candidates> candidateList = obtainCandidateList(tTokens,
				uTokens, tIdfArray, uIdfArray);
		sortCandidateList(candidateList);
		float scoreValue = 0.0f;
		HashMap<Integer, Object> tMap = new HashMap<>();
		HashMap<Integer, Object> uMap = new HashMap<>();
		for (Object aCandidateList : candidateList) {
			Candidates actualCandidates = (Candidates) aCandidateList;
			Integer tPos = actualCandidates.getTPos();
			Integer uPos = actualCandidates.getUPos();
			if ((!tMap.containsKey(tPos)) && (!uMap.containsKey(uPos))) {
				scoreValue += actualCandidates.getScore();
				tMap.put(tPos, null);
				uMap.put(uPos, null);
			}
		}
		return scoreValue;
	}

	// FIXME: This causes horrible code duplication.
	// /**
	// * explainStringMetric gives a brief explanation of how the stringMetric
	// was
	// * computed.
	// *
	// * @param T String
	// * @param U String
	// * @return String
	// */
	// public String getSimilarityExplained(String T, String U) {
	// StringBuffer buff = new StringBuffer();
	// buff.append("\n\t*****TagLink String Distance*****");
	// if (T.equals(U)) {
	// buff.append("\nS(T,U)=1.0\n");
	// } else {
	// ArrayList<String> tArrayList = tokeniser.tokenizeToArrayList(T);
	// ArrayList<String> uArrayList = tokeniser.tokenizeToArrayList(U);
	// String[] tTokens = tArrayList.toArray(new String[tArrayList.size()]),
	// uTokens = uArrayList.toArray(new String[uArrayList.size()]);
	// buff.append("\nT={");
	// for (String tToken : tTokens) {
	// buff.append(tToken).append(", ");
	// }
	// buff.append("}\n");
	//
	// buff.append("U={");
	// for (String uToken : uTokens) {
	// buff.append(uToken).append(", ");
	// }
	// buff.append("}\n");
	//
	// float minStringSize = getMinStringSize(tTokens, uTokens);
	// buff.append("min(|T|,|U|)=").append(minStringSize).append("\n");
	//
	// buff.append("\nIDF weights:\n");
	// buff.append("Ti\tai(Ti)\n");
	// float[] tIdfArray = getIDFArray(tTokens),
	// uIdfArray = getIDFArray(uTokens);
	// for (int i = 0; i < tIdfArray.length; i++) {
	// buff.append(tTokens[i]).append("\t").append(round(tIdfArray[i])).append("\n");
	// }
	// buff.append("\nUj\taj(Uj)\n");
	// for (int i = 0; i < uIdfArray.length; i++) {
	// buff.append(uTokens[i]).append("\t").append(round(uIdfArray[i])).append("\n");
	// }
	// buff.append("\nScores:\n");
	// buff.append("Ti\tUj\tSij(Ti,Uj)\tIDFij(Ti,Uj)\tMRij(Ti,Uj)\tSij\n");
	// ArrayList<Candidates> candidateList = new ArrayList<Candidates>();
	// for (int t = 0; t < tTokens.length; t++) {
	// int lastTr = -1;
	// for (int u = 0, flag = 0; u < uTokens.length && flag == 0; u++) {
	// int tr = Math.abs(t - u);
	// if (lastTr >= 0 && lastTr < tr) {
	// flag = 1;
	// } else {
	// String tTok = tTokens[t], uTok = uTokens[u];
	// float innerScore = characterBasedStringMetric.getSimilarity(tTok,
	// uTok);
	// if (innerScore >= 0.0) {
	// float MR;
	// if (innerScore == 1.0) {
	// MR = tTokens[t].length();
	// } else {
	// MR = ((TagLinkToken) characterBasedStringMetric).getMatched();
	// }
	// MR = MR / minStringSize;
	// float IDF = tIdfArray[t] * uIdfArray[u],
	// weight = (IDF + MR) / 2.0f;
	// if (innerScore == 1) {
	// lastTr = tr;
	// }
	// buff.append(tTok).append("\t").append(uTok).append("\t").append(round(innerScore)).append("\t").append(round(IDF)).append("\t").append(round(MR)).append("\t").append(round(innerScore
	// * weight)).append("\n");
	// candidateList.add(new Candidates(t, u, innerScore * weight));
	// }
	// }
	// }
	// }
	// sortCandidateList(candidateList);
	//
	// // iterate the candidate list
	// buff.append("\nCommon tokens (Algorithm 1):\n");
	// buff.append("Ti\tUj\tSij*Xij\n");
	// float score = 0.0f;
	// HashMap<Integer,Object> tMap = new HashMap<Integer, Object>();
	// HashMap<Integer,Object> uMap = new HashMap<Integer, Object>();
	// for (Object aCandidateList : candidateList) {
	// Candidates actualCandidates = (Candidates) aCandidateList;
	// Integer tPos = actualCandidates.getTPos();
	// Integer uPos = actualCandidates.getUPos();
	// if ((!tMap.containsKey(tPos)) &&
	// (!uMap.containsKey(uPos))) {
	// float tokenScore = actualCandidates.getScore();
	// score += tokenScore;
	// tMap.put(tPos, null);
	// uMap.put(uPos, null);
	// buff.append(tTokens[(tPos)]).append("\t").append(uTokens[(uPos)]).append("\t").append(round(tokenScore)).append("\n");
	// }
	// }
	// buff.append("\nS(T,U)=").append(round(score)).append("\n");
	// }
	// return buff.toString();
	// }

	/**
	 * obtainCandidateList set a candidate list of pair of tokens. Sometimes it
	 * will not compute all candidate pairs in oder to reduce the computational
	 * cost.
	 *
	 * @param tTokens
	 *            String[]
	 * @param uTokens
	 *            String[]
	 * @param tIdfArray
	 *            float[]
	 * @param uIdfArray
	 *            float[]
	 * @return ArrayList
	 */
	private ArrayList<Candidates> obtainCandidateList(String[] tTokens,
			String[] uTokens, float[] tIdfArray, float[] uIdfArray) {
		ArrayList<Candidates> candidateList = new ArrayList<>();
		float minStringSize = getMinStringSize(tTokens, uTokens);
		for (int t = 0; t < tTokens.length; t++) {
			int lastTr = -1;
			for (int u = 0, flag = 0; u < uTokens.length && flag == 0; u++) {
				int tr = Math.abs(t - u);
				if (lastTr >= 0 && lastTr < tr) {
					flag = 1;
				} else {
					String tTok = tTokens[t], uTok = uTokens[u];
					float innerScore = characterBasedStringMetric.compare(tTok,
							uTok);
					if (innerScore >= 0.0f) {
						float matched;
						if (innerScore == 1.0f) {
							matched = tTokens[t].length();
						} else {
							matched = ((TagLinkToken) characterBasedStringMetric)
									.getMatched();
						}
						float weightMatched = matched / minStringSize, weightTFIDF = tIdfArray[t]
								* uIdfArray[u], weight = (weightTFIDF + weightMatched) / 2.0f;
						if (innerScore == 1.0f) {
							lastTr = tr;
						}
						candidateList.add(new Candidates(t, u, innerScore
								* weight));
					}
				}
			}
		}
		return candidateList;
	}

	/**
	 * sortCandidateList sort a list of candidate pair of tokens.
	 *
	 * @param list
	 *            ArrayList of candidates
	 */
	private static void sortCandidateList(ArrayList<Candidates> list) {
		java.util.Collections.sort(list,
				new java.util.Comparator<Candidates>() {
					public int compare(Candidates o1, Candidates o2) {
						// First sort, by score in index
						float scoreT = o1.getScore();
						float scoreU = o2.getScore();
						if (scoreU > scoreT) {
							return 1;
						}
						if (scoreU < scoreT) {
							return -1;
						}
						return 0;
					}
				});
	}

	/**
	 * getIDFArray normalize a vector of IDF weights.
	 *
	 * @param tokenArray
	 *            String[]
	 * @return float[]
	 */
	private float[] getIDFArray(String[] tokenArray) {
		int tokenArrayLength = tokenArray.length;
		float[] IDFArray = new float[tokenArrayLength];
		if (idfMap == null) {
			float cosineWeight = 1.0f / ((float) Math.sqrt(tokenArrayLength));
			for (int i = 0; i < tokenArrayLength; i++) {
				IDFArray[i] = cosineWeight;
			}
		} else {
			float sq = 0f;
			for (int i = 0; i < tokenArrayLength; i++) {
				String actualToken = tokenArray[i];
				float idfWeight = 0.0f;
				try {
					idfWeight = (idfMap.get(actualToken));
				} catch (Exception e) {
					// SAM added this as the catch was unguarded.
					e.printStackTrace();
				}
				IDFArray[i] = idfWeight;
				sq += idfWeight * idfWeight;
			}
			sq = (float) Math.sqrt(sq);
			for (int i = 0; i < tokenArrayLength; i++) {
				IDFArray[i] = IDFArray[i] / sq;
			}
		}
		return IDFArray;
	}

	@Override
	public String toString() {
		if (idfMap == null) {
			return "[TagLink_[" + characterBasedStringMetric.toString() + "]";
		} else {
			return "[TagLink_IDF_[" + characterBasedStringMetric.toString()
					+ "]";
		}

	}

	/**
	 * getIDFMap compute the IDF weights for the dataset provided.
	 *
	 * @param dataSetArray
	 *            String[]
	 */
	private HashMap<String, Float> getIDFMap(String[] dataSetArray) {
		float N = dataSetArray.length;
		HashMap<String, Float> idfMap = new HashMap<>();
		for (int row = 0; row < N; row++) {
			HashMap<String, Object> rowMap = new HashMap<>();
			HashMap<String, Float> freqMap = new HashMap<>();
			String actualRow = dataSetArray[row];
			List<String> tokenArrayList = tokenizer.tokenizeToList(actualRow);
			String[] rowArray = tokenArrayList
					.toArray(new String[tokenArrayList.size()]);
			for (String actualToken : rowArray) {
				rowMap.put(actualToken, null);

				float actualFrequency = getFrequency(actualToken, freqMap) + 1.0f;
				freqMap.put(actualToken, actualFrequency);
			}
			Collection<String> entries = rowMap.keySet();
			for (String actualToken : entries) {
				float actualFrequency = getFrequency(actualToken, idfMap) + 1.0f;
				idfMap.put(actualToken, actualFrequency);
			}

		}

		Collection<Map.Entry<String, Float>> entries = idfMap.entrySet();
		Map.Entry<String, Float> ent;
		for (Map.Entry<String, Float> entry : entries) {
			ent = entry;
			String key = ent.getKey();
			float frequency = ent.getValue();
			float idf = (float) Math.log((N / frequency) + 1.0f);
			idfMap.put(key, idf);
		}
		return idfMap;
	}

	/**
	 * getFrequency retrieve the value of the map.
	 *
	 * @param word
	 *            String
	 * @param map
	 *            Map
	 * @return float
	 */
	private static float getFrequency(String word, Map<String, Float> map) {
		Float frequency = map.get(word);
		if (frequency == null) {
			return 0;
		}
		return frequency;
	}

	/**
	 * round a float number.
	 *
	 * @param number
	 *            float
	 * @return float
	 */
	private static float round(float number) {
		int round = (int) (number * 1000.00f);
		float rest = (number * 1000.00f) - round;
		if (rest >= 0.5f) {
			round++;
		}
		return (round / 1000.00f);
	}
}

class Candidates {
	private int tPos, uPos;
	private float score;

	/**
	 * Candidates constructor. Creates an instance of a candidate string pair T
	 * and U. It requires the position of the pair in the string and the score
	 * or distance between them.
	 *
	 * @param tPos
	 *            int
	 * @param uPos
	 *            int
	 * @param score
	 *            float
	 */
	public Candidates(int tPos, int uPos, float score) {
		this.tPos = tPos;
		this.uPos = uPos;
		this.score = score;
	}

	/**
	 * getTPos, return the position of string T.
	 *
	 * @return int
	 */
	public int getTPos() {
		return tPos;
	}

	/**
	 * getUPos, return the position of string U.
	 *
	 * @return int
	 */
	public int getUPos() {
		return uPos;
	}

	/**
	 * getScore, return the score or distance between strings T and U.
	 *
	 * @return float
	 */
	public float getScore() {
		return score;
	}

}