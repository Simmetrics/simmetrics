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

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 23-Nov-2006
 * Time: 13:15:07
 * To change this template use File | Settings | File Templates.
 */
public class TestSuite extends TestCase {
    /**
     * main constructor setting the name of the test case.
     * @param s the name of the test
     */
    public TestSuite(String s) {
        super(s);
    }

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
     * Tests SimMetric code.
     *
     * @return Test results of the test
     */
    static public junit.framework.Test testAllBasicContainers() {
        junit.framework.TestSuite newSuite = new junit.framework.TestSuite();
        //TODO add test cases here
        return newSuite;
    }

    /**
     * main method for the junit testing.
     *
      * @param args - unused
     */
    static public void main(String[] args) {
        junit.textui.TestRunner runner = new junit.textui.TestRunner();
        System.exit(
                    TestRunner.run(runner.getTest(uk.ac.shef.wit.simmetrics.basiccontainers.TestSuite.class.getName())).
                            wasSuccessful() ? 0 : 1
            );
    }
}
