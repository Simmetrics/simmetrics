package uk.ac.shef.wit.simmetrics.simplifier;

import java.util.ArrayList;
import java.util.Set;

import uk.ac.shef.wit.simmetrics.tokenisers.Tokenizer;
import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;

public class SimplifyingTokenizer implements Tokenizer {

	private Tokenizer tokenizer;

	private Simplifier simplifier;

	@Deprecated
	public String getShortDescriptionString() {
		return getClass().getSimpleName();
	}
	
	public void setSimplifier(Simplifier simplifier) {
		this.simplifier = simplifier;
	}
	
	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}
	
	public Simplifier getSimplifier() {
		return simplifier;
	}
	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	@Override
	public String toString() {
		return "SimplifyingTokenizer [" + tokenizer + ", " + simplifier + "]";
	}

	public InterfaceTermHandler getStopWordHandler() {
		return tokenizer.getStopWordHandler();
	}

	public void setStopWordHandler(InterfaceTermHandler stopWordHandler) {
		tokenizer.setStopWordHandler(stopWordHandler);
	}

	public ArrayList<String> tokenizeToList(String input) {
		return tokenizer.tokenizeToList(simplifier.simplify(input));
	}

	public Set<String> tokenizeToSet(String input) {
		return tokenizer.tokenizeToSet(simplifier.simplify(input));
	}

}
