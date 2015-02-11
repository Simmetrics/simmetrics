package org.simmetrics;

import org.simmetrics.metrics.ChapmanLengthDeviation;
import org.simmetrics.metrics.ChapmanMeanLength;
import org.simmetrics.metrics.ChapmanOrderedNameCompoundSimilarity;
import org.simmetrics.metrics.JaroWinkler;
import org.simmetrics.metrics.MongeElkan;
import org.simmetrics.simplifiers.SoundexSimplifier;
import org.simmetrics.tokenizers.WhitespaceTokenizer;

/**
 * Utility class for StringMetrics.
 *
 * <p>
 * Consists of metrics created by Sam Chapman. All metrics are setup with
 * sensible defaults, to customize metrics use {@link StringMetricBuilder}.
 * <p>
 * The available metrics are:
 * 
 * <ul>
 * <li>Length Deviation
 * <li>Matching Soundex
 * <li>Mean Length
 * <li>Ordered Name Compound Similarity
 * </ul>
 * 
 * @author M.P. Korstanje
 */
public class Chapman {

	public StringMetric lengthDeviation() {
		return new ChapmanLengthDeviation();
	}

	public StringMetric matchingSoundex() {
		return new StringMetricBuilder().with(
				new MongeElkan(new StringMetricBuilder().with(new JaroWinkler())
						.simplify(new SoundexSimplifier())
						.build()))
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

	public StringMetric meanLength() {
		return new ChapmanMeanLength();
	}

	public StringMetric orderedNameCompoundSimilarity() {
		return new StringMetricBuilder().with(
				new ChapmanOrderedNameCompoundSimilarity())
				.tokenize(new WhitespaceTokenizer())
				.build();
	}

}
