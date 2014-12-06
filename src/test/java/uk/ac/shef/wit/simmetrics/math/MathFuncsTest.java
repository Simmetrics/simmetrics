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

package uk.ac.shef.wit.simmetrics.math;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 23-Nov-2006
 * Time: 16:44:27
 * To change this template use File | Settings | File Templates.
 */
public class MathFuncsTest extends TestCase {
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp() {
        //create objects for testing
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
     * Tests max3 float.
     */
    public void max3float() {
//        Test test = new Test();
        assertEquals(0.3f, MathFuncs.max3(0.1f, 0.2f, 0.3f));
        assertEquals(0.31f, MathFuncs.max3(0.31f, 0.2f, 0.3f));
        assertEquals(0.5f, MathFuncs.max3(0.1f, 0.5f, 0.3f));
        assertEquals(0.5f, MathFuncs.max3(-10.1f, 0.5f, -0.3f));
    }

    /**
     * Tests max3 int.
     */
    public void max3int() {
        assertEquals(5, MathFuncs.max3(-10, 5, 3));
        assertEquals(10, MathFuncs.max3(10, 5, 3));
        assertEquals(13, MathFuncs.max3(-10, 5, 13));
    }

    /**
     * tests min3 int.
     */
    public void min3int() {
        assertEquals(-10, MathFuncs.min3(-10, 5, 13));
        assertEquals(-13, MathFuncs.min3(-10, 5, -13));
        assertEquals(5, MathFuncs.min3(10, 5, 13));
    }

    /**
     * tests min3 float.
     */
    public void min3float() {
        assertEquals(5.45f, MathFuncs.min3(10.1f, 5.45f, 13.12f));
        assertEquals(0.1f, MathFuncs.min3(0.1f, 5.45f, 13.12f));
        assertEquals(-3.12f, MathFuncs.min3(10.1f, 5.45f, -3.12f));
    }

    /**
     * tests max4 float.
     */
    public void max4float() {
        assertEquals(36.9f, MathFuncs.max4(10.1f, 5.45f, -3.12f, 36.9f));
        assertEquals(10.1f, MathFuncs.max4(10.1f, 5.45f, -3.12f, 6.9f));
        assertEquals(-3.12f, MathFuncs.max4(-10.1f, -5.45f, -3.12f, -36.9f));
        assertEquals(25.4f, MathFuncs.max4(10.1f, 25.45f, -3.12f, 16.9f));
    }

}
