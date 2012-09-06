/**
 * <p>Title: </p> TagLinkToken string distance
 *
 * <p>Description: </p> This is a string metric for pairs of tokens.
 * Matched character pairs are defined by Algorithm1.
 * This string distance follows notation as described in Camacho & Salhi 2006.
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Horacio Camacho
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>. (modified code to optermise update to generics and 1.5 and to fit in SimMetrics)
 *
 * email:       jhcama@essex.ac.uk
 * www:         http://privatewww.essex.ac.uk/~jhcama/
 *
 * address:     Horacio Camacho,
 *              Department of Mathematical Sciences,
 *              University of Essex,
 *              Colchester,
 *              Wivenhoe Park,
 *              CO4 3SQ
 *              United Kingdom,
 *
 * @version 1.1
 */

package uk.ac.shef.wit.simmetrics.similaritymetrics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;

public final class TagLinkToken extends AbstractStringMetric implements Serializable {

	private static final long serialVersionUID = 4986176241837760359L;
	
	private float matched;
    private float tr;
    private float tSize;
    private float uSize;
    private static final float DEF_TR = 0.3f;
    private String sA, sB, tokenT;
    private int largestIndex;

    /**
     * a constant for calculating the estimated timing cost.
     */
    private final float ESTIMATEDTIMINGCONST = 0.0001979638591117920f;


    /**
     * TagLinkToken default constructor. Instance of this class with parameter
     * gamma = 0.3
     */
    public TagLinkToken() {
        this(DEF_TR);
    }

    /**
     * TagLinkToken constrctur. Instance of this class with user specified
     * parameter.
     *
     * @param tr float
     */
    private TagLinkToken(float tr) {
        this.tr = tr;
    }


    /**
     * gets the un-normalised similarity measure of the metric for the given strings.
     *
     * @param T string T from which to test
     * @param U string U from which to test
     * @return returns the score of the similarity measure (un-normalised)
     */
    public float getUnNormalisedSimilarity(String T, String U) {
        return getSimilarity(T, U);
    }


    /**
     * getSimilarity return the a strng distance value between 0 and 1 of a pair
     * of tokens. Where 1 is the maximum similarity.
     *
     * @param T String
     * @param U String
     * @return float
     */
    public float getSimilarity(String T, String U) {
        float score;
        if (T.equals(U)) {
            matched = T.length();
            return 1.0f;
        } else {
            tSize = T.length();
            uSize = U.length();
            // let T be the largest token
            if (tSize < uSize) {
                String tmp1 = T;
                T = U;
                U = tmp1;
                float tmp2 = tSize;
                tSize = uSize;
                uSize = tmp2;
                tokenT = U;
            }
            tokenT = T;
            ArrayList<Candidates> candidateList = algorithm1(T, U);
            sortList(candidateList);
            score = getScore(candidateList);
            score = (score / tSize + score / uSize) / 2.0f;
            return winkler(score, T, U);
        }
    }

    /**
     * explainStringMetric returns an explanation of how the string distance was
     * computed.
     *
     * @param T String
     * @param U String
     * @return String
     */
    public String getSimilarityExplained(String T, String U) {
        StringBuffer buff = new StringBuffer();
        buff.append("\n****TagLinkToken****\n");
        buff.append("Ti=").append(T).append(", Uj=").append(U).append("\n");
        float score = 0.0f;
        if (T.equals(U)) {
            matched = T.length();
            buff.append("Sij=1.0");
        } else {
            tSize = T.length();
            uSize = U.length();
            // let T be the biggest token
            if (tSize < uSize) {
                String tmp1 = T;
                T = U;
                U = tmp1;
                float tmp2 = tSize;
                tSize = uSize;
                uSize = tmp2;
            }
            ArrayList<Candidates> candidateList = algorithm1(T, U);
            sortList(candidateList);
            buff.append("Common characteres:\n");
            buff.append("Ti\tUj\tSij(Ti,Uj)\n");
            matched = 0;
            HashMap<Integer,Object> tMap = new HashMap<Integer, Object>();
            HashMap<Integer,Object> uMap = new HashMap<Integer, Object>();
            for (Object aCandidateList : candidateList) {
                Candidates actualCandidates = (Candidates) aCandidateList;
                Integer tPos = actualCandidates.getTPos(),
                        uPos = actualCandidates.getUPos();
                if ((!tMap.containsKey(tPos)) &&
                        (!uMap.containsKey(uPos))) {
                    float actualScore = actualCandidates.getScore();
                    score += actualScore;
                    tMap.put(tPos, null);
                    uMap.put(uPos, null);
                    buff.append(T.charAt(tPos)).append("\t").append(U.charAt(uPos)).append("\t").append(round(actualScore)).append("\n");
                    matched++;
                }
            }
            score = (score / tSize + score / uSize) / 2.0f;
            System.out.println("score " + score);
            buff.append("Sij(T,U)=").append(round(winkler(score, T, U)));
            buff.append("\nMatched characters=").append(matched);
        }
        return buff.toString();
    }

