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

package uk.ac.shef.wit.simmetrics.basiccontainers;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Package: uk.ac.shef.wit.simmetrics.basiccontainers
 * Description: SortableTermObject implements a sortable list object for term frequency.
 * Date: 15-Apr-2004
 * Time: 15:14:39
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
final class SortableTermObject<T> implements Comparator<T>, Serializable {

	private static final long serialVersionUID = -6812384536433692936L;

	/**
     * the term of the object.
     */
    private final String term;

    /**
     * the priavte frequency of the term.
     */
    private final int frequency;

    /**
     * constructor with a given term and frequency.
     * @param termToSet the term to use
     * @param frequencyToSet the frequency of the term
     */
    public SortableTermObject(final String termToSet, final int frequencyToSet) {
        term = termToSet;
        frequency = frequencyToSet;
    }

    /**
     * get the frequency.
     * @return the frequency of the term
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * get the term.
     * @return the string of the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * 	       first argument is less than, equal to, or greater than the
     *	       second.
     * @throws ClassCastException if the arguments' types prevent them from
     * 	       being compared by this Comparator.
     */
    @SuppressWarnings("unchecked")
	public int compare(final T o1, final T o2) {
        if(((SortableTermObject<T>)o1).frequency > ((SortableTermObject<T>)o2).frequency) {
            return 1;
        } else if(((SortableTermObject<T>)o1).frequency < ((SortableTermObject<T>)o2).frequency) {
            return -1;
        } else {
            return 0;
        }

    }
}
