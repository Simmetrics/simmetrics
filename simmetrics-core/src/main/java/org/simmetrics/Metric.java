package org.simmetrics;

public interface Metric<T> {
	
	
	public float compare(T a,T b);

}