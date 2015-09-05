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
package org.simmetrics.metrics;

import static com.google.common.base.Preconditions.checkNotNull;

import org.simmetrics.Metric;

/**
 * Identity metric that returns 1.0 when the inputs are equals, and 0.0 when
 * they're not.
 * 
 * @param <T>
 *            type of token
 */
public final class Identity<T> implements Metric<T> {

	@Override
	public float compare(T a, T b) {
		checkNotNull(a);
		checkNotNull(b);
		return a.equals(b) ? 1.0f : 0.0f;
	}

	@Override
	public String toString() {
		return "Identity";
	}

}
