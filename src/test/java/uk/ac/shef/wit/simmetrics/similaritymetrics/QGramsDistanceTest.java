/*
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

package uk.ac.shef.wit.simmetrics.similaritymetrics;

public class QGramsDistanceTest extends InterfaceStringMetricTest {

	@Override
	public InterfaceStringMetric getMetric() {
		return new QGramsDistance();
	}

	@Override
	public T[] getTests() {
		return new T[] {
				new T(0.7857f, "test string1", "test string2"),
				new T(0.7059f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.6667f, "a b c d", "a b c e"),
				new T(0.6250f, "Healed", "Sealed"),
				new T(0.4706f, "Healed", "Healthy"),
				new T(0.5333f, "Healed", "Heard"),
				new T(0.5000f, "Healed", "Herded"),
				new T(0.2857f, "Healed", "Help"),
				new T(0.1429f, "Healed", "Sold"),
				new T(0.2857f, "Healed", "Help"),
				new T(0.6111f, "Sam J Chapman", "Samuel John Chapman"),
				new T(0.7500f, "Sam Chapman", "S Chapman"),
				new T(0.1818f, "John Smith", "Samuel John Chapman"),
				new T(0.0000f, "John Smith", "Sam Chapman"),
				new T(0.0000f, "John Smith", "Sam J Chapman"),
				new T(0.0000f, "John Smith", "S Chapman"),
				new T(0.7042f, "Web Database Applications", "Web Database Applications with PHP & MySQL"),
				new T(0.5250f, "Web Database Applications", "Creating Database Web Applications with PHP and ASP"),
				new T(0.5432f, "Web Database Applications", "Building Database Applications on the Web Using PHP3"),
				new T(0.5476f, "Web Database Applications", "Building Web Database Applications with Visual Studio 6"),
				new T(0.4308f, "Web Database Applications", "Web Application Development With PHP"),
				new T(0.4138f, "Web Database Applications", "WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.0000f, "Web Database Applications", "Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.0000f, "Web Database Applications", "How to Find a Scholarship Online"),
				new T(0.4262f, "Web Aplications", "Web Database Applications with PHP & MySQL"),
				new T(0.3429f, "Web Aplications", "Creating Database Web Applications with PHP and ASP"),
				new T(0.3099f, "Web Aplications", "Building Database Applications on the Web Using PHP3"),
				new T(0.2973f, "Web Aplications", "Building Web Database Applications with Visual Studio 6"),
				new T(0.4727f, "Web Aplications", "Web Application Development With PHP"),
				new T(0.2453f, "Web Aplications", "WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.0000f, "Web Aplications", "Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.0000f, "Web Aplications", "How to Find a Scholarship Online"),
		};
	}

}

