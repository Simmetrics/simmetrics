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

package uk.ac.shef.wit.simmetrics.utils;

/**
 * Description: file utility.
 *
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 * @version 1.1
 */

import java.io.*;

/**
 * utility class that loads files and returns there contents as strings.
 */
public final class FileLoader {

    /**
     * constructor default empty.
     */
    public FileLoader() {
    }

    /**
     * gets the contents of a given file and returns it as a string.
     *
     * @param file the file to open
     * @return a string of the contents of the file or "" if empty
     *         or not present
     */
    public static StringBuffer fileToStringBuffer(final String file) {
        return fileToString(new File(file));
    }

    /**
     * gets an object from a file.
     *
     * @param file the file to get from
     * @return the object from the file
     */
    public static Object fileToObject(final String file) {
        return fileToObject(new File(file));
    }

    /**
     * private call to load a file and return its content as a string.
     *
     * @param f a file for which to load its content
     * @return a string containing the files contents or "" if empty
     *         or not present
     */
    public static StringBuffer fileToString(final File f) {
        final StringBuffer fileString = new StringBuffer("");

        try {
            final FileReader fileR = new FileReader(f);
            final BufferedReader bfr = new BufferedReader(fileR);
            String line;

            while (true) {
                line = bfr.readLine();
                if (line == null) {
                    break;
                }

                if (line.length() != 0) {
                    fileString.append(line);
                    fileString.append('\n');
                }
            }

        } catch (Exception e) {
            //todo should this be empty
        }

        return fileString;
    }

    /**
     * private call to load a file and return its content as an object.
     *
     * @param f a file for which to load its content
     * @return an Object containing the files contents or null if
     *         not present
     */
    public static Object fileToObject(final File f) {
        if (f == null) {
            return null;
        } else {
            if (!f.exists()) {
                return null;
            } else {
                Object fileObject = null;
                try {
                    FileInputStream fis = new FileInputStream(f);
                    //check is unecessary
/*
                    if (fis == null) {
                        return null;
                    }
*/
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    //check is unecessary
/*
                    if (ois == null) {
                        return null;
                    }
*/
                    fileObject = ois.readObject();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return fileObject;
            }
        }
    }

    /**
     * writes the given object content to a given filename.
     *
     * @param content the object content to write
     * @param fname   the filename to write it into
     * @return true if successful false otherwsie
     */
    public static boolean objectToFile(final Object content, String fname) {
        try {
            FileOutputStream fos = new FileOutputStream(fname);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(content);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * writes the given string content to a given filename.
     *
     * @param content the string content to write
     * @param fname   the filename to write it into
     * @return true if successful false otherwsie
     */
    public static boolean stringToFile(final StringBuffer content, String fname) {
        try {
            final FileWriter fileW = new FileWriter(new File(fname));
            final BufferedWriter bfw = new BufferedWriter(fileW);
            bfw.write(content.toString());
            bfw.flush();
            bfw.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}