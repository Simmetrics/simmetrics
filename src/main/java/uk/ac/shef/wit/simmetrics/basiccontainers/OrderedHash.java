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
import java.util.Hashtable;
import java.util.Vector;

/**
 * Title: OrderedHash
 * Description: class that implements an ordered hash allowing keys and values to be linked in an ordered hash sequence.
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */
final class OrderedHash extends Hashtable implements Serializable {

    /**
     * private store for the ordered Vector of keys.
     */
    private final Vector orderVector = new Vector();

    /**
     * private store for the ordered value vector.
     */
    private final Vector valueVector = new Vector();

    /**
     * default constructor.
     */
    public OrderedHash() {
        super();
    }

    /**
     * puts an object into the ordered hash.
     * @param key
     * @param value
     * @return the object put in the hash
     */
    public Object put(final Object key, final Object value) {
        if (!orderVector.contains(key)) {
            orderVector.add(key);
        }

        if (value instanceof Vector) {
            valueVector.addAll((Vector) value);
        } else {
            valueVector.add(value);
        }

        return super.put(key, value);
    }

    /**
     * gets the ordered key vector.
     * @return vector of ordered keys (from the order in which they where added)
     */
    public Vector getOrderedKeys() {
        return orderVector;
    }

    /**
     * gets the ordered value vector.
     * @return vector of ordered vlaues (from the order in which they where added)
     */
    public Vector getOrderedValues() {
        return valueVector;
    }
}