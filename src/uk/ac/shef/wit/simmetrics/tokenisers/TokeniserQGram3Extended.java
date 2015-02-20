/**
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistant measures
 * rather than unbounded similarity scores.
 *
 * Copyright (C) 2005 Sam Chapman - Open Source Release v1.1
 *
 * Please Feel free to contact me about this library, I would appreciate
 * knowing quickly what you wish to use it for and any criticisms/comments
 * upon the SimMetric library.
 *
 * email:       s.chapman@dcs.shef.ac.uk
 * www:         http://www.dcs.shef.ac.uk/~sam/
 * www:         http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 *
 * address:     Sam Chapman,
 *              Department of Computer Science,
 *              University of Sheffield,
 *              Sheffield,
 *              S. Yorks,
 *              S1 4DP
 *              United Kingdom,
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package uk.ac.shef.wit.simmetrics.tokenisers;

import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;
import uk.ac.shef.wit.simmetrics.wordhandlers.DummyStopTermHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Package: uk.ac.shef.wit.simmetrics.tokenisers
 * Description: TokeniserQGram3Extended implements a tokeniser splitting the string into qGrams
 * extending beyond the ends of the string input, using padding characters.

 * Date: 06-Apr-2004
 * Time: 14:58:35
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class TokeniserQGram3Extended implements InterfaceTokeniser, Serializable {

	private static final long serialVersionUID = 2438812291988912986L;

	/**
     * stopWordHandler used by the tokenisation.
     */
    private InterfaceTermHandler stopWordHandler = new DummyStopTermHandler();

    /**
     * padding used in q gram calculation.
     */
    private final char QGRAMSTARTPADDING = '#';

    /**
     * padding used in q gram calculation.
     */
    private final char QGRAMENDPADDING = '#';

    /**
     * displays the tokenisation method.
     *
     * @return the tokenisation method
     */
    public final String getShortDescriptionString() {
        return "TokeniserQGram3Extended";
    }

    /**
     * displays the delimiters used  - ie none.
     *
     * @return the delimiters used - i.e. none ""
     */
    public final String getDelimiters() {
        return "";
    }

    /**
     * Return tokenized version of a string.
     *
     * @param input
     * @return tokenized version of a string
     */
    public final ArrayList<String> tokenizeToArrayList(final String input) {
        final ArrayList<String> returnVect = new ArrayList<String>();
        final StringBuffer adjustedString = new StringBuffer();
        adjustedString.append(QGRAMSTARTPADDING);
        adjustedString.append(QGRAMSTARTPADDING);
        adjustedString.append(input);
        adjustedString.append(QGRAMENDPADDING);
        adjustedString.append(QGRAMENDPADDING);
        int curPos = 0;
        final int length = adjustedString.length() - 2;
        while (curPos < length) {
            final String term = adjustedString.substring(curPos, curPos + 3);
            if(!stopWordHandler.isWord(term)) {
                returnVect.add(term);
            }
            curPos++;
        }

        return returnVect;
    }

    /**
     * gets the stop word handler used.
     * @return the stop word handler used
     */
    public InterfaceTermHandler getStopWordHandler() {
        return stopWordHandler;
    }

    /**
     * sets the stop word handler used with the handler given.
     * @param stopWordHandler the given stop word hanlder
     */
    public void setStopWordHandler(final InterfaceTermHandler stopWordHandler) {
        this.stopWordHandler = stopWordHandler;
    }

    /**
     * Return tokenized set of a string.
     *
     * @param input
     * @return tokenized version of a string as a set
     */
    public Set<String> tokenizeToSet(final String input) {
        final Set<String> returnSet = new HashSet<String>();
        returnSet.addAll(tokenizeToArrayList(input));
        return returnSet;
    }
}

