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

/**
 * Encodes a string into a Caverphone 1.0 value.
 * <p>
 * This is an algorithm created by the Caversham Project at the University of
 * Otago. It implements the Caverphone 1.0 algorithm.
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see <a href="http://en.wikipedia.org/wiki/Caverphone">Wikipedia -
 *      Caverphone</a>
 * @see <a
 *      href="http://caversham.otago.ac.nz/files/working/ctp150804.pdf">Caverphone
 *      2.0 specification</a>
 * @see org.apache.commons.codec.language.Caverphone1
 * 
 * @deprecated will be removed due to a lack of a good use case
 *
 */
@Deprecated
public final class Caverphone1 implements Simplifier {

	private final org.apache.commons.codec.language.Caverphone1 simplifier = new org.apache.commons.codec.language.Caverphone1();

	@Override
	public String simplify(String input) {
		checkNotNull(input);
		return simplifier.encode(input);
	}
	
	@Override
	public String toString() {
		return "Caverphone1";
	}

}
