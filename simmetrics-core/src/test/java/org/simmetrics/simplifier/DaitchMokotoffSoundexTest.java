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

import org.simmetrics.simplifiers.DaitchMokotoffSoundex;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class DaitchMokotoffSoundexTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new DaitchMokotoffSoundex();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "365490"),
				new T("James", "164000"),
				new T("", "000000"),
				new T("Travis", "397400"),
				new T("Marcus", "694400"),
				new T("Ozymandias", "046634"),
				new T("Jones", "164000"),
				new T("Jenkins", "165640"),
				new T("Trevor", "397900"),
				new T("Marinus", "696400"),
		};
	}

}
