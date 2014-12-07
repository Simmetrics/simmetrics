package uk.ac.shef.wit.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.Set;

import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;

/**
 * Basic Q-Gram tokenizer for a variable Q.The Q-Gram is extended beyond the
 * length of the string with padding.
 * 
 * @author mpkorstanje
 *
 */
public class TokeniserQGramExtended implements InterfaceTokeniser {

	private final char Q_GRAM_START_PADDING = '#';
	private final char Q_GRAM_END_PADDING = '#';

	private final TokeniserQGram tokenizer;
	private final String endPadding;
	private final String startPadding;

	public TokeniserQGramExtended(int q) {
		tokenizer = new TokeniserQGram(q);

		String startPadding = "";
		String endPadding = "";
		for (int i = 0; i < q - 1; i++) {
			startPadding += Q_GRAM_START_PADDING;
			endPadding += Q_GRAM_END_PADDING;
		}

		this.startPadding = startPadding;
		this.endPadding = endPadding;

	}

	public void setStopWordHandler(InterfaceTermHandler stopWordHandler) {
		tokenizer.setStopWordHandler(stopWordHandler);
	}

	public InterfaceTermHandler getStopWordHandler() {
		return tokenizer.getStopWordHandler();
	}

	public ArrayList<String> tokenizeToArrayList(String input) {
		return tokenizer.tokenizeToArrayList(startPadding + input + endPadding);
	}

	public String getShortDescriptionString() {
		return getClass().getSimpleName();
	}

	public Set<String> tokenizeToSet(String input) {
		return tokenizer.tokenizeToSet(startPadding + input + endPadding);
	}

}
