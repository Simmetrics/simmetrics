package org.simmetrics;

import org.simmetrics.metrics.BlockDistance;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.metrics.DiceSimilarity;
import org.simmetrics.metrics.EuclideanDistance;
import org.simmetrics.metrics.JaccardSimilarity;
import org.simmetrics.metrics.Jaro;
import org.simmetrics.metrics.JaroWinkler;
import org.simmetrics.metrics.Levenshtein;
import org.simmetrics.metrics.MatchingCoefficient;
import org.simmetrics.metrics.MongeElkan;
import org.simmetrics.metrics.NeedlemanWunch;
import org.simmetrics.metrics.OverlapCoefficient;
import org.simmetrics.metrics.QGramsDistance;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.metrics.SmithWaterman;
import org.simmetrics.metrics.SmithWatermanGotoh;
import org.simmetrics.metrics.SmithWatermanGotohWindowedAffine;
import org.simmetrics.simplifier.SoundexSimplifier;
import org.simmetrics.tokenisers.QGram2Tokenizer;
import org.simmetrics.tokenisers.QGram3ExtendedTokenizer;
import org.simmetrics.tokenisers.Tokenizer;
import org.simmetrics.tokenisers.WhitespaceTokenizer;
import org.simmetrics.tokenisers.WordQGramTokenizer;

public class Metrics {

	public static StringMetric blockDistance(){
		return new StringMetricBuilder()
			.setMetric(new BlockDistance())
			.setTokeninzer(new WhitespaceTokenizer())
			.build();
	}
	
	public static StringMetric cosineSimilarity(){
		return cosineSimilarity(new WhitespaceTokenizer());
	}
	
	public static StringMetric cosineSimilarity(Tokenizer tokenizer){
		return new StringMetricBuilder()
			.setMetric(new CosineSimilarity())
			.setTokeninzer(tokenizer)
			.build();
	}
	
	public static StringMetric diceSimilarity(){
		return diceSimilarity(new WhitespaceTokenizer());
	}
	
	public static StringMetric diceSimilarity(Tokenizer tokenizer){
		return new StringMetricBuilder()
			.setMetric(new DiceSimilarity())
			.setTokeninzer(tokenizer)
			.build();
	} 
	
	public static StringMetric euclideanDistance(){
		return euclideanDistance(new WhitespaceTokenizer());
	}
	
	public static StringMetric euclideanDistance(Tokenizer tokenizer){
		return new StringMetricBuilder()
			.setMetric(new EuclideanDistance())
			.setTokeninzer(tokenizer)
			.build();
	} 
	
	public static StringMetric jaccardSimilarity(){
		return jaccardSimilarity(new WhitespaceTokenizer());
	}
	
	public static StringMetric jaccardSimilarity(Tokenizer tokenizer){
		return new StringMetricBuilder()
			.setMetric(new JaccardSimilarity())
			.setTokeninzer(tokenizer)
			.build();
	} 
	
	public static StringMetric jaro(){
		return new Jaro();
	}
	
	public static StringMetric jaroWinkler(){
		return new JaroWinkler();
	}
	
	public static StringMetric levenshtein(){
		return new Levenshtein();
	}
	
	public static StringMetric matchingCoefficient(){
		return matchingCoefficient(new WhitespaceTokenizer());
	}
	
	public static StringMetric matchingCoefficient(Tokenizer tokenizer){
		return new StringMetricBuilder()
			.setMetric(new MatchingCoefficient())
			.setTokeninzer(tokenizer)
			.build();
	}
	
	public static StringMetric mongeElkan(){
		return mongeElkan(new WhitespaceTokenizer());
	}
	
	public static StringMetric mongeElkan(Tokenizer tokenizer){
		return new StringMetricBuilder()
			.setMetric(new MongeElkan(new SmithWatermanGotoh()))
			.setTokeninzer(tokenizer)
			.build();
	}
	
	public static StringMetric needlemanWunch(){
		return new NeedlemanWunch();
	}
	
	public static StringMetric overlapCoefficient(){
		return overlapCoefficient(new WhitespaceTokenizer());
	}
	
	public static StringMetric overlapCoefficient(Tokenizer tokenizer){
		return new StringMetricBuilder()
			.setMetric(new OverlapCoefficient())
			.setTokeninzer(tokenizer)
			.build();
	}

	
	
	public static StringMetric qGramsDistance() {
		return qGramsDistance(new QGram3ExtendedTokenizer());
	}

	public static StringMetric qGramsDistance(Tokenizer tokenizer) {
		return new StringMetricBuilder()
			.setMetric(new QGramsDistance())
			.setTokeninzer(tokenizer)
			.build();
	}

	public static StringMetric simonWhite() {
		return simonWhite(new WordQGramTokenizer(new QGram2Tokenizer()));
	}

	public static StringMetric simonWhite(Tokenizer tokenizer) {
		return new StringMetricBuilder()
		.setMetric(new SimonWhite())
		.setTokeninzer(tokenizer)
		.build();
	}

	public static SmithWaterman smithWaterman(){
		return new SmithWaterman();
	}

	public static SmithWatermanGotoh smithWatermanGotoh(){
		return new SmithWatermanGotoh();
	}
	
	public static SmithWatermanGotohWindowedAffine smithWatermanGotohWindowedAffine(){
		return new SmithWatermanGotohWindowedAffine();
	}
	
	public static StringMetric soundex() {
		return new StringMetricBuilder()
		.setMetric(new JaroWinkler())
		.setSimplifier(new SoundexSimplifier())
		.build();
	}
}
