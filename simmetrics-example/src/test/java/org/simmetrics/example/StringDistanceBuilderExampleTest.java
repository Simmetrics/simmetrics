/*
 * #%L
 * Simmetrics Examples
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
package org.simmetrics.example;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.simmetrics.example.StringDistanceBuilderExample;


@SuppressWarnings("javadoc")
public class StringDistanceBuilderExampleTest {
	
	private static final float DELTA = 0.0001f;
	
	@Test
	public void example00(){
		assertEquals(7.0000f, StringDistanceBuilderExample.example00(), DELTA);
	}
	
	@Test
	public void example01(){
		assertEquals(0.0000f, StringDistanceBuilderExample.example01(), DELTA);
	}
	
	@Test
	public void example02(){
		assertEquals(0.0000f, StringDistanceBuilderExample.example02(), DELTA);
	}
	
	@Test
	public void example03(){
		assertEquals(2.0000f, StringDistanceBuilderExample.example03(), DELTA);
	}
	
	@Test
	public void example04(){
		assertEquals(2.8284f, StringDistanceBuilderExample.example04(), DELTA);
	}
	
	@Test
	public void example05(){
		assertEquals(4.6904f, StringDistanceBuilderExample.example05(), DELTA);
	}
	
	@Test
	public void example06(){
		assertEquals(4.6904f, StringDistanceBuilderExample.example06(), DELTA);
	}
	
	@Test
	public void example07(){
		assertEquals(4.6904f, StringDistanceBuilderExample.example07(), DELTA);
	}
}
