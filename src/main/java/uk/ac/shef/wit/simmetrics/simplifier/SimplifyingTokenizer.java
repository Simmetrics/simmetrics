package uk.ac.shef.wit.simmetrics.simplifier;

import java.util.ArrayList;
import java.util.Set;

import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser;
import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;

public class SimplifyingTokenizer implements InterfaceTokeniser {

	private InterfaceTokeniser tokenizer;

	private Simplifier simplifier;

	@Deprecated
	public String getShortDescriptionString() {
		return getClass().getSimpleName();
	}
	
	public void setSimplifier(Simplifier simplifier) {
		this.simplifier = simplifier;
	}
	
	public void setTokenizer(InterfaceTokeniser tokenizer) {
		this.tokenizer = tokenizer;
	}
	
	public Simplifier getSimplifier() {
		return simplifier;
	}
	public InterfaceTokeniser getTokenizer() {
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

	public ArrayList<String> tokenizeToArrayList(String input) {
		return tokenizer.tokenizeToArrayList(simplifier.simplify(input));
	}

	public Set<String> tokenizeToSet(String input) {
		return tokenizer.tokenizeToSet(simplifier.simplify(input));
	}

}
