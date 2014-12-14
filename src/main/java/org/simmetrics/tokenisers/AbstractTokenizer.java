package org.simmetrics.tokenisers;

import java.util.HashSet;
import java.util.Set;

import uk.ac.shef.wit.simmetrics.wordhandlers.DummyStopTermHandler;
import uk.ac.shef.wit.simmetrics.wordhandlers.TermHandler;

public abstract class AbstractTokenizer implements Tokenizer {

	protected TermHandler stopWordHandler = new DummyStopTermHandler();

	public TermHandler getStopWordHandler() {
		return stopWordHandler;
	}

	public void setStopWordHandler(final TermHandler stopWordHandler) {
		this.stopWordHandler = stopWordHandler;
	}

	public Set<String> tokenizeToSet(final String input) {
		return new HashSet<String>(tokenizeToList(input));
	}

	public final String toString() {
		if (stopWordHandler instanceof DummyStopTermHandler) {
			return getClass().getSimpleName();
		}

		return getClass().getSimpleName() + " [" + stopWordHandler + "]";
	}

	protected boolean isWord(String term) {
		return stopWordHandler.isWord(term);
	}

}