/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.simmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class MathTest {

	@Test
	public void max3float() {	
		assertEquals(0.3f, Math.max(0.1f, 0.2f, 0.3f), 0.0);
		assertEquals(0.31f, Math.max(0.31f, 0.2f, 0.3f), 0.0);
		assertEquals(0.5f, Math.max(0.1f, 0.5f, 0.3f), 0.0);
		assertEquals(0.5f, Math.max(-10.1f, 0.5f, -0.3f), 0.0);
	}

	@Test
	public void max3int() {
		assertEquals(5, Math.max(-10, 5, 3));
		assertEquals(10, Math.max(10, 5, 3));
		assertEquals(13, Math.max(-10, 5, 13));
	}

	@Test
	public void max4float() {
		assertEquals(36.9f, Math.max(10.1f, 5.45f, -3.12f, 36.9f), 0.0);
		assertEquals(10.1f, Math.max(10.1f, 5.45f, -3.12f, 6.9f), 0.0);
		assertEquals(-3.12f, Math.max(-10.1f, -5.45f, -3.12f, -36.9f),
				0.0);
		assertEquals(25.45f, Math.max(10.1f, 25.45f, -3.12f, 16.9f), 0.0);
	}

	@Test
	public void max4int() {
		assertEquals(5, Math.max(-10, 5, 3, 1));
		assertEquals(10, Math.max(10, 5, 3, 1));
		assertEquals(13, Math.max(-10, 5, 13, 1));
		assertEquals(15, Math.max(-10, 5, 3, 15));
	}

	@Test
	public void min3float() {
		assertEquals(5.45f, Math.min(10.1f, 5.45f, 13.12f), 0.0);
		assertEquals(0.1f, Math.min(0.1f, 5.45f, 13.12f), 0.0);
		assertEquals(-3.12f, Math.min(10.1f, 5.45f, -3.12f), 0.0);
	}

	@Test
	public void min3int() {
		assertEquals(-10, Math.min(-10, 5, 13));
		assertEquals(-13, Math.min(-10, 5, -13));
		assertEquals(5, Math.min(10, 5, 13));
	}

	@Test
	public void min4int() {
		assertEquals(-10, Math.min(-10, 5, 3, 1));
		assertEquals(-5, Math.min(10, -5, 13, 1));
		assertEquals(3, Math.min(10, 5, 3, 15));
		assertEquals(1, Math.min(10, 5, 3, 1));

	}

	@Test
	public void min4float() {
		assertEquals(-10.1f, Math.min(-10.1f, 5.45f, -3.12f, 36.9f), 0.0);
		assertEquals(-5.45f, Math.min(10.1f, -5.45f, -3.12f, 6.9f), 0.0);
		assertEquals(3.12f, Math.min(10.1f, 5.45f, 3.12f, 36.9f),0.0);
		assertEquals(16.9f, Math.min(100.1f, 25.45f, 23.12f, 16.9f), 0.0);
	}

}
