package uk.ac.shef.wit.simmetrics.simplifier;

public abstract class AbstractSimplifier implements Simplifier{

	public AbstractSimplifier() {
		super();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}