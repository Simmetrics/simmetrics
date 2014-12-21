package org.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.Set;

import org.simmetrics.wordhandlers.TermHandler;

/**
 * Basic Q-Gram tokenizer for a variable Q.The Q-Gram is extended beyond the
 * length of the string with padding.
 * 
 * @author mpkorstanje
 *
 */
public class QGramExtendedTokenizer extends AbstractTokenizer {

	private final char Q_GRAM_START_PADDING = '#';
	private final char Q_GRAM_END_PADDING = '#';

	private final QGramTokenizer tokenizer;
	private final String endPadding;
	private final String startPadding;

	public QGramExtendedTokenizer(int q) {
		tokenizer = new QGramTokenizer(q);

		String startPadding = "";
		String endPadding = "";
		for (int i = 0; i < q - 1; i++) {
			startPadding += Q_GRAM_START_PADDING;
			endPadding += Q_GRAM_END_PADDING;
		}

		this.startPadding = startPadding;
		this.endPadding = endPadding;

	}

	public void setStopWordHandler(TermHandler stopWordHandler) {
		tokenizer.setStopWordHandler(stopWordHandler);
	}

	public TermHandler getStopWordHandler() {
		return tokenizer.getStopWordHandler();
	}

	public ArrayList<String> tokenizeToList(String input) {
		return tokenizer.tokenizeToList(startPadding + input + endPadding);
	}

	public String getShortDescriptionString() {
		return getClass().getSimpleName();
	}

	public Set<String> tokenizeToSet(String input) {
		return tokenizer.tokenizeToSet(startPadding + input + endPadding);
	}

}
