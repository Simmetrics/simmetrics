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

package uk.ac.shef.wit.simmetrics.task;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.MongeElkan;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;
import uk.ac.shef.wit.simmetrics.utils.FileLoader;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Description: a simple single metric task.
 *
 * @author Sam Chapman, NLP Group, Sheffield Uni, UK
 *         (<a href="mailto:sam@dcs.shef.ac.uk">email</a>, <a href="http://www.dcs.shef.ac.uk/~sam/">website</a>)
 *         <p/>
 *         Date: 29-Mar-2006
 *         Time: 20:12:23
 */
public class SimpleTask implements InterfaceTask {

    public static void main(final String[] args) {
        new SimpleTask();
    }

    public SimpleTask() {
        //todo test constructor remove this when code is stabilised

        XMLEncoder e = null;
        try {
            e = new XMLEncoder(
                                      new BufferedOutputStream(
                                          new FileOutputStream("C:\\Config.xml")));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ArrayList<AbstractStringMetric> array = new ArrayList<AbstractStringMetric>();
        AbstractStringMetric metric = new MongeElkan(new Levenshtein());
//        ((MongeElkan)metric).
        array.add(metric);
        assert e != null;
        e.writeObject(array);

        e.close();
    }

    /**
     * Runs the task.
     *
     * @return a TaskResult object.
     */
    public TaskResult runTask() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Parses the task from the given uri.
     *
     * @param path the path of the task definition file
     */
    public void parseTask(String path) {
        FileLoader.fileToString(new File(path));


    }
}