    /**
     * getScore summ the total score of a candidate list of pair of characters.
     *
     * @param candidateList ArrayList
     * @return float
     */
    private float getScore(ArrayList<Candidates> candidateList) {
        matched = 0;
        largestIndex = -1;
        float scoreValue = 0;
        HashMap<Integer,Object> tMap = new HashMap<Integer, Object>();
        HashMap<Integer,Object> uMap = new HashMap<Integer, Object>();
        for (Object aCandidateList : candidateList) {
            Candidates actualCandidates = (Candidates) aCandidateList;
            Integer actualTPos = actualCandidates.getTPos(),
                    actualUPos = actualCandidates.getUPos();
            if ((!tMap.containsKey(actualTPos)) &&
                    (!uMap.containsKey(actualUPos))) {
                scoreValue += actualCandidates.getScore();
                tMap.put(actualTPos, null);
                uMap.put(actualUPos, null);
                if (largestIndex < (actualTPos)) {
                    largestIndex = (actualTPos);
                }
                matched++;
            }
        }
        return scoreValue;
    }

    /**
     * algorithm1 select the considered most appropiate character pairs are return
     * a list of candidates.
     *
     * @param T String
     * @param U String
     * @return ArrayList
     */
    private ArrayList<Candidates> algorithm1(String T, String U) {
        ArrayList<Candidates> candidateList = new ArrayList<Candidates>();
        int bound = (int) (1.0 / tr);
        for (int t = 0; t < T.length(); t++) {
            char chT = T.charAt(t);
            float lastTr = -1;
            for (int u = Math.max(0, t - bound), flag = 0;
                 u < Math.min(t + bound + 1, U.length()) && flag == 0; u++) {
                float tr2 = ((float) Math.abs(t - u));
                if ((lastTr >= 0.0) && (lastTr < tr2)) {
                    flag = 1;
                } else {
                    char chU = U.charAt(u);
                    float charScore = 0.0f;
                    if (chT == chU) {
                        charScore = 1.0f;
                    }
                    if (charScore > 0.0) {
                        //SAM commented out IF statement as this is always true
                        // //if (charScore == 1.0) {
                            lastTr = tr2;
                        //}
                        charScore = charScore - (tr * tr2);
                        if (charScore == 1.0) {
                            flag = 1;
                        }
                        candidateList.add(new Candidates(t, u, charScore));
                    }
                }
            }
        }
        return candidateList;
    }

    /**
     * sortList sort a candidate list by its scores.
     *
     * @param candidateList ArrayList
     */
    private void sortList(ArrayList<Candidates> candidateList) {
        java.util.Collections.sort(candidateList, new java.util.Comparator<Candidates>() {
            public int compare(Candidates o1, Candidates o2) {
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
        }
        );
    }

    /**
     * winkler scorer. Compute the Winkler heuristic as in Winkler 1999.
     *
     * @param score float
     * @param T     String
     * @param U     String
     * @return float
     */
    private float winkler(float score, String T, String U) {
        score = score + (getPrefix(T, U) * 0.1f * (1.0f - score));
        return score;
    }

    private int getPrefix(String T, String U) {
        int bound = Math.min(4, Math.min(T.length(), U.length()));
        int prefix;
        for (prefix = 0; prefix < bound; prefix++) {
            if (T.charAt(prefix) != U.charAt(prefix)) {
                break;
            }
        }
        return prefix;
    }

    /**
     * getMatched return the number of matched character. This value is requiered
     * for the MR-IDF method as proposed in Horacio & Salhi (2006)
     *
     * @return float
     */
    public float getMatched() {
        return matched;
    }

    /**
     * getTr return the contant value Gamma.
     *
     * @return float
     */
    public float getTr() {
        return tr;
    }


    /**
     * setTreshold set a new value to the constant Gamma.
     *
     * @param treshold float
     */
    public void setTreshold(float treshold) {
        tr = treshold;
    }

    /**
     * getShortDescriptionString return the name of the string metric.
     *
     * @return String
     */
    public String getShortDescriptionString() {
        return "[TagLinkToken_Tr_" + tr + "]";
    }

    public boolean splitWord(float score) {
        boolean answer = true;
        if (score == 1.0) {
            answer = false;
        } else {
            float matchedRate = matched / uSize;
            int cutUpper = ((int) tSize) - largestIndex;
            if ((largestIndex < 3) || (cutUpper < 3) || (matchedRate < 0.8) ||
                    (score < 0.7)) {
                answer = false;
            } else {
                split();
            }
        }
        return answer;
    }

    private void split() {
        sA = "";
        sB = "";
        for (int cutIndex = 0; cutIndex < tSize; cutIndex++) {
            if (cutIndex <= largestIndex) {
                sA += tokenT.charAt(cutIndex);
            } else {
                sB += tokenT.charAt(cutIndex);
            }
        }
    }

    public String getSa() {
        return sA;
    }

    public String getSb() {
        return sB;
    }

    /**
     * round a float number.
     *
     * @param number float
     * @return float
     */
    private float round(float number) {
        int round = (int) (number * 1000.00f);
        float rest = (number * 1000.00f) - round;
        if (rest >= 0.5) {
            round++;
        }
        return (round / 1000.00f);
    }

    /**
     * gets the estimated time in milliseconds it takes to perform a similarity timing.
     *
     * @param string1 string 1
     * @param string2 string 2
     * @return the estimated time in milliseconds taken to perform the similarity measure
     */
    public float getSimilarityTimingEstimated(final String string1, final String string2) {
        final float str1Length = string1.length();
        final float str2Length = string2.length();
        return (str1Length * str2Length) * ESTIMATEDTIMINGCONST;
    }

    /**
     * returns the long string identifier for the metric.
     *
     * @return the long string identifier for the metric
     */
    public String getLongDescriptionString() {
        return getShortDescriptionString();
    }


}
