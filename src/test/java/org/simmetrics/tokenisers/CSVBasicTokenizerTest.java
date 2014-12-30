/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance
 * Metrics, e.g. Levenshtein Distance, that provide float based similarity
 * measures between String Data. All metrics return consistent measures
 * rather than unbounded similarity scores.
 * 
 * Copyright (C) 2014  SimMetrics authors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package org.simmetrics.tokenisers;

import org.simmetrics.tokenisers.CSVBasicTokenizer;
import org.simmetrics.tokenisers.Tokenizer;

public class CSVBasicTokenizerTest extends TokeniserTest {


	@Override
	protected Tokenizer getTokenizer() {
		return new CSVBasicTokenizer();
	}
	@Override
	public T[] getTests() {

		return new T[] { 
				new T("1a,2a,3a,4a\n1b,2b,3b,4b", 
				"1a", "2a", "3a","4a", "1b", "2b", "3b", "4b"),
				new T("1a,2a,3a,4a\n1a,2a,3a,4a", 
						"1a", "2a", "3a","4a", "1a", "2a", "3a", "4a")
		};
	}

}
