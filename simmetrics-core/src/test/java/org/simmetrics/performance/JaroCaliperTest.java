/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2015 Simmetrics Authors
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.simmetrics.performance;

import org.junit.Test;
import org.simmetrics.performance.JaroCaliper.Method;
import org.simmetrics.performance.JaroCaliper.Value;

@SuppressWarnings("javadoc")
public class JaroCaliperTest {

	@Test
	public void smokeTest() {
		for (Value a : Value.values()) {
			for (Value b : Value.values()) {
				for (Method m : Method.values()) {
					JaroCaliper test = new JaroCaliper();
					test.a = a;
					test.b = b;
					test.method = m;
					test.compare(1);
				}
			}
		}
	}
}
