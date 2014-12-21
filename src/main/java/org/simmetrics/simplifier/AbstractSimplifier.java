package org.simmetrics.simplifier;

public abstract class AbstractSimplifier implements Simplifier{

	public AbstractSimplifier() {
		super();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}