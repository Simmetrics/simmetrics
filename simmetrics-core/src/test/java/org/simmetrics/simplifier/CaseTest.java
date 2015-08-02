package org.simmetrics.simplifier;

import org.simmetrics.simplifiers.Case;
import org.simmetrics.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
public class CaseTest {
	
	public static class CaseUpper extends SimplifierTest {

		@Override
		protected Simplifier getSimplifier() {
			return new Case.Upper();
		}

		@Override
		protected T[] getTests() {
			return new T[]{
					new T("A","A"),
					new T("a","A"),
					new T("","")
			};
		}
		
		
	}
	
	public static class CaseLower extends SimplifierTest {

		@Override
		protected Simplifier getSimplifier() {
			return new Case.Lower();
		}

		@Override
		protected T[] getTests() {
			return new T[]{
					new T("A","a"),
					new T("a","a"),
					new T("","")
			};
		}
		
		
	}

}
