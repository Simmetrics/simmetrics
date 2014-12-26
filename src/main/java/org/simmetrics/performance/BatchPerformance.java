package org.simmetrics.performance;

import java.util.concurrent.TimeUnit;

import org.perf4j.StopWatch;
import org.simmetrics.StringMetric;

import static org.simmetrics.StringMetrics.compare;

import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.metrics.Simplifying;
import org.simmetrics.metrics.Tokenizing;
import org.simmetrics.simplifier.CachingSimplifier;
import org.simmetrics.simplifier.Simplifier;
import org.simmetrics.tokenisers.CachingTokenizer;
import org.simmetrics.tokenisers.QGram2Tokenizer;
import org.simmetrics.tokenisers.Tokenizer;
import org.simmetrics.tokenisers.WordQGramTokenizer;

import com.google.common.base.Stopwatch;

public class BatchPerformance {
	private static final int TEST_REPEATS = 100000;

	private static final String name = "Captain Jack Sparrow";

	private static String[] names = new String[] { "Maryjo Murff",
			"Page Desmarais", "Alayna Sawin", "Charmain Scoggin",
			"Sanora Larkey", "Tressie Scales", "Marica Clyburn",
			"Doreen Youngs", "Lorine Mosbey", "Nedra Wagaman",
			"Yolande Lasley", "Gearldine Carmona", "Shirleen Gratton",
			"Merle Damm", "Latricia Hartranft", "Hector Salim",
			"Ardell Motter", "Tammie Toothaker", "Stacee Heiser", "Man Cooter",
			"Booker Pringle", "Shakia Doshier", "Kathryne Desanti",
			"Catalina Darst", "Wendell Vales", "Bessie Mirabella",
			"Carly Wike", "Alise Hatchell", "Marg Dandy", "Clarence Luong",
			"Lakeshia Chick", "Ted Vanderpool", "Exie Kear", "Allie Seibert",
			"Eilene Hastie", "Burma Edinger", "Erick Cusumano",
			"Kandice Hoffman", "Tamatha Verge", "Augustine Kunst",
			"Chu Diemer", "Kerry Jeter", "Lacy Mattoon", "Marvella Gilbreath",
			"Dominick Sharrock", "Lezlie Devillier", "Dennis Dobson",
			"Maryanna Yearwood", "Geoffrey Eisert", "Lachelle Buth",
			"Leah Mitchem", "Elton Avilez", "Salley Starner", "Latanya Gonyea",
			"Diamond Hubbell", "Jessia Leisure", "Florencio Yingst",
			"Sybil Bibee", "Nick Dechant", "Melba Beery", "Samual Wolfrum",
			"Yung Ferrill", "Ngoc Starnes", "Kindra Dahle", "Erika Bryand",
			"Eleanora Whitehair", "Merlin Ceja", "Isadora Tabb",
			"Davis Zickefoose", "Mildred Hosking", "Jene Cornejo",
			"Garnett Kipp", "Gloria Dano", "Joane Gallagher", "Lawanda Richer",
			"Tim Sutton", "Roxann Birk", "Allene Barge", "Natisha Niswander",
			"Mitchel Manser", "Barb Smoot", "Delphia Seville", "Lonny Gailey",
			"Lucy Jelks", "Helaine Donlan", "Efren Macias", "Carrie Clayton",
			"Marielle Stanger", "Kasha Ferrigno", "Tracey Westley",
			"Mitchell Rampton", "Luis Onstad", "Sherlyn Grote", "Wan Eyer",
			"Sandy Leffingwell", "Tonie Packett", "Lanita Jimmerson",
			"Chanel Magnusson", "Vernon Mackson", "Rosamond Bridges" };

	public static void main(String[] args) {

		testCompareUncached();
		testCompareCached();

	}

	private static void testCompareUncached() {

		SimonWhite metric = new SimonWhite();

		Stopwatch sw = Stopwatch.createStarted();
		for (int n = 0; n < TEST_REPEATS; n++) {
			@SuppressWarnings("unused")
			final float[] results = compare(metric, name, names);

		}
		sw.stop();

		String message = "Uncached performance %s ms. Repeats %s.";

		System.out.println(String.format(message,
				sw.elapsed(TimeUnit.MILLISECONDS), TEST_REPEATS));

	}

	private static void testCompareCached() {

		SimonWhite metric = new SimonWhite() {
			{
				setTokenizer(new CachingTokenizer(new WordQGramTokenizer(
						new QGram2Tokenizer())));
			}

		};

		Stopwatch sw = Stopwatch.createStarted();
		for (int n = 0; n < TEST_REPEATS; n++) {
			@SuppressWarnings("unused")
			final float[] results = compare(metric, name, names);

		}
		sw.stop();

		String message = "Cached performance %s ms. Repeats %s";

		System.out.println(String.format(message,
				sw.elapsed(TimeUnit.MILLISECONDS), TEST_REPEATS));

	}
}
