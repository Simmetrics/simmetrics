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
package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.Metaphone;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class MetaphoneTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new Metaphone();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TNHS"),
				new T("James", "JMS"),
				new T("", ""),
				new T("Travis", "TRFS"),
				new T("Marcus", "MRKS"),
				new T("Ozymandias", "OSMN"),
				new T("Jones", "JNS"),
				new T("Jenkins", "JNKN"),
				new T("Trevor", "TRFR"),
				new T("Marinus", "MRNS"),
		};
	}

}
