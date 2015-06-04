SimMetrics [![Build Status](https://travis-ci.org/Simmetrics/simmetrics.svg)](https://travis-ci.org/Simmetrics/simmetrics)
==========

SimMetrics is a java library of Similarity or Distance Metrics, e.g. Levenshtein distance, Cosine similarity, that provide float based similarity measures between String data. All metrics return consistent measures rather than unbounded similarity scores. 

## Usage ##

For a quick and easy use [StringMetrics](./simmetrics-core/src/main/java/org/simmetrics/StringMetrics.java) contains a collection of well known string metrics.

```

	String str1 = "This is a sentence. It is made of words";
	String str2 = "This sentence is similair. It has almost the same words";
	
	StringMetric metric = StringMetrics.cosineSimilarity();
	
	float result = metric.compare(str1, str2); //0.4472

```

The [StringMetricBuilder](./simmetrics-core/src/main/java/org/simmetrics/StringMetricBuilder.java) is a convenience tool to build string metrics. Any class implementing StringMetric, ListMetric, SetMetric can be used to build a string metric. The builder supports simplification, tokenization, token-filtering, and caching.

For a terse syntax use `import static org.simmetrics.StringMetricBuilder.with;`

```
	String str1 = "This is a sentence. It is made of words";
	String str2 = "This sentence is similair. It has almost the same words";

	StringMetric metric =
			with(new CosineSimilarity<String>())
			.simplify(new Case.Lower(Locale.ENGLISH))
			.simplify(new NonWordCharacter())
			.tokenize(new Whitespace())
			.build();

	float result = metric.compare(str1, str2); //0.5590
```

## Maven ##

SimMetrics is available through Maven Central. Include the dependency:

```
	<dependency>
		<groupId>com.github.mpkorstanje</groupId>
		<artifactId>simmetrics-core</artifactId>
		<version>3.0.3</version>
	</dependency>
```
