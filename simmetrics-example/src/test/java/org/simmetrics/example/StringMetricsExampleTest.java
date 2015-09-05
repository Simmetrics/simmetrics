/*
 * #%L
 * Simmetrics Examples
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
package org.simmetrics.example;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


@SuppressWarnings("javadoc")
public class StringMetricsExampleTest {
	
	private static final float DELTA = 0.0001f;

	@Test
	public void example01(){
		assertEquals(0.7383f, StringMetricsExample.example01(), DELTA);
	}
	
	@Test
	public void example02(){
		assertEquals(0.7777f, StringMetricsExample.example02(), DELTA);
	}
	
	@Test
	public void example03(){
		assertEquals(0.7356f, StringMetricsExample.example03(), DELTA);
	}

}
