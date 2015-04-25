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

package uk.ac.shef.wit.simmetrics.wordhandlers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Package: uk.ac.shef.wit.simmetrics.wordhandlers
 * Description: GenericStopTermHandler implements a generic stopword handler.
 * Date: 19-Apr-2004 Time: 14:22:00
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public final class GenericStopTermHandler implements InterfaceTermHandler {

	private static final long serialVersionUID = -4642022688792329430L;
	/**
     * the wordset the stopword handler uses.
     */
    private final Set<String> wordSet = new HashSet<String>();

    /**
     * adds a stopWord to the intewrface.
     * @param termToAdd the stopWord to add
     */
    public void addWord(final String termToAdd) {
        wordSet.add(termToAdd);
    }

    /**
     * displays the stopWordHandler method.
     *
     * @return the stopWordHandler method
     */
    public final String getShortDescriptionString() {
        return "GenericStopTermHandler";
    }

    /**
     * removes the given stopword from the list.
     * @param termToRemove the stopword term to remove
     */
    public void removeWord(final String termToRemove) {
        wordSet.remove(termToRemove);
    }

    /**
     * gets the number of stopwords in the list.
     * @return the number of stopwords in the list
     */
    public int getNumberOfWords() {
        return wordSet.size();
    }

    /**
     * isStopWord determines if a given term is a stop word or not.
     * @param termToTest the term to test
     * @return true if a stopword false otherwise.
     */
    public boolean isWord(final String termToTest) {
        return wordSet.contains(termToTest);
    }

    /**
     * gets the stopwords as a stringBuffer.
     * @return an empty StringBuffer
     */
    public StringBuffer getWordsAsBuffer() {
        final StringBuffer outputBuffer = new StringBuffer();
        outputBuffer.append(Arrays.toString(wordSet.toArray()));
        return outputBuffer;
    }
}
