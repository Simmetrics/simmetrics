package org.simmetrics.simplifier;

public class CompositeSimplifier extends AbstractSimplifier {

	private Simplifier[] simplifiers;

	public void setSimplifiers(Simplifier... simplifiers) {
		this.simplifiers = simplifiers;
	}

	public String simplify(String input) {
		for (Simplifier s : simplifiers) {
			input = s.simplify(input);
		}

		return input;

	}

}
