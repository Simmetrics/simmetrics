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


package org.simmetrics.utils;

import org.simmetrics.StringMetricBuilder;
import org.simmetrics.simplifiers.Simplifier;

/**
 * A simplifier that delegates the work to another simplifier.
 * 
 * @see StringMetricBuilder
 * 
 * 
 *
 */
public interface SimplifyingSimplifier extends Simplifier, Simplifying {

	// Empty: because composite interface

}
