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

import org.simmetrics.metrics.functions.MatchMismatch;
import org.simmetrics.metrics.functions.Substitution;

@SuppressWarnings("javadoc")
public class MatchMismatchTest extends SubstitutionTest {
	
	@Override
	public Substitution getCost() {
		return new MatchMismatch(1.0f, -0.25f);
	}

	@Override
	public T[] getTests() {
		return new T[]{
				new T(1.000f, "a", 0, "a", 0),
				new T(-0.25f, "a", 0, "b", 0),
				new T(1.000f, "ab", 0, "ba", 1),
				new T(-0.25f, "ab", 1, "ba", 1),
				
		};
	}

}
