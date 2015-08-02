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

package org.simmetrics.metrics.costfunctions;

import org.simmetrics.metrics.functions.Gap;
import org.simmetrics.metrics.functions.LinearGap;

@SuppressWarnings("javadoc")
public final class LinearGapTest extends GapCostTest {
	@Override
	public Gap getCost() {
		return new LinearGap(-0.42f);
	}

	@Override
	public T[] getTests() {
		final String testString = "hello world AAAAAAA BBB ABCDEF this is a test";
		return new T[] { new T(-2.1000f, testString, 0, 6),
				new T(-0.0000f, testString, 3, 4),
				new T(-1.2600f, testString, 13, 17),
				new T(-0.8400f, testString, 19, 22),
				new T(-2.1000f, testString, 23, 29),
				new T(-0.0000f, testString, 5, 6), };
	}

}