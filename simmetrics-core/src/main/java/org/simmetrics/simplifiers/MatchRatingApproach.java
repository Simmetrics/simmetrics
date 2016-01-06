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
package org.simmetrics.simplifiers;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.commons.codec.language.MatchRatingApproachEncoder;

/**
 * Match Rating Approach Phonetic Algorithm Developed by <CITE>Western Airlines</CITE> in 1977.
 * <p>
 * This class is immutable and thread-safe.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Match_rating_approach">Wikipedia - Match Rating Approach</a>
 * @see org.apache.commons.codec.language.MatchRatingApproachEncoder
 * 
 * @deprecated will be removed due to a lack of a good use case
 */
@Deprecated
public final class MatchRatingApproach implements Simplifier {

	private final MatchRatingApproachEncoder simplifier = new MatchRatingApproachEncoder();

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return simplifier.encode(input);
	}
	
	@Override
	public String toString() {
		return "MatchRatingApproach";
	}

}
