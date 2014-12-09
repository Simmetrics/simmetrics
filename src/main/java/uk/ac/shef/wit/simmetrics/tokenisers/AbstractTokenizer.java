package uk.ac.shef.wit.simmetrics.tokenisers;

import java.util.HashSet;
import java.util.Set;

import uk.ac.shef.wit.simmetrics.wordhandlers.DummyStopTermHandler;
import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;

public abstract class AbstractTokenizer implements Tokenizer {

	protected InterfaceTermHandler stopWordHandler = new DummyStopTermHandler();

	public InterfaceTermHandler getStopWordHandler() {
		return stopWordHandler;
	}

	public void setStopWordHandler(final InterfaceTermHandler stopWordHandler) {
		this.stopWordHandler = stopWordHandler;
	}

	public Set<String> tokenizeToSet(final String input) {
		return new HashSet<String>(tokenizeToList(input));
	}

	public final String toString() {
		return getClass().getSimpleName();
	}

	protected boolean isWord(String term) {
		return stopWordHandler.isWord(term);
	}

}