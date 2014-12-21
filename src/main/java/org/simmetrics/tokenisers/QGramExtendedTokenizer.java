package org.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.Set;

import org.simmetrics.wordhandlers.TermHandler;

import com.google.common.base.Strings;

/**
 * Basic Q-Gram tokenizer for a variable Q.The Q-Gram is extended beyond the
 * length of the string with padding.
 * 
 * @author mpkorstanje
 *
 */
public class QGramExtendedTokenizer extends AbstractTokenizer {

	private final String Q_GRAM_START_PADDING = "#";
	private final String Q_GRAM_END_PADDING = "#";

	private final QGramTokenizer tokenizer;
	private final String endPadding;
	private final String startPadding;

	public QGramExtendedTokenizer(int q) {
		tokenizer = new QGramTokenizer(q);

		this.startPadding = Strings.repeat(Q_GRAM_START_PADDING, q - 1);
		this.endPadding = Strings.repeat(Q_GRAM_END_PADDING, q - 1);

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

	public Set<String> tokenizeToSet(String input) {
		return tokenizer.tokenizeToSet(startPadding + input + endPadding);
	}

}
