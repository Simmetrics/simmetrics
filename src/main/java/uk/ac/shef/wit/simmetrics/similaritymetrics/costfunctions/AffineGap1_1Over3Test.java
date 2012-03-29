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

package uk.ac.shef.wit.simmetrics.similaritymetrics.costfunctions;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 24-Nov-2006
 * Time: 10:37:43
 * To change this template use File | Settings | File Templates.
 */
public class AffineGap1_1Over3Test extends TestCase {

    /**
     * costfunction tested.
     */
    private InterfaceAffineGapCost costFunction;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp() {
        costFunction = new AffineGap1_1Over3();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown() {
        // release objects under test here, if necessary
    }

    /**
     * Tests emptying the cart.
     */
    public void testAll() {
        final String testString = "hello world AAAAAAA BBB ABCDEF this is a test";
        assertEquals(2.6666667f, costFunction.getCost(testString, 0, 6));
        assertEquals(1.0f, costFunction.getCost(testString, 3, 4));
        assertEquals(2.0f, costFunction.getCost(testString, 13, 17));
        assertEquals(1.6666667f, costFunction.getCost(testString, 19, 22));
        assertEquals(2.6666667f, costFunction.getCost(testString, 23, 29));
        assertEquals(1.0f, costFunction.getCost(testString, 5, 6));
    }
}
