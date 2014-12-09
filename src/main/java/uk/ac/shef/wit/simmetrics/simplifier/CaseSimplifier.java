package uk.ac.shef.wit.simmetrics.simplifier;

import java.util.Locale;

public abstract class CaseSimplifier implements Simplifier {

	public static class Lower extends CaseSimplifier {

		private final Locale l;

		public Lower(Locale l) {
			super();
			this.l = l;
		}

		public Lower() {
			l = Locale.getDefault();
		}

		public String simplify(String s) {
			return s.toLowerCase(l);
		}
	}

	public static class Upper extends CaseSimplifier {

		private final Locale l;

		public Upper(Locale l) {
			super();
			this.l = l;
		}

		public Upper() {
			l = Locale.getDefault();
		}

		public String simplify(String s) {
			return s.toUpperCase(l);
		}
	}
}
