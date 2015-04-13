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

package uk.ac.shef.wit.simmetrics.metrichandlers;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.BlockDistance;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;

/**
 * uk.ac.shef.wit.simmetrics.metrichandlers.MetricHandler defines a class able to detail information about the metrics available.
 *
 * @author Sam Chapman <a href="mailto:s.chapman@dcs.shef.ac.uk>Email</a>
 * @version 1.0
 *          Date: 21-Jul-2005
 *          Time: 15:47:20
 *          <p/>
 *          Copyright Sam Chapman 21-Jul-2005 <a href="http://www.dcs.shef.ac.uk/~sam/">website</a>
 */
public class MetricHandler {

    /**
     * private string metric used to get the details of the resource.
     */
    private static AbstractStringMetric aMetric = new BlockDistance();

    /**
     * gets the metrics available in the jar or filepath.
     *
     * @return an ArrayList of Strings containing metric names
     */
    public static List<String> GetMetricsAvailable() {

        List<String> outputVect = new ArrayList<String>();

        Class<?> tosubclass = null;
        try {
            tosubclass = Class.forName("uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String pckgname = "uk.ac.shef.wit.simmetrics.similaritymetrics";

        // Translate the package name into an absolute path
        String name = pckgname;
        if (!name.startsWith("/")) {
            name = "/" + name;
        }
        name = name.replace('.', '/');

        // Get a File object for the package
        URL url = aMetric.getClass().getResource(name);
        // URL url = tosubclass.getResource(name);
        // URL url = ClassLoader.getSystemClassLoader().getResource(name);
        //System.out.println(name + "->" + url);

        // Happens only if the jar file is not well constructed, i.e.
        // if the directories do not appear alone in the jar file like here:
        //
        //          meta-inf/
        //          meta-inf/manifest.mf
        //          commands/                  <== IMPORTANT
        //          commands/Command.class
        //          commands/DoorClose.class
        //          commands/DoorLock.class
        //          commands/DoorOpen.class
        //          commands/LightOff.class
        //          commands/LightOn.class
        //          RTSI.class
        //
        if (url == null) return null;

        File directory = new File(url.getFile());

        // New code
        // ======
        if (directory.exists()) {
            // Get the list of the files contained in the package
            String[] files = directory.list();
            for (String file : files) {

                // we are only interested in .class files
                if (file.endsWith(".class")) {
                    // removes the .class extension
                    String classname = file.substring(0, file.length() - 6);
                    try {
                        // Try to create an instance of the object
                        Object o = Class.forName(pckgname + "." + classname).newInstance();
                        assert tosubclass != null;
                        if (tosubclass.isInstance(o)) {
                            outputVect.add(classname);
                        }
                    } catch (ClassNotFoundException cnfex) {
                        System.err.println(cnfex);
                    } catch (InstantiationException iex) {
                        // We try to instanciate an interface
                        // or an object that does not have a
                        // default constructor
                    } catch (IllegalAccessException iaex) {
                        // The class is not public
                    }
                }
            }
        } else {
            try {
                // It does not work with the filesystem: we must
                // be in the case of a package contained in a jar file.
                JarURLConnection conn = (JarURLConnection) url.openConnection();
                String starts = conn.getEntryName();
                JarFile jfile = conn.getJarFile();
                Enumeration<JarEntry> e = jfile.entries();
                while (e.hasMoreElements()) {
                    ZipEntry entry = e.nextElement();
                    String entryname = entry.getName();
                    if (entryname.startsWith(starts)
                            && (entryname.lastIndexOf('/') <= starts.length())
                            && entryname.endsWith(".class")) {
                        String classname = entryname.substring(0, entryname.length() - 6);
                        if (classname.startsWith("/"))
                            classname = classname.substring(1);
                        classname = classname.replace('/', '.');
                        try {
                            // Try to create an instance of the object
                            Object o = Class.forName(classname).newInstance();
                            assert tosubclass != null;
                            if (tosubclass.isInstance(o)) {
                                outputVect.add(classname.substring(classname.lastIndexOf('.') + 1));
                            }
                        } catch (ClassNotFoundException cnfex) {
                            System.err.println(cnfex);
                        } catch (InstantiationException iex) {
                            // We try to instanciate an interface
                            // or an object that does not have a
                            // default constructor
                        } catch (IllegalAccessException iaex) {
                            // The class is not public
                        }
                    }
                }
            } catch (IOException ioex) {
                System.err.println(ioex);
            }
        }

        return outputVect;
    }

    /**
     * creates a metric with a given name using reflection.
     *
     * @param metricName the <code>String</code> name of the metric to create
     * @return if a valid name the metric otherwise null
     */
    public static AbstractStringMetric createMetric(String metricName) {
        AbstractStringMetric aplugin = null;

        try {
            Class<?> metricDefinition = Class.forName("uk.ac.shef.wit.simmetrics.similaritymetrics." + metricName);
            Constructor<?> constructor = metricDefinition.getConstructor();
            aplugin = (AbstractStringMetric) constructor.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //classnot found return null
            //e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return aplugin;
    }
}
