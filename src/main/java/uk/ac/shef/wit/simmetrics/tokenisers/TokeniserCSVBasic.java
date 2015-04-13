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
 * Description: TokeniserCSVBasic implements a simple CSV tokeniser.
 * NB(this doesn't consider embedded escaped comma's within the fields)

 * Date: 31-Mar-2004
 * Time: 15:17:07
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class TokeniserCSVBasic implements InterfaceTokeniser, Serializable {

	private static final long serialVersionUID = -6480259323180295979L;

	/**
     * stopWordHandler used by the tokenisation.
     */
    private InterfaceTermHandler stopWordHandler = new DummyStopTermHandler();

    /**
     * priavte delimitors for commas within a CSV file.
     */
    private final String delimiters = ",";

    /**
     * displays the tokenisation method.
     *
     * @return the tokenisation method
     */
    public final String getShortDescriptionString() {
        return "TokeniserCSVBasic";
    }

    /**
     * displays the delimiters used .
     *
     * @return the delimiters used
     */
    public final String getDelimiters() {
        return delimiters;
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
     * Return tokenized version of a string .
     *
     * @param input
     * @return tokenized version of a string
     */
    public final ArrayList<String> tokenizeToArrayList(final String input) {
        final ArrayList<String> returnArrayList = new ArrayList<String>();
        int curPos = 0;
        while (curPos < input.length()) {
            final char ch = input.charAt(curPos);
            if (Character.isWhitespace(ch)) {
                curPos++;
            }
            int nextGapPos = input.length();
            //check delimitors
            for (int i = 0; i < delimiters.length(); i++) {
                final int testPos = input.indexOf(delimiters.charAt(i), curPos);
                if (testPos < nextGapPos && testPos != -1) {
                    nextGapPos = testPos;
                }
            }
            //add new token
            final String term = input.substring(curPos, nextGapPos);
            if(!stopWordHandler.isWord(term) && !term.equals(" ")) {
                returnArrayList.add(term);
            }
            curPos = nextGapPos;
        }

        return returnArrayList;
    }

    /**
     * Return tokenized set of a string.
     *
     * @param input
     * @return tokenized set of a string
     */
    public Set<String> tokenizeToSet(final String input) {
        final Set<String> returnSet = new HashSet<String>();
        returnSet.addAll(tokenizeToArrayList(input));
        return returnSet;
    }
}
