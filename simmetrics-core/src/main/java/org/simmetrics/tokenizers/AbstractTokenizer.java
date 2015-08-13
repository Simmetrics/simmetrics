/*
 * #%L Simmetrics Core %% Copyright (C) 2014 - 2015 Simmetrics Authors %% This
 * program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/gpl-3.0.html>. #L%
 */

package org.simmetrics.tokenizers;

import java.util.HashSet;
import java.util.Set;

/**
 * Convenience tokenizer. Provides default implementation to tokenize to set.
 *
 */
public abstract class AbstractTokenizer implements Tokenizer {

	@Override
	public Set<String> tokenizeToSet(final String input) {
		return new HashSet<>(tokenizeToList(input));
	}

}
