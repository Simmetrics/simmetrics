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

import java.io.Serializable;

/**
 * Package: uk.ac.shef.wit.simmetrics.api
 * Description: InterfaceTermHandler defines an interface for stop word handlers.
 * Date: 19-Apr-2004
 * Time: 14:16:47
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
public interface InterfaceTermHandler extends Serializable {

    /**
     * adds a Word to the intewrface.
     * @param termToAdd the Word to add
     */
    public void addWord(String termToAdd);

    /**
     * removes the given word from the list.
     * @param termToRemove the word term to remove
     */
    public void removeWord(String termToRemove);

    /**
     * gets the short description string of the stop word handler used.
     * @return a short string details the stop word handler used
     */
    public String getShortDescriptionString();

    /**
     * gets the number of stopwords in the list.
     * @return the number of stopwords in the list
     */
    public int getNumberOfWords();

    /**
     * isStopWord determines if a given term is a word or not.
     * @param termToTest the term to test
     * @return true if a stopword false otherwise.
     */
    public boolean isWord(final String termToTest);

    /**
     * gets the words as an output string buffer.
     * @return a stringBuffer of the words in the handler
     */
    public StringBuffer getWordsAsBuffer();

}
